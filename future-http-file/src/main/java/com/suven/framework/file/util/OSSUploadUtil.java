package com.suven.framework.file.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.suven.framework.file.config.FileConfigSetting;

/**
 * 
 * @ClassName: OSSUploadUtil
 * Description: 阿里云OSS文件上传工具类
 * @author AggerChen
 * @date 2016年11月3日 下午12:03:24
 */
public class OSSUploadUtil {
    
    private FileConfigSetting config = null;
    private OSS ossClient;

    private static class OSSUpload {
        private static final OSSUploadUtil INSTANCE = new OSSUploadUtil();
    }

    public static OSSUploadUtil getInstance() {
        OSSUploadUtil oss =  OSSUpload.INSTANCE;
        return oss;
    }

    private OSSUploadUtil(){}





    /**
      *
      * @MethodName: uploadFileToOSS
      * Description: OSS单文件上传
      * @param file 上传的文件
      * @param fileType 文件后缀
      *  @param fileName 文件完整名称，包括后缀
      *  @param bucketName 阿里云的文件块名称
      * @return String 文件地址
      * UUID.randomUUID().toString().toUpperCase().replace("-", "")
      */
    public static boolean uploadFileToOSS(OSS ossClient,File file,String fileType,String fileName,String bucketName){
        try {
            InputStream input = new FileInputStream(file);
            boolean status =  uploadFileInputStreamToOSS(ossClient,input,fileType,fileName,bucketName);
            return status;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     *
     * @MethodName: uploadFileInputStreamToOSS
     * Description: OSS单文件上传,填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
     * @param inputStream 上传的文件流
     * @param fileType 文件后缀
     *  @param fileName 文件完整名称，包括后缀
     *  @param bucketName 阿里云的文件块名称
     * @return String 文件地址
     * UUID.randomUUID().toString().toUpperCase().replace("-", "")
     */
    public static   boolean uploadFileInputStreamToOSS(OSS ossClient,InputStream inputStream,String fileType,String fileName,String bucketName){
        try {
            String type = contentType(fileType);

            ObjectMetadata meta = new ObjectMetadata();             // 创建上传Object的Metadata
            meta.setContentType(type);        // 设置上传内容类型
            meta.setCacheControl("no-cache");// 被下载时网页的缓存行为
            meta.setContentEncoding("utf-8");
            if(!"application/octet-stream".equals(type)){
                meta.setContentDisposition("inline");
            }
            PutObjectRequest request = new PutObjectRequest( bucketName, fileName,inputStream,meta);            //创建上传请求
            ossClient.putObject(request);
            return true;
        } catch (OSSException oe) {
            oe.printStackTrace();
            return false;
        } catch (ClientException ce) {
            ce.printStackTrace();
            return false;
        } finally {
        }

    }
    /**
     * 
     * @MethodName: updateFile
     * Description: 更新文件:只更新内容，不更新文件名和文件地址。
     *         (因为地址没变，可能存在浏览器原数据缓存，不能及时加载新数据，例如图片更新，请注意)
     * @param file
     * @param fileType
     * @param oldUrl
     * @return String
     */
    public static boolean updateFile(OSS ossClient,File file,String fileType,String oldUrl,String bucketName){
        String fileName = getFileName(oldUrl);
        if(fileName==null) {
            return false;
        }
        return uploadFileToOSS(ossClient,file,fileType,fileName,bucketName);
    }
    
    /**
     * 
     * @MethodName: replaceFile
     * Description: 替换文件:删除原文件并上传新文件，文件名和地址同时替换
     *         解决原数据缓存问题，只要更新了地址，就能重新加载数据)
     * @param file
     * @param fileType 文件后缀
     * @param oldUrl 需要删除的文件地址
     * @return String 文件地址
     */
    public static boolean replaceOSSFile(OSS ossClient,File file,String fileType,String fileName, String bucketName,String oldUrl){
        boolean flag = deleteFile(ossClient,oldUrl);        //先删除原文件
        if(!flag){
            //更改文件的过期时间，让他到期自动删除。
        }
        return uploadFileToOSS(ossClient,file, fileType,fileName, bucketName);
    }

    /**
     * 
     * @MethodName: deleteFile
     * Description: 单文件删除
     * @param fileUrl 需要删除的文件url
     * @return boolean 是否删除成功
     */
    public static boolean deleteFile(OSS ossClient,String fileUrl){
        String bucketName = getBucketName(fileUrl);        //根据url获取bucketName
        String fileName = getFileName(fileUrl);            //根据url获取fileName
        if(bucketName==null||fileName==null){
            return false;
        }
        try {
            GenericRequest request = new DeleteObjectsRequest(bucketName).withKey(fileName);
            ossClient.deleteObject(request);
        } catch (Exception oe) {
            oe.printStackTrace();
            return false;
        } finally {
        }
        return true;
    }
    
    /**
     * 
     * @MethodName: batchDeleteFiles
     * Description: 批量文件删除(较快)：适用于相同endPoint和BucketName
     * @param fileUrls 需要删除的文件url集合
     * @return int 成功删除的个数
     */
    public static int deleteFiles(OSS ossClient,List<String> fileUrls){
        int deleteCount = 0;    //成功删除的个数
        String bucketName = getBucketName(fileUrls.get(0));        //根据url获取bucketName
        List<String> fileNames = getFileName(fileUrls);            //根据url获取fileName
        if(bucketName == null || fileNames.size() <= 0) {
            return 0;
        }
        try {
            DeleteObjectsRequest request = new DeleteObjectsRequest(bucketName).withKeys(fileNames);
            DeleteObjectsResult result = ossClient.deleteObjects(request);
            deleteCount = result.getDeletedObjects().size();
        } catch (OSSException oe) {
            oe.printStackTrace();
            throw new RuntimeException("OSS服务异常:", oe);
        } catch (ClientException ce) {
            ce.printStackTrace();
            throw new RuntimeException("OSS客户端异常:", ce);
        } finally { }
        return deleteCount;
        
    }
    
//    /**
//     *
//     * @MethodName: batchDeleteFiles
//     * Description: 批量文件删除(较慢)：适用于不同endPoint和BucketName
//     * @param fileUrls 需要删除的文件url集合
//     * @return int 成功删除的个数
//     */
//    public int deleteFiles(List<String> fileUrls){
//        int count = 0;
//        for (String url : fileUrls) {
//            if(deleteFile(url)){
//                count++;
//            }
//        }
//        return count;
//    }
//

    
    /**
     * 
     * @MethodName: contentType
     * Description: 获取文件类型
     * @param fileType
     * @return String
     */
    private static String contentType(String fileType){
        fileType = fileType.toLowerCase();
        String contentType = "";
        switch (fileType) {
        case "bmp":    contentType = "image/bmp";
                    break;
        case "gif":    contentType = "image/gif";
                    break;
        case "png":    
        case "jpeg":    
        case "jpg":    contentType = "image/jpeg";
                    break;
        case "html":contentType = "text/html";
                    break;
        case "txt":    contentType = "text/plain";
                    break;
        case "vsd":    contentType = "application/vnd.visio";
                    break;
        case "ppt":    
        case "pptx":contentType = "application/vnd.ms-powerpoint";
                    break;
        case "doc":    
        case "docx":contentType = "application/msword";
                    break;
        case "xml":contentType = "text/xml";
                    break;
        case "mp4":contentType = "video/mp4";
                    break;
        default: contentType = "application/octet-stream";
                    break;
        }
        return contentType;
     }  
    
    /**
     * 
     * @MethodName: getBucketName
     * Description: 根据url获取bucketName
     * @param fileUrl 文件url
     * @return String bucketName
     */
    private  static String getBucketName(String fileUrl){
        String http = "http://";
        String https = "https://";
        int httpIndex = fileUrl.indexOf(http);
        int httpsIndex = fileUrl.indexOf(https);
        int startIndex  = 0;
        if(httpIndex==-1){
            if(httpsIndex==-1){
                return null;
            }else{
                startIndex = httpsIndex+https.length();
            }
        }else{
            startIndex = httpIndex+http.length();
        }
        int endIndex = fileUrl.indexOf(".oss-"); 
        return fileUrl.substring(startIndex, endIndex);
    }
    
    /**
     * 
     * @MethodName: getFileName
     * Description: 根据url获取fileName
     * @param fileUrl 文件url
     * @return String fileName
     */
    private static  String getFileName(String fileUrl){
        String str = "aliyuncs.com/";
        int beginIndex = fileUrl.indexOf(str);
        if(beginIndex==-1) {
            return null;
        }
        return fileUrl.substring(beginIndex+str.length());
    }
    
    /**
     * 
     * @MethodName: getFileName
     * Description: 根据url获取fileNames集合
     * @param fileUrls 文件url
     * @return List<String>  fileName集合
     */
    private static List<String> getFileName(List<String> fileUrls){
        List<String> names = new ArrayList<>();
        for (String url : fileUrls) {
            names.add(getFileName(url));
        }
        return names;
    }

    public String oploadBlacket(String bucketName,String objectName) throws Exception{

// 创建InitiateMultipartUploadRequest对象。
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest("<yourBucketName>", "<yourObjectName>");

// 如果需要在初始化分片时设置文件存储类型，请参考以下示例代码。
// ObjectMetadata metadata = new ObjectMetadata();
// metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
// request.setObjectMetadata(metadata);

// 初始化分片。
        InitiateMultipartUploadResult upresult = ossClient.initiateMultipartUpload(request);
// 返回uploadId，它是分片上传事件的唯一标识，您可以根据这个uploadId发起相关的操作，如取消分片上传、查询分片上传等。
        String uploadId = upresult.getUploadId();

// partETags是PartETag的集合。PartETag由分片的ETag和分片号组成。
        List<PartETag> partETags =  new ArrayList<PartETag>();
// 计算文件有多少个分片。
        final long partSize = 1 * 1024 * 1024L;   // 1MB
        final File sampleFile = new File("<localFile>");
        long fileLength = sampleFile.length();
        int partCount = (int) (fileLength / partSize);
        if (fileLength % partSize != 0) {
            partCount++;
        }
// 遍历分片上传。
        for (int i = 0; i < partCount; i++) {
            long startPos = i * partSize;
            long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
            InputStream instream = new FileInputStream(sampleFile);
            // 跳过已经上传的分片。
            instream.skip(startPos);
            UploadPartRequest uploadPartRequest = new UploadPartRequest();
            uploadPartRequest.setBucketName(bucketName);
            uploadPartRequest.setKey(objectName);
            uploadPartRequest.setUploadId(uploadId);
            uploadPartRequest.setInputStream(instream);
            // 设置分片大小。除了最后一个分片没有大小限制，其他的分片最小为100 KB。
            uploadPartRequest.setPartSize(curPartSize);
            // 设置分片号。每一个上传的分片都有一个分片号，取值范围是1~10000，如果超出这个范围，OSS将返回InvalidArgument的错误码。
            uploadPartRequest.setPartNumber( i + 1);
            // 每个分片不需要按顺序上传，甚至可以在不同客户端上传，OSS会按照分片号排序组成完整的文件。
            UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
            // 每次上传分片之后，OSS的返回结果包含PartETag。PartETag将被保存在partETags中。
            partETags.add(uploadPartResult.getPartETag());
        }


// 创建CompleteMultipartUploadRequest对象。
// 在执行完成分片上传操作时，需要提供所有有效的partETags。OSS收到提交的partETags后，会逐一验证每个分片的有效性。当所有的数据分片验证通过后，OSS将把这些分片组合成一个完整的文件。
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(bucketName, objectName, uploadId, partETags);

// 如果需要在完成文件上传的同时设置文件访问权限，请参考以下示例代码。
// completeMultipartUploadRequest.setObjectACL(CannedAccessControlList.PublicRead);

// 完成上传。
        CompleteMultipartUploadResult completeMultipartUploadResult = ossClient.completeMultipartUpload(completeMultipartUploadRequest);

// 关闭OSSClient。
        ossClient.shutdown();
        return null;
    }
}