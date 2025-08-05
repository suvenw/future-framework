package com.suven.framework.file.vo.response;

import com.suven.framework.http.api.ApiDesc;

/**
 * 文件上传下载,请求接口返回信息;
 */

public class FileDownBytesResponseVo {
    @ApiDesc(value =  "下载文件的字节数组")
      private byte[] data;



      public static FileDownBytesResponseVo build(){
          return new FileDownBytesResponseVo();
      }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
