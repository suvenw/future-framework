package com.suven.framework.file.client.sftp;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.ssh.Sftp;
import com.suven.framework.file.client.AbstractFileClient;
import com.suven.framework.file.client.s3.FileUrlResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Sftp 文件客户端
 *
 */
public class SftpFileClient extends AbstractFileClient<SftpFileClientConfig> {

    Logger log = LoggerFactory.getLogger(SftpFileClient.class);
    private Sftp sftp;

    public SftpFileClient(Long id, SftpFileClientConfig config) {
        super(id, config);
    }

    @Override
    protected void doInit() {
        // 补全风格。例如说 Linux 是 /，Windows 是 \
        if (!config.getBasePath().endsWith(File.separator)) {
            config.setBasePath(config.getBasePath() + File.separator);
        }
        // 初始化 Ftp 对象
        this.sftp = new Sftp(config.getHost(), config.getPort(), config.getUsername(), config.getPassword());
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
        try {
            String filePath = getFilePath(path);
            File file = createTempFile();
            FileUtil.writeBytes(content, file);
            sftp.upload(filePath, file);
        }catch (Exception e){
            log.info("Sftp upload bytes content Exception", e);
        }
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
    public String upload(InputStream inputStream, String path, String type, long objectSize,long partSize) {
        // 执行写入
        try {
            String filePath = getFilePath(path);
            File file = createTempFile();
            FileUtil.writeFromStream(inputStream, file);
            sftp.upload(filePath, file);
        }catch (Exception e){
            log.info("Sftp upload bytes content Exception", e);
        }
        // 拼接返回路径
        return super.formatFileUrl(config.getDomain(), path);
    }

    @Override
    public void delete(String path) {
        String filePath = getFilePath(path);
        sftp.delFile(filePath);
    }

    @Override
    public byte[] getContent(String path) {
        String filePath = getFilePath(path);
        File destFile = createTempFile();
        sftp.download(filePath, destFile);
        return FileUtil.readBytes(destFile);
    }

    public static File createTempFile() {
        // 创建文件，通过 UUID 保证唯一
        File file = null;
        try {
            file = File.createTempFile(IdUtil.simpleUUID(), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 标记 JVM 退出时，自动删除
        file.deleteOnExit();
        return file;
    }
    /**
     * 获得文件预签名地址
     *
     * @param path 相对路径
     * @return 文件预签名地址
     */
    @Override
    public FileUrlResponseDto getPrepareSignedObjectUrl(String path) throws Exception {
        return super.getPrepareSignedObjectUrl(path);
    }

    private String getFilePath(String path) {
        return config.getBasePath() + path;
    }

}
