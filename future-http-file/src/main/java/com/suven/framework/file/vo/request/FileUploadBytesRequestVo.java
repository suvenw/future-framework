package com.suven.framework.file.vo.request;


import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.RequestParserVo;import java.io.InputStream;
import java.io.Serializable;

public class FileUploadBytesRequestVo extends RequestParserVo implements Serializable{



    /**
     *  @param urlPath 缓存文件标识
     *  @param position 追加位置
     *  @param fileSize 文件大小
     *  @param filePart 分块文件
     *  @param fileExtName 文件后缀类型 extName
     *  @param netType  网络类型
     *  @param userId 上传的用户
     *
     */

//   private String urlPath; //缓存文件标识
    @ApiDesc(value =  "重复上传文件排除标识",required = 1)
   private String fileMd5; //重复上传文件排除标识
    @ApiDesc(value =  "文件后缀类型",required = 1)
   private String fileType; //文件后缀类型
    @ApiDesc(value =  "文件总大小",required = 1)
   private long fileSize;  //文件总大小
    @ApiDesc(value =  "当初文件偏移位置,文件数据读取的下标",required = 1)
   private long offset;    //offset，当初文件偏移位置
    @ApiDesc(value =  "上传文件块大小",required = 1)
   private byte[] blockSize; //上传文件块大小
    @ApiDesc(value =  "重上传网络类型",required = 1)
   private int netType;     //上传网络类型
    @ApiDesc(value =  "文件流",required = 0)
    private InputStream fileInputStream;
    @ApiDesc(value =  "分块大小",required = 0)
    private long chunkSize;     //上传网络类型

    @ApiDesc(value =  "上传到阿里服务文件夹名称对应服务配置的key",required = 0)
    private String ossKey;
    @ApiDesc(value =  "阿里云文件域名后的文件夹路径 eg: im/head",required = 0)
    private String prefixPath;

    @ApiDesc(value =  "阿里云文件自定义完整路径和名称字,若不传,则以prefixPath为前缀生成uuid文件,若传则以文件名保存 eg: im/head/userName.png",required = 0)
    private String ossPath;


    //    public String getFileExtName() {
//        if(this.urlPath == null){
//            return null;
//        }
//        String fileSuffix = urlPath.substring(urlPath.lastIndexOf(".") + 1);
//        return fileSuffix;
//    }
    public static FileUploadBytesRequestVo build(){
        return new FileUploadBytesRequestVo();
    }

    public boolean checkFileSize() {
        if(null != blockSize && getBlockSize().length > 0){
            if( offset + getBlockSize().length < fileSize){
                return true;
            }
        }return false;

    }

    public String getUrlPath() {
        return fileMd5;
    }


    public String getFileExtName(String fileName ) {
        String fileExtName =  fileName.substring(fileName.lastIndexOf(".") + 1);
        return fileExtName;
    }


    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public FileUploadBytesRequestVo setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
        return this;
    }

    public long getOffset() {
        return offset;
    }

    public FileUploadBytesRequestVo setOffset(long offset) {
        this.offset = offset;
        return this;
    }


    public byte[] getBlockSize() {
        return blockSize;
    }

    public FileUploadBytesRequestVo setBlockSize(byte[] blockSize) {
        this.blockSize = blockSize;
        return this;
    }

    public int getNetType() {
        return netType;
    }

    public void setNetType(int netType) {
        this.netType = netType;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public FileUploadBytesRequestVo setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
        return this;
    }

    public long getChunkSize() {
        return chunkSize;
    }

    public FileUploadBytesRequestVo setChunkSize(long chunkSize) {
        this.chunkSize = chunkSize;
        return this;
    }

    public String getOssKey() {
        return ossKey;
    }

    public void setOssKey(String ossKey) {
        this.ossKey = ossKey;
    }

    public String getPrefixPath() {
        return prefixPath;
    }

    public void setPrefixPath(String prefixPath) {
        this.prefixPath = prefixPath;
    }

    public String getOssPath() {
        return ossPath;
    }

    public void setOssPath(String ossPath) {
        this.ossPath = ossPath;
    }
}
