package com.suven.framework.file.vo.response;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.file.vo.UploadFileErrorEnum;

/**
 * 文件上传下载,请求接口返回信息;
 */

public class FileUploadResponseVo {
    @ApiDesc(value =  "上传文件错误类型")
      private int status;
    @ApiDesc(value =  "上传文件错误类型,描述信息")
      private String errorMsg;
    @ApiDesc(value =  "当初文件偏移位置,文件数据读取的下标")
      private long offset;
    @ApiDesc(value =  "上传文件原路径或加密密匙")
      private String path;
    @ApiDesc(value =  "域名")
    private String domain;
    @ApiDesc(value =  "阿里oss文件路径")
    private String ossUrl;


      public static FileUploadResponseVo build(){
          return new FileUploadResponseVo();
      }

      public FileUploadResponseVo setErrorEnum(UploadFileErrorEnum errorEnum){
          if(errorEnum == null) errorEnum  = UploadFileErrorEnum.UPLOAD_FILE_ERROR_DEFAULT;
          this.setStatus(errorEnum.getIndex()).setErrorMsg(errorEnum.getName());
          return this;
      }

        public int getStatus() {
            return status;
        }

        public FileUploadResponseVo setStatus(int status) {
            this.status = status;
            return this;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public FileUploadResponseVo setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
            return this;
        }

        public long getOffset() {
            return offset;
        }

        public FileUploadResponseVo setOffset(long offset) {
            this.offset = offset;
            return this;
        }

        public String getPath() {
            return path;
        }

        public FileUploadResponseVo setPath(String path) {
          if(!path.startsWith("http") && !path.startsWith("/") ){
              path = "/" + path;
          }
        if(!path.startsWith("https") && !path.startsWith("/") ){
            path = "/" + path;
        }
          this.path = path;
          return this;
        }

        public String getDomain() {
            return domain;
        }

        public FileUploadResponseVo setDomain(String domain) {
            this.domain = domain;
            return this;
        }

    public String getOssUrl() {
        return ossUrl;
    }

    public void setOssUrl(String ossUrl) {
        this.ossUrl = ossUrl;
    }
}
