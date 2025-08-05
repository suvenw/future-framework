package com.suven.framework.file.client.ftp;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.ftp.Ftp;
import cn.hutool.extra.ftp.FtpException;
import cn.hutool.extra.ftp.FtpMode;
import com.suven.framework.file.client.AbstractFileClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Objects;

/**
 * Ftp 文件客户端
 */
public class FtpFileClient extends AbstractFileClient<FtpFileClientConfig> {

    private Logger log = LoggerFactory.getLogger(FtpFileClient.class);

    private Ftp ftp;

    public FtpFileClient(Long id, FtpFileClientConfig config) {
        super(id, config);
    }

    @Override
    protected void doInit() {
        // 把配置的 \ 替换成 /, 如果路径配置 \a\test, 替换成 /a/test, 替换方法已经处理 null 情况
        config.setBasePath(StrUtil.replace(config.getBasePath(), StrUtil.BACKSLASH, StrUtil.SLASH));
        // ftp的路径是 / 结尾
        if (!config.getBasePath().endsWith(StrUtil.SLASH)) {
            config.setBasePath(config.getBasePath() + StrUtil.SLASH);
        }
        // 初始化 Ftp 对象
        this.ftp = new Ftp(config.getHost(), config.getPort(), config.getUsername(), config.getPassword(),
                CharsetUtil.CHARSET_UTF_8, null, null, FtpMode.valueOf(config.getMode()));
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
        String fileName = FileUtil.getName(filePath);
        String dir = StrUtil.removeSuffix(filePath, fileName);
        ftp.reconnectIfTimeout();
        boolean success = ftp.upload(dir, fileName, new ByteArrayInputStream(content));
        if (!success) {
            throw new FtpException(StrUtil.format("上传文件到目标目录 ({}) 失败", filePath));
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
    public String upload(InputStream inputStream, String path, String type, long objectSize,long partSize) throws Exception {
        // 执行写入
        try {
            String filePath = getFilePath(path);
            String fileName = FileUtil.getName(filePath);
            String dir = StrUtil.removeSuffix(filePath, fileName);
            ftp.reconnectIfTimeout();
            boolean success = ftp.upload(dir, fileName, inputStream);
            if (!success) {
                throw new FtpException(StrUtil.format("上传文件到目标目录 ({}) 失败", filePath));
            }
            // 拼接返回路径
            return super.formatFileUrl(config.getDomain(), path);
        }catch (Exception e){
            log.info("upload InputStream file Exception",e);
        }finally {
            if (Objects.nonNull(inputStream)){
                inputStream.close();
            }
        }
        return null;

    }



    @Override
    public void delete(String path) {
        String filePath = getFilePath(path);
        ftp.reconnectIfTimeout();
        ftp.delFile(filePath);
    }

    @Override
    public byte[] getContent(String path) {
        String filePath = getFilePath(path);
        String fileName = FileUtil.getName(filePath);
        String dir = StrUtil.removeSuffix(filePath, fileName);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ftp.reconnectIfTimeout();
        ftp.download(dir, fileName, out);
        return out.toByteArray();
    }

    private String getFilePath(String path) {
        return config.getBasePath() + path;
    }

}