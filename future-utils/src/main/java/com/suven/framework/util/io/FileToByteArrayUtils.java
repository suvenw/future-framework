package com.suven.framework.util.io;

import com.suven.framework.core.ObjectTrue;
import com.suven.framework.util.http.OkHttpClients;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/**
 * Java实现读取文件到Byte数组
 * <p>
 * 提供多种方式读取文件为字节数组，包括传统IO方式、NIO方式、MappedByteBuffer方式等。
 * 同时支持网络图片下载功能。
 * </p>
 *
 * @author Joven.wang
 * @date 2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2018 gc by https://www.suven.top
 */
@Slf4j
public class FileToByteArrayUtils {

    /**
     * 将文件转换为字节数组
     * <p>
     * 使用FileInputStream读取文件内容到字节数组。
     * </p>
     *
     * @param filePath 文件路径
     * @return 字节数组，如果文件过大则返回null
     * @throws IOException 读取文件时发生错误
     */
    public static byte[] toByteArray(String filePath) throws IOException {
        File file = new File(filePath);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            log.error("File too large to read into byte array: {}, size: {} bytes", filePath, fileSize);
            return null;
        }
        try (FileInputStream fi = new FileInputStream(file)) {
            byte[] buffer = new byte[(int) fileSize];
            int offset = 0;
            int numRead = 0;
            while (offset < buffer.length && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
                offset += numRead;
            }
            // 确保所有数据均被读取
            if (offset != buffer.length) {
                throw new IOException("Could not completely read file " + file.getName());
            }
            return buffer;
        }
    }

    /**
     * 使用传统IO方式读取文件到字节数组
     * <p>
     * 使用BufferedInputStream和ByteArrayOutputStream实现缓冲读取。
     * </p>
     *
     * @param filename 文件名
     * @return 字节数组
     * @throws IOException 读取文件时发生错误
     */
    public static byte[] toByteRead(String filename) throws IOException {

        File f = new File(filename);
        if (!f.exists()) {
            log.error("File not found: {}", filename);
            throw new FileNotFoundException(filename);
        }

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
             BufferedInputStream in = new BufferedInputStream(new FileInputStream(f))) {
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            log.error("Error reading file with toByteRead method: {}", filename, e);
            throw e;
        }
    }

    /**
     * 使用NIO方式读取文件到字节数组
     * <p>
     * 使用FileChannel和ByteBuffer实现高效的NIO读取。
     * </p>
     *
     * @param filename 文件名
     * @return 字节数组
     * @throws IOException 读取文件时发生错误
     */
    public static byte[] toFileChannel(String filename) throws IOException {

        File f = new File(filename);
        if (!f.exists()) {
            log.error("File not found: {}", filename);
            throw new FileNotFoundException(filename);
        }

        try (FileInputStream fs = new FileInputStream(f);
             FileChannel channel = fs.getChannel()) {
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
            }
            return byteBuffer.array();
        } catch (IOException e) {
            log.error("Error reading file with toFileChannel method: {}", filename, e);
            throw e;
        }
    }

    /**
     * 使用MappedByteBuffer方式读取文件到字节数组
     * <p>
     * MappedByteBuffer可以在处理大文件时提升性能，通过内存映射文件实现。
     * </p>
     *
     * @param filename 文件名
     * @return 字节数组
     * @throws IOException 读取文件时发生错误
     */
    public static byte[] toAccessFile(String filename) throws IOException {

        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(filename, "r").getChannel();
            MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0, fc.size()).load();
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (IOException e) {
            log.error("Error reading file with toAccessFile method: {}", filename, e);
            throw e;
        } finally {
            try {
                if(fc != null) {
                    fc.close();
                }
            } catch (IOException e) {
                log.error("Error closing FileChannel in toAccessFile method: {}", filename, e);
            }
        }
    }

    /**
     * 使用RandomAccessFile按指定偏移量读取文件块到字节数组
     *
     * @param filePath 文件路径
     * @param startPoint 起始位置偏移量
     * @param blockSize 读取的块大小
     * @return 字节数组，读取失败返回null
     * @throws IOException 读取文件时发生错误
     */
    public static byte[] toAccessFile(String filePath, int startPoint, int blockSize) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            log.error("File not found: {}", filePath);
            throw new FileNotFoundException(filePath);
        }

        try (RandomAccessFile accessFile = new RandomAccessFile(file, "r");
             ByteArrayOutputStream os = new ByteArrayOutputStream(blockSize)) {
            byte[] bytes = new byte[blockSize];
            accessFile.seek(startPoint); // 移动指针到每"段"开头
            int length = accessFile.read(bytes);
            os.write(bytes, 0, length);
            os.flush();
            return os.toByteArray();
        } catch (IOException e) {
            log.error("Error reading file with toAccessFile(position) method: {}, startPos: {}, blockSize: {}",
                    filePath, startPoint, blockSize, e);
        }
        return null;
    }

    /**
     * 追加内容到文件指定位置
     * <p>
     * 在文件指定位置写入数据块，返回写入后的当前位置。
     * </p>
     *
     * @param writeFilePath 写入文件路径
     * @param blockSize 要写入的数据块
     * @param position 写入位置
     * @return 当前文件位置
     */
    public static long toAccessFilePosition(String writeFilePath, byte[] blockSize, long position) {
        File file = new File(writeFilePath);
        return toAccessFilePosition(file, blockSize, position);
    }

    /**
     * 追加内容到文件指定位置
     * <p>
     * 在文件指定位置写入数据块，返回写入后的当前位置。
     * </p>
     *
     * @param writeFile 写入文件对象
     * @param blockSize 要写入的数据块
     * @param position 写入位置
     * @return 当前文件位置
     */
    public static long toAccessFilePosition(File writeFile, byte[] blockSize, long position) {

        try (RandomAccessFile outStream = new RandomAccessFile(writeFile, "rwd")) {
            if(writeFile.length() != position){
                log.warn("File length {} does not match position {}, returning file length", writeFile.length(), position);
                return writeFile.length();
            }
            outStream.seek(position);
            outStream.write(blockSize);
        } catch (IOException e) {
            log.error("Error writing to file with toAccessFilePosition method: {}, position: {}",
                    writeFile.getPath(), position, e);
        }

        return ObjectTrue.isEmpty(writeFile) ? 0 : writeFile.length();
    }

    /**
     * 通过URL下载图片
     * <p>
     * 使用OkHttp客户端下载网络图片并保存到本地。
     * </p>
     *
     * @param urlPath 图片URL地址
     * @param writePath 本地保存路径
     * @return 下载是否成功
     */
    public static boolean downloadPicture(String urlPath, String writePath) {
        try {
            byte[] data = OkHttpClients.getHttp(urlPath, null);
            writeInputStream(data, writePath);
            log.info("Successfully downloaded picture from {} to {}", urlPath, writePath);
            return true;
        } catch (Exception e) {
            log.error("Error downloading picture from {} to {}", urlPath, writePath, e);
        }
        return false;
    }
    /**
     * 网络图片下载
     *
     * @param imageUrl 图片url
     * @param formatName 文件格式名称
     * @param localFile 下载到本地文件
     * @return 下载是否成功
     */
    public static boolean downloadImage(String imageUrl, String formatName, String localFile) {
        boolean isSuccess = false;
        try {
            URL url = new URL(imageUrl);
            isSuccess = ImageIO.write(ImageIO.read(url), formatName, new File(localFile));
            if (isSuccess) {
                log.info("Successfully downloaded image from {} to {}", imageUrl, localFile);
            } else {
                log.warn("Failed to download image from {} to {}", imageUrl, localFile);
            }
        } catch (IOException e) {
            log.error("IO error downloading image from {} to {}", imageUrl, localFile, e);
        } catch (Exception e) {
            log.error("Unexpected error downloading image from {} to {}", imageUrl, localFile, e);
        }
        return isSuccess;
    }



    /**
     * 从网络URL读取字节数据
     * <p>
     * 使用HttpURLConnection从指定URL读取二进制数据。
     * </p>
     *
     * @param urlPath 网络URL地址
     * @return 字节数组，读取失败返回null
     */
    public static byte[] readNetInputStream(String urlPath) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(20 * 1000);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            InputStream inStream = conn.getInputStream();
            byte[] result = readInputStream(inStream);
            if (result != null) {
                log.info("Successfully read data from URL: {}", urlPath);
            }
            return result;
        } catch (Exception e) {
            log.error("Error reading data from URL: {}", urlPath, e);
        } finally {
            if(conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    /**
     * 将字节数据写入文件
     *
     * @param data 字节数据
     * @param writePath 写入路径
     */
    public static void writeInputStream(byte[] data, String writePath) {
        try (FileOutputStream outStream = new FileOutputStream(writePath)) {
            outStream.write(data);
            log.info("Successfully wrote data to file: {}, size: {} bytes", writePath, data.length);
        } catch (Exception e) {
            log.error("Error writing data to file: {}", writePath, e);
        }
    }

    /**
     * 从输入流读取字节数据
     * <p>
     * 使用缓冲区从输入流中读取数据到字节数组。
     * </p>
     *
     * @param inStream 输入流
     * @return 字节数组，读取失败返回null
     */
    public static byte[] readInputStream(InputStream inStream) {
        try (ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            return outStream.toByteArray();
        } catch (IOException e) {
            log.error("Error reading from input stream", e);
        } finally {
            try {
                if(inStream != null){
                    inStream.close();
                }
            } catch (IOException e) {
                log.error("Error closing input stream", e);
            }
        }
        return null;
    }

}
