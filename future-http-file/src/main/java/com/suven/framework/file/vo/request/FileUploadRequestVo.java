package com.suven.framework.file.vo.request;


import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.RequestParserVo;import java.io.Serializable;

public class FileUploadRequestVo extends RequestParserVo implements Serializable{




    @ApiDesc(value =  "文件总大小",required = 1)
   private long fileSize;

    @ApiDesc(value =  "上传到阿里服务文件夹名称对应服务配置的key",required = 0)
    private String ossKey;

    @ApiDesc(value =  "阿里云文件域名后的文件夹路径 eg: im/head",required = 0)
    private String prefixPath;


    @ApiDesc(value =  "阿里云文件自定义完整路径和名称字,若不传,则以prefixPath为前缀生成uuid文件,若传则以文件名保存 eg: im/head/userName.png",required = 0)
    private String ossPath;



    public static FileUploadRequestVo build(){
        return new FileUploadRequestVo();
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileExtName(String fileName ) {
        String fileExtName =  fileName.substring(fileName.lastIndexOf(".") + 1);
        return fileExtName;
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
