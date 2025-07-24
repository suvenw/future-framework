package com.suven.framework.test.junit;

import com.suven.framework.util.json.JsonFormatTool;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class FileUtil {
    /**

     * 文件分块工具

     * @param offset 起始偏移位置

     * @param file 文件

     * @param blockSize 分块大小

     * @return 分块数据

     */

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

                }

            }

        }

        return null;

    }
    public static void oupload(String filePath,String netUrl, Map<String, Object> params) throws Exception{


    File file = new File( filePath);
    MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
    RequestBody filebody = MultipartBody.create(MEDIA_TYPE_PNG, file);
    MultipartBody.Builder multiBuilder = new MultipartBody.Builder();
    //这里是 封装上传图片参数
//        multiBuilder.addFormDataPart("file", file.getName(), filebody);
    //参数以添加header方式将参数封装，否则上传参数为空
    // 设置请求体
        multiBuilder.setType(MultipartBody.FORM);
//这里是 封装上传图片参数
        multiBuilder.addFormDataPart("files", file.getName(), filebody);
    // 封装请求参数,这里最重要
//    HashMap<String, String> params = new HashMap<>();
//        params.put("client","Android");
//        params.put("uid","1061");
//        params.put("token","1911173227afe098143caf4d315a436d");
//        params.put("uuid","A000005566DA77");
    //参数以添加header方式将参数封装，否则上传参数为空
        if (params != null && !params.isEmpty()) {
        for (String key : params.keySet()) {
            multiBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""), RequestBody.create(null, String.valueOf(params.get(key))));
        }
    }
    RequestBody multiBody = multiBuilder.build();
//    OkHttpClient okHttpClient = new OkHttpClient();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.MINUTES)//设置连接超时时间
                .readTimeout(20, TimeUnit.MINUTES).build();//设置读取超时时间


        Request request = new   Request.Builder().url(netUrl).post(multiBody).build();
    Response response = okHttpClient.newCall(request).execute();
    String result  = response.body().string();

    System.out.println("Response content:" + JsonFormatTool.formatJson(result));
//    Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//        @Override
//        public void onFailure(Call call, IOException e) {
//            //请求失败的处理
//            System.out.println("操作失败" + e);
//        }
//        @Override
//        public void onResponse(Call call, Response response) throws IOException {
//           if( response.isSuccessful()){
//               System.out.println("操作成功" + response.body().string());
//           }
//        }
//    });

    }
//    protected Request generateRequest(String url) {
//
//        // 获取分块数据，按照每次10M的大小分块上传
//
//        final int CHUNK_SIZE = 10 * 1024 * 1024;
//
//        //切割文件为10M每份
//
//        byte[] blockData = FileUtil.getBlock(offset, new File(fileInfo.filePath), CHUNK_SIZE);
//
//        if (blockData == null) {
//
//            throw new RuntimeException(String.format("upload file get blockData faild，filePath:%s , offest:%d", fileInfo.filePath, offset));
//
//        }
//
//        curBolckSize = blockData.length;
//
//        // 分块上传，客户端和服务端约定，name字段传文件分块的始偏移量
//
//        String formData = String.format("form-data;name=%s; filename=file", offset);
//
//        RequestBody filePart = new MultipartBody(blockData, MediaType.parse("application/octet-stream"), this);
//
//        MultipartBody requestBody = new MultipartBody.Builder()
//
//                .setType(MultipartBody.FORM)
//
//                .addPart(Headers.of("Content-Disposition", formData), filePart)
//
//                .build();
//
//        // 创建Request对象
//
//        Request request = new Request.Builder()
//
//                .url(url)
//
//                .post(requestBody)
//
//                .build();
//
//        return request;
//
//    }
}
