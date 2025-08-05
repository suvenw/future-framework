package com.suven.framework.file.client.s3;

/**
 * 文件预签名地址 Response DTO
 */

public class FileUrlResponseDto {
    public FileUrlResponseDto() {
    }

    public FileUrlResponseDto(String uploadUrl, String url) {
        this.uploadUrl = uploadUrl;
        this.url = url;
    }

    /**
     * 文件上传 URL（用于上传）
     *
     * 例如说：
     */
    private String uploadUrl;

    /**
     * 文件 URL（用于读取、下载等）
     */
    private String url;

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
