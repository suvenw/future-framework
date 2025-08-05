package com.suven.framework.file.vo.response;

import com.suven.framework.http.api.ApiDesc;

/**
 * 文件上传下载,请求接口返回信息;
 */

public class FileHistoryResponseVo {

    @ApiDesc(value =  "当前位置")
    private long curPosition;
    @ApiDesc(value =  "不带group Path，用于存储fastDfs")
    private String noGroupPath;


    public static FileHistoryResponseVo build(){
          return new FileHistoryResponseVo();
      }


    public long getCurPosition() {
        return curPosition;
    }

    public FileHistoryResponseVo setCurPosition(long curPosition) {
        this.curPosition = curPosition;
        return this;
    }


    public String getNoGroupPath() {
        return noGroupPath;
    }

    public FileHistoryResponseVo setNoGroupPath(String noGroupPath) {
        this.noGroupPath = noGroupPath;
        return this;
    }

}
