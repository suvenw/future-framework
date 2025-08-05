package com.suven.framework.file.client;


import com.suven.framework.file.client.s3.FileUrlResponseDto;

import java.io.InputStream;

/**
 * 文件客户端
 *
 */
public interface FileClient {

    /**
     * 获得客户端编号
     *
     * @return 客户端编号
     */
    Long getId();

    /**
     * 上传文件
     *
     * @param content 文件流
     * @param path    相对路径
     * @return 完整路径，即 HTTP 访问地址
     * @throws Exception 上传文件时，抛出 Exception 异常
     */
    String upload(byte[] content, String path, String type) throws Exception;
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
    String upload(InputStream inputStream, String path, String type,long objectSize,long partSize) throws Exception;
    /**
     * 上传文件 ,（即 1024 字节）和分块大小 256（即 256 字节）。
     *  objectSize 上传文件大小设置为-1
     *  partSize 上传文件分块大小设置为1024
     * @param inputStream 文件流
     * @param path    相对路径
     * @param type 上传文件类型
     * @return 完整路径，即 HTTP 访问地址
     * @throws Exception 上传文件时，抛出 Exception 异常
     *
     */
   default String upload(InputStream inputStream, String path, String type) throws Exception{
      return upload(inputStream,path,type,-1,1024);
   }

    /**
     * 删除文件
     *
     * @param path 相对路径
     * @throws Exception 删除文件时，抛出 Exception 异常
     */
    void delete(String path) throws Exception;

    /**
     * 获得文件的内容
     *
     * @param path 相对路径
     * @return 文件的内容
     */
    byte[] getContent(String path) throws Exception;



    /**
     * 获得文件预签名地址
     *
     * @param path 相对路径
     * @return 文件预签名地址
     */
    default FileUrlResponseDto getPrepareSignedObjectUrl(String path) throws Exception {
        throw new UnsupportedOperationException("不支持的操作");
    }

}
