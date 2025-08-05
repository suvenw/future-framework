package com.suven.framework.file.vo.request;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.RequestParserVo;

import java.util.List;

public class FileBathUploadRequestVo extends RequestParserVo {

    @ApiDesc(value =  "批量上传，每个文件总大小，前端把每个文件大小按顺序，中间用','分隔",required = 1)
    private List<Long> fileSize;


    @ApiDesc(value =  "上传到阿里服务文件夹名称对应服务配置的key",required = 0)
    private String ossKey;

    @ApiDesc(value =  "阿里云文件域名后的文件夹路径 eg: im/head",required = 0)
    private String prefixPath;


    public List<Long> getFileSize() {
        return fileSize;
    }

    public void setFileSize(List<Long> fileSize) {
        this.fileSize = fileSize;
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


}
