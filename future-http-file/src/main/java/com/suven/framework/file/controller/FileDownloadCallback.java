package com.suven.framework.file.controller;


import com.github.tobato.fastdfs.domain.proto.storage.DownloadCallback;
import com.suven.framework.util.io.FileToByteArrayUtils;

import java.io.IOException;
import java.io.InputStream;

public class FileDownloadCallback implements DownloadCallback<byte[]> {
    @Override
    public byte[] recv(InputStream ins) throws IOException {
        byte[] data =  FileToByteArrayUtils.readInputStream(ins);
        return data;
    }
}
