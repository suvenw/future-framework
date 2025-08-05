package com.suven.framework.file.client.s3;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.suven.framework.file.client.AbstractFileClient;
import io.minio.*;
import io.minio.http.Method;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * 基于 S3 协议的文件客户端，实现 MinIO、阿里云、腾讯云、七牛云、华为云等云服务
 * <p>
 * S3 协议的客户端，采用亚马逊提供的 software.amazon.awssdk.s3 库
 */
public class S3FileClient extends AbstractFileClient<S3FileClientConfig> {

    private MinioClient client;

    public S3FileClient(Long id, S3FileClientConfig config) {
        super(id, config);
    }

    @Override
    protected void doInit() {
        // 补全 domain
        if (StrUtil.isEmpty(config.getDomain())) {
            config.setDomain(buildDomain());
        }
        // 初始化客户端
        client = MinioClient.builder()
                .endpoint(buildEndpointURL()) // Endpoint URL
                .region(buildRegion()) // Region
                .credentials(config.getAccessKey(), config.getAccessSecret()) // 认证密钥
                .build();
    }

    /**
     * 基于 endpoint 构建调用云服务的 URL 地址
     *
     * @return URI 地址
     */
    private String buildEndpointURL() {
        // 如果已经是 http 或者 https，则不进行拼接.主要适配 MinIO
        if (HttpUtil.isHttp(config.getEndpoint()) || HttpUtil.isHttps(config.getEndpoint())) {
            return config.getEndpoint();
        }
        return StrUtil.format("https://{}", config.getEndpoint());
    }

    /**
     * 基于 bucket + endpoint 构建访问的 Domain 地址
     *
     * @return Domain 地址
     */
    private String buildDomain() {
        // 如果已经是 http 或者 https，则不进行拼接.主要适配 MinIO
        if (HttpUtil.isHttp(config.getEndpoint()) || HttpUtil.isHttps(config.getEndpoint())) {
            return StrUtil.format("{}/{}", config.getEndpoint(), config.getBucket());
        }
        // 阿里云、腾讯云、华为云都适合。七牛云比较特殊，必须有自定义域名
        return StrUtil.format("https://{}.{}", config.getBucket(), config.getEndpoint());
    }

    /**
     * 基于 bucket 构建 region 地区
     *
     * @return region 地区
     */
    private String buildRegion() {
        // 阿里云必须有 region，否则会报错
        if (config.getEndpoint().contains(S3FileClientConfig.ENDPOINT_ALIYUN)) {
            return StrUtil.subBefore(config.getEndpoint(), '.', false)
                    .replaceAll("-internal", "")// 去除内网 Endpoint 的后缀
                    .replaceAll("https://", "");
        }
        // 腾讯云必须有 region，否则会报错
        if (config.getEndpoint().contains(S3FileClientConfig.ENDPOINT_TENCENT)) {
            return StrUtil.subAfter(config.getEndpoint(), "cos.", false)
                    .replaceAll("." + S3FileClientConfig.ENDPOINT_TENCENT, ""); // 去除 Endpoint
        }
        return null;
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
    public String upload(byte[] content, String path, String type) throws Exception {
        // 执行上传
        client.putObject(PutObjectArgs.builder()
                .bucket(config.getBucket()) // bucket 必须传递
                .contentType(type)
                .object(path) // 相对路径作为 key
                .stream(new ByteArrayInputStream(content), content.length, -1) // 文件内容
                .build());
        // 拼接返回路径
        return config.getDomain() + "/" + path;
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
    public String upload(InputStream inputStream, String path,  String type,long objectSize,long partSize) throws Exception {
        // 执行上传
        client.putObject(PutObjectArgs.builder()
                .bucket(config.getBucket()) // bucket 必须传递
                .contentType(type)
                .object(path) // 相对路径作为 key
                .stream(inputStream, objectSize,partSize) // 文件内容
                .build());
        // 拼接返回路径
        return config.getDomain() + "/" + path;
    }



    @Override
    public void delete(String path) throws Exception {
        client.removeObject(RemoveObjectArgs.builder()
                .bucket(config.getBucket()) // bucket 必须传递
                .object(path) // 相对路径作为 key
                .build());
    }

    @Override
    public byte[] getContent(String path) throws Exception {
        GetObjectResponse response = client.getObject(GetObjectArgs.builder()
                .bucket(config.getBucket()) // bucket 必须传递
                .object(path) // 相对路径作为 key
                .build());
        return IoUtil.readBytes(response);
    }

    /**
     * 获得文件预签名地址
     *
     * @param path 相对路径
     * @return 文件预签名地址
     */
    @Override
    public FileUrlResponseDto getPrepareSignedObjectUrl(String path) throws Exception {
        String uploadUrl = client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.PUT)
                .bucket(config.getBucket())
                .object(path)
                .expiry(10, TimeUnit.MINUTES) // 过期时间（秒数）取值范围：1 秒 ~ 7 天
                .build()
        );
        return new FileUrlResponseDto(uploadUrl, config.getDomain() + "/" + path);
    }


}
