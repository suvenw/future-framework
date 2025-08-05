package com.suven.framework.file.vo.request;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.RequestParserVo;

import java.util.List;

public class FileDeleteRequestVo extends RequestParserVo {

    @ApiDesc(value =  "删除一个文件地址的唯一标识",required = 0)
   private String fileUrl;

    @ApiDesc(value =  "批量删除文件地址的唯一标识，各文件地址间用',' 分隔开",required = 0)
    List<String> fileUrls;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public List<String> getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(List<String> fileUrls) {
        this.fileUrls = fileUrls;
    }
}
