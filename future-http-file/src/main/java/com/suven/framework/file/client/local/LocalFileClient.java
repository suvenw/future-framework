package com.suven.framework.file.client.local;

import cn.hutool.core.io.FileUtil;
import com.suven.framework.file.client.AbstractFileClient;

import java.io.File;
import java.io.InputStream;

/**
 * 本地文件客户端
 */
public class LocalFileClient extends AbstractFileClient<LocalFileClientConfig> {

    public LocalFileClient(Long id, LocalFileClientConfig config) {
        super(id, config);
    }

    @Override
    protected void doInit() {
        // 补全风格。例如说 Linux 是 /，Windows 是 \
        if (!config.getBasePath().endsWith(File.separator)) {
            config.setBasePath(config.getBasePath() + File.separator);
        }
    }

    /**
     * 上传文件 ,（即 1024 字节）和分块大小 256（即 256 字节）。
     *
     * @param content 文件流
     * @param path    相对路径
     * @param type 上传文件类型
     * @return 完整路径，即 HTTP 访问地址
     * @throws Exception 上传文件时，抛出 Exception 异常
     */
    @Override
    public String upload(byte[] content, String path, String type) {
        // 执行写入
        String filePath = getFilePath(path);
        FileUtil.writeBytes(content, filePath);
        // 拼接返回路径
        return super.formatFileUrl(config.getDomain(), path);
    }

    /**
     * 上传文件 ,（即 1024 字节）和分块大小 256（即 256 字节）。
     *
     * @param inputStream 文件流
     * @param path    相对路径
     * @param type 上传文件类型
     * @param objectSize 上传文件大小  1024 （即 1024 字节)
     * @param partSize 上传文件分块大小 256 分块大小 256（即 256 字节）。
     * @return 完整路径，即 HTTP 访问地址
     * @throws Exception 上传文件时，抛出 Exception 异常
     */
    @Override
    public String upload(InputStream inputStream, String path, String type, long objectSize,long partSize) throws Exception {
        // 执行写入
        String filePath = getFilePath(path);
        FileUtil.writeFromStream(inputStream, filePath);
        // 拼接返回路径
        return super.formatFileUrl(config.getDomain(), path);
    }

    @Override
    public void delete(String path) {
        String filePath = getFilePath(path);
        FileUtil.del(filePath);
    }

    @Override
    public byte[] getContent(String path) {
        String filePath = getFilePath(path);
        return FileUtil.readBytes(filePath);
    }

    private String getFilePath(String path) {
        return config.getBasePath() + path;
    }

}
