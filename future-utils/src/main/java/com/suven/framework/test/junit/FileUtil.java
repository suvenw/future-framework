package com.suven.framework.test.junit;

import com.suven.framework.util.json.JsonFormatTool;
import okhttp3.*;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 文件操作工具类
 * <p>
 * 提供文件分块读取和文件上传功能
 * </p>
 *
 * @author suven
 */
@SuppressWarnings({"unused", "resource"})
public class FileUtil {
    /**
     * 文件分块工具
     * <p>
     * 从指定文件的指定偏移位置开始读取指定大小的数据块
     * </p>
     *
     * @param offset    起始偏移位置（字节）
     * @param file      要读取的文件
     * @param blockSize 分块大小（字节）
     * @return 分块数据，如果读取失败或到达文件末尾则返回 null
     */
    @Nullable
    public static byte[] getBlock(long offset, File file, int blockSize) {
        byte[] result = new byte[blockSize];
        RandomAccessFile accessFile = null;

        try {
            accessFile = new RandomAccessFile(file, "r");
            accessFile.seek(offset);
            int readSize = accessFile.read(result);

            if (readSize == -1) {
                return null;
            } else if (readSize == blockSize) {
                return result;
            } else {
                byte[] tmpByte = new byte[readSize];
                System.arraycopy(result, 0, tmpByte, 0, readSize);
                return tmpByte;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (accessFile != null) {
                try {
                    accessFile.close();
                } catch (IOException e1) {
                    // Ignore exception on close
                }
            }
        }
        return null;
    }

    /**
     * 上传文件到指定URL
     * <p>
     * 使用 OkHttp 实现文件上传，支持 multipart/form-data 格式
     * </p>
     *
     * @param filePath 文件路径
     * @param netUrl   目标URL
     * @param params   额外请求参数，可为 null
     * @throws Exception 上传失败时抛出异常
     */
    public static void oupload(String filePath, String netUrl, @Nullable Map<String, Object> params) throws Exception {
        File file = new File(filePath);
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
        RequestBody filebody = MultipartBody.create(MEDIA_TYPE_PNG, file);
        MultipartBody.Builder multiBuilder = new MultipartBody.Builder();

        // 设置请求体类型
        multiBuilder.setType(MultipartBody.FORM);

        // 封装上传文件参数
        multiBuilder.addFormDataPart("files", file.getName(), filebody);

        // 封装请求参数
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                multiBuilder.addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, String.valueOf(params.get(key)))
                );
            }
        }

        RequestBody multiBody = multiBuilder.build();

        // 创建 OkHttp 客户端，设置超时时间
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.MINUTES)  // 设置连接超时时间
                .readTimeout(20, TimeUnit.MINUTES)     // 设置读取超时时间
                .build();

        // 创建请求并执行
        Request request = new Request.Builder().url(netUrl).post(multiBody).build();
        Response response = okHttpClient.newCall(request).execute();
        String result = response.body().string();

        // 输出响应结果
        System.out.println("Response content:" + JsonFormatTool.formatJson(result));
    }
}
