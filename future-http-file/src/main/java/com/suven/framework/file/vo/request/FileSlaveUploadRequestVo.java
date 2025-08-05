package com.suven.framework.file.vo.request;



import com.suven.framework.http.api.ApiDesc;

import java.io.Serializable;

public class FileSlaveUploadRequestVo extends FileUploadBytesRequestVo implements Serializable{



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

    @ApiDesc(value = "原文件路径",type = "String")
   private String urlPath; //缓存文件标识
    @ApiDesc(value = "别名,缩放图的扩展名称",type = "String")
   private String prefixName; //别名


    public static FileSlaveUploadRequestVo build(){
        return new FileSlaveUploadRequestVo();
    }


    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }



    public String group(){
        String fileGroupName =  urlPath.substring(0,urlPath.indexOf("/") );
        return fileGroupName;
    }
    public String masterFilename(){
        String masterFilename =  urlPath.substring(urlPath.indexOf("/")+1 );
        return masterFilename;
    }

    public String getPrefixName() {
        return prefixName;
    }

    public FileSlaveUploadRequestVo setPrefixName(String prefixName) {
        this.prefixName = prefixName;
        return this;
    }



    public static void main(String[] args) {
        FileSlaveUploadRequestVo vo =  new  FileSlaveUploadRequestVo();
        vo.setUrlPath("group1/M00/111.jpg");
        System.out.println(vo.group());
        System.out.println(vo.masterFilename());
    }

}
