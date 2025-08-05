package com.suven.framework.file.vo.request;


import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.RequestParserVo;import java.io.Serializable;

public class FileDownloadRequestVo extends RequestParserVo implements Serializable{



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

    @ApiDesc(value =  "缓存文件标识",required = 1)
   private String urlPath; //缓存文件标识
    @ApiDesc(value =  "文件后缀类型",required = 1)
   private String fileType; //文件后缀类型
    @ApiDesc(value =  "当初文件偏移位置",required = 0)
   private long fileOffset;  //offset，当初文件偏移位置
    @ApiDesc(value =  "文件总大小",required = 0)
   private long fileSize;    //文件总大小


    public static FileDownloadRequestVo build(){
        return new FileDownloadRequestVo();
    }


    public String getUrlPath() {
        return urlPath;
    }

    public FileDownloadRequestVo setUrlPath(String urlPath) {
        this.urlPath = urlPath;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public FileDownloadRequestVo setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public long getFileOffset() {
        return fileOffset;
    }

    public FileDownloadRequestVo setFileOffset(long fileOffset) {
        this.fileOffset = fileOffset;
        return this;
    }

    public long getFileSize() {
        return fileSize;
    }

    public FileDownloadRequestVo setFileSize(long fileSize) {
        this.fileSize = fileSize;
        return this;
    }
}
