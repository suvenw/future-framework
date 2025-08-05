package com.suven.framework.file.client;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.file.client.ftp.FtpFileClient;
import com.suven.framework.file.client.ftp.FtpFileClientConfig;
import com.suven.framework.file.client.local.LocalFileClient;
import com.suven.framework.file.client.local.LocalFileClientConfig;
import com.suven.framework.file.client.s3.S3FileClient;
import com.suven.framework.file.client.s3.S3FileClientConfig;
import com.suven.framework.file.client.sftp.SftpFileClient;
import com.suven.framework.file.client.sftp.SftpFileClientConfig;

import java.util.Arrays;
import java.util.Map;

/**
 * 文件存储器枚举
 */
public enum FileStorageEnum {

    LOCAL(10, LocalFileClientConfig.class, LocalFileClient.class),
    FTP(11, FtpFileClientConfig.class, FtpFileClient.class),
    SFTP(12, SftpFileClientConfig.class, SftpFileClient.class),
    S3(20, S3FileClientConfig.class, S3FileClient.class),


    ;

    private static final Map<Integer, FileStorageEnum> enumMap =
            IterableConvert.convertEnumMap(Arrays.asList(values()),FileStorageEnum::getStorage);

    FileStorageEnum(Integer storage, Class<? extends FileClientConfig> configClass, Class<? extends FileClient> clientClass) {
        this.storage = storage;
        this.configClass = configClass;
        this.clientClass = clientClass;
    }

    /**
     * 存储器
     */
    private final Integer storage;

    /**
     * 配置类
     */
    private final Class<? extends FileClientConfig> configClass;
    /**
     * 客户端类
     */
    private final Class<? extends FileClient> clientClass;

    public static FileStorageEnum getByStorage(Integer storage) {
        return enumMap.get(storage);
    }

    public Integer getStorage() {
        return storage;
    }

    public Class<? extends FileClientConfig> getConfigClass() {
        return configClass;
    }

    public Class<? extends FileClient> getClientClass() {
        return clientClass;
    }
}
