package com.suven.framework.file.vo.request;


import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.RequestParserVo;import java.io.Serializable;


 /** 上传 字符串的请求类
 *   urlPath 缓存文件标识
 *   position 追加位置
 *   fileSize 文件大小
 *   filePart 分块文件
 *   fileExtName 文件后缀类型 extName
 *   netType  网络类型
 *   userId 上传的用户
 *
 */

public class FileUploadStringRequestVo extends RequestParserVo implements Serializable{



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
    @ApiDesc(value =  "重复上传文件排除标识",required = 1)
   private String fileMd5; //重复上传文件排除标识
     @ApiDesc(value =  "文件后缀类型",required = 1)
   private String fileType; //文件后缀类型
     @ApiDesc(value =  "文件总大小",required = 1)
   private long fileSize;  //文件总大小
     @ApiDesc(value =  "offset，当初文件偏移位置",required = 1)
   private long offset;    //offset，当初文件偏移位置
     @ApiDesc(value =  "上传文件块大小,base64 encode",required = 1)
   private String blockSize; //上传文件块大小
     @ApiDesc(value =  "缓存文件标识",required = 0)
    private String urlPath; //缓存文件标识
     @ApiDesc(value =  "别名,用于缩放图",required = 0)
    private String prefixName; //别名


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

    public String getFileMd5() {
        return fileMd5;
    }

    public FileUploadStringRequestVo setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
        return this;
    }

    public FileUploadStringRequestVo setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public long getFileSize() {
        return fileSize;
    }

    public FileUploadStringRequestVo setFileSize(long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public long getOffset() {
        return offset;
    }

    public FileUploadStringRequestVo setOffset(long offset) {
        this.offset = offset;
        return this;
    }

    public String getBlockSize() {
        return blockSize;
    }

    public FileUploadStringRequestVo setBlockSize(String blockSize) {
        this.blockSize = blockSize;
        return this;
    }

    public FileUploadStringRequestVo setUrlPath(String urlPath) {
        this.urlPath = urlPath;
        return this;
    }

    public String getPrefixName() {
        return prefixName;
    }

    public FileUploadStringRequestVo setPrefixName(String prefixName) {
        this.prefixName = prefixName;
        return this;
    }
    public String group(){
        String fileGroupName =  urlPath.substring(0,urlPath.indexOf("/") );
        return fileGroupName;
    }
    public String masterFilename(){
        String masterFilename =  urlPath.substring(urlPath.indexOf("/")+1 );
        return masterFilename;
    }

}
