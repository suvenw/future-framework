package com.suven.framework.util.io;

import com.suven.framework.util.http.OkHttpClients;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/**
 * Title: FileToByteArrayUtils.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) java实现读取文件到Byte数组
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
public class FileToByteArrayUtils {

    public static byte[] toByteArray(String filePath) throws IOException {
        File file = new File(filePath);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file "
                    + file.getName());
        }
        fi.close();
        return buffer;
    }

    /**
     * the traditional io way
     * @param filename
     * @return
     * @throws IOException
     */
    public static byte[] toByteRead(String filename) throws IOException {

        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }

    /**
     * NIO way
     * @param filename
     * @return
     * @throws IOException
     */
    public static byte[] toFileChannel(String filename) throws IOException {

        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }

        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
            }
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
     * @param filename
     * @return
     * @throws IOException
     */
    public static byte[] toAccessFile(String filename) throws IOException {

        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(filename, "r").getChannel();
            MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0,fc.size()).load();
            //System.out.println(byteBuffer.isLoaded());
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                // System.out.println("remain");
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                fc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] toAccessFile(String filePath, int startPoint, int blockSize) throws IOException {
        RandomAccessFile accessFile;
        ByteArrayOutputStream os ;
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }
        try {
            accessFile = new RandomAccessFile(file, "r");
            os = new ByteArrayOutputStream(blockSize);

            byte[] bytes = new byte[blockSize];
            accessFile.seek(startPoint);// 移动指针到每“段”开头
            int length = accessFile.read(bytes);
            os.write(bytes, 0, length);
            os.flush();
            os.close();
            return os.toByteArray();
        } catch (IOException e) {
                e.printStackTrace();
        }
        return null;
    }

    /**
     * 追加缓存文件
     * 返回当前位子
     * @param writeFilePath
     * @param blockSize
     * @param position
     */
    public static long toAccessFilePosition(String writeFilePath , byte[] blockSize, long position) {
        File file = new File(writeFilePath);
        return toAccessFilePosition(file,blockSize,position);
    }

    /**
     * 追加缓存文件
     * 返回当前位子
     * @param writeFile
     * @param blockSize
     * @param position
     */
    public static long toAccessFilePosition(File writeFile , byte[] blockSize, long position) {

        RandomAccessFile outStream = null;
        try {
            if(writeFile.length() != position){
                return writeFile.length();
            }
            outStream = new RandomAccessFile(writeFile, "rwd");
            outStream.seek(position);
            outStream.write(blockSize);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(outStream != null){
                    outStream.close();
                }
            } catch (IOException e) {
            }
        }

        return writeFile == null ? 0 : writeFile.length();
    }

    //链接url下载图片
    public static boolean downloadPicture(String urlPath,String writePath) {
        try {
            byte[] data = OkHttpClients.getHttp(urlPath,null);
            writeInputStream(data,writePath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 网络图片下载
     * @param imageUrl 图片url
     * @param formatName 文件格式名称
     * @param localFile 下载到本地文件
     * @return 下载是否成功
     */
    public static boolean downloadImage(String imageUrl, String formatName, String localFile) {
        boolean isSuccess = false;
        URL url = null;
        try {
            url = new URL(imageUrl);
            isSuccess = ImageIO.write(ImageIO.read(url), formatName, new File(localFile));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }



    /**
     * @param urlPath
     */
    public static  byte[] readNetInputStream(String urlPath){
        FileOutputStream outStream = null;
        try {
            //new一个URL对象
            URL url = new URL(urlPath);
            //打开链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒  这里先设置为20s
            conn.setConnectTimeout(20 * 1000);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = readInputStream(inStream);
            return data;
            //new一个文件对象用来保存图片，默认保存当前工程根目录
//            File imageFile = new File(path);
//            //创建输出流
//            outStream = new FileOutputStream(imageFile);
//            //写入数据
//            outStream.write(data);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭输出流
//            outStream.close();
        }
        return null;

    }

    public static void writeInputStream(byte[] data,String writePath){
        FileOutputStream outStream = null;
        try {
            //new一个文件对象用来保存图片，默认保存当前工程根目录
            File imageFile = new File(writePath);
            //创建输出流
            outStream = new FileOutputStream(imageFile);
            //写入数据
            outStream.write(data);
//         关闭输出流
        }catch (Exception e){
            e.printStackTrace();
        }finally {
           try{
               if(null != outStream){
                   outStream.close();
               }
           }catch (Exception ex){

           }
        }

    }

    public static byte[] readInputStream(InputStream inStream){
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while( (len=inStream.read(buffer)) != -1 ){
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            //把outStream里的数据写入内存
            return outStream.toByteArray();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(inStream != null){
                    //关闭输入流
                    inStream.close();
                }
            } catch (IOException e) {
            }
        }
        return null;
    }

}
