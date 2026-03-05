package com.suven.framework.util.qrcode;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码生成、解析器帮助类
 * <p>
 * 提供二维码的生成和解析功能，支持添加Logo、底部描述文字等功能。
 * </p>
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class QRCodeUtil {
    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "png";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;


    /**
     * 检查路径图片是否存在
     *
     * @param imgPath 图片路径
     * @return BufferedImage对象，文件不存在返回null
     * @throws IOException 读取图片时发生错误
     */
    @Nullable
    private static BufferedImage CheckImageExist(String imgPath) throws IOException{
        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println(""+imgPath+"   该文件不存在！");
            return null;
        }
        return ImageIO.read(file);
    }


    /**
     * 创建二维码图片
     * <p>
     * 根据内容生成二维码，可选择添加Logo和底部描述文字。
     * </p>
     *
     * @param content 二维码内容
     * @param logoImage Logo图片，可为null
     * @param bottomDes 底部描述文字，可为null
     * @param needCompress 是否压缩Logo大小
     * @return 二维码图片
     * @throws Exception 生成二维码时发生错误
     */
    private static BufferedImage createImage(String content, @Nullable BufferedImage logoImage, @Nullable String bottomDes,
                                             boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); //容错级别 H->30%
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int tempHeight = height;
        boolean needDescription=(null==bottomDes&&"".equals(bottomDes));
        if (needDescription) {
            tempHeight += 30;
        }
        BufferedImage image = new BufferedImage(width, tempHeight,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }

        if(null==logoImage)
            return image;

        // 插入图片
        QRCodeUtil.insertImage(image, logoImage, needCompress);

        if(needDescription)
            return image;

        QRCodeUtil.addFontImage(image, bottomDes);

        return image;
    }

    /**
     * 添加底部图片文字
     *
     * @param source 图片源
     * @param declareText 文字内容，可为null
     */
    private static void addFontImage(BufferedImage source, @Nullable String declareText) {
        BufferedImage textImage = FontImage.getImage(declareText,new Font("宋体", Font.BOLD, 30), QRCODE_SIZE, 50);
//        BufferedImage image = ImageIO.read(file);
        Graphics2D graph = source.createGraphics();

        int width = textImage.getWidth(null);
        int height = textImage.getHeight(null);

        Image src =textImage;
        graph.drawImage(src, 0, QRCODE_SIZE - 20, width, height, null);
        graph.dispose();
    }

    /**
     * 插入Logo图片
     *
     * @param source 图片操作对象
     * @param logoImage Logo图片
     * @param needCompress 是否压缩Logo大小
     * @throws Exception 插入Logo时发生错误
     */
    private static void insertImage(BufferedImage source, BufferedImage logoImage,
                                    boolean needCompress) throws Exception {
        int width = logoImage.getWidth(null);
        int height = logoImage.getHeight(null);

        Image src =logoImage;
        if (needCompress) {
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }

            Image  image = logoImage.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 创建目录
     * <p>
     * 如果目录不存在则创建，支持多级目录创建。
     * </p>
     *
     * @param destPath 目录路径
     */
    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }


    /**
     * 生成二维码（以Logo路径）
     * <p>
     * 根据Logo文件路径生成二维码，支持添加底部描述文字。
     * </p>
     *
     * @param content 内容（若带http://会自动跳转）
     * @param imgPath Logo图片地址，可为null
     * @param bottomDes 底部文字描述，可为null
     * @param destPath 保存二维码地址（没有该目录会自动创建）
     * @param needCompress 是否压缩Logo大小
     * @throws Exception 生成二维码时发生错误
     */
    public static void encodeOfPath(String content, @Nullable String imgPath, @Nullable String bottomDes, String destPath,
                                    boolean needCompress) throws Exception {

        BufferedImage image = QRCodeUtil.createImage(content, CheckImageExist(imgPath),
                bottomDes,needCompress);
        mkdirs(destPath);
        String file = UUID.randomUUID().toString()+".png";
        ImageIO.write(image, FORMAT_NAME, new File(destPath+"/"+file));
    }


    /**
     * 生成二维码
     * <p>
     * 根据Logo图片对象生成二维码，支持添加底部描述文字。
     * </p>
     *
     * @param content 内容
     * @param logoImage 图片源LOGO，可为null
     * @param bottomDes 底部描述文字，可为null
     * @param destPath 保存二维码地址（没有该目录会自动创建）
     * @param needCompress 是否压缩Logo大小
     * @throws Exception 生成二维码时发生错误
     */
    public static void encode(String content, @Nullable BufferedImage logoImage, @Nullable String bottomDes, String destPath,
                              boolean needCompress) throws Exception {

        BufferedImage image = QRCodeUtil.createImage(content, logoImage, bottomDes,
                needCompress);
        mkdirs(destPath);
        String file = UUID.randomUUID().toString()+".png";
        ImageIO.write(image, FORMAT_NAME, new File(destPath+"/"+file));
    }

    /**
     * 生成二维码（指定文件名）
     * <p>
     * 根据Logo图片对象生成二维码，支持添加底部描述文字和指定文件名。
     * </p>
     *
     * @param content 内容
     * @param logoImage 图片源LOGO，可为null
     * @param bottomDes 底部描述文字，可为null
     * @param picName 图片名
     * @param destPath 保存二维码地址（没有该目录会自动创建）
     * @param needCompress 是否压缩Logo大小
     * @throws Exception 生成二维码时发生错误
     */
    public static void encode(String content, @Nullable BufferedImage logoImage, @Nullable String bottomDes, String picName, String destPath,
                              boolean needCompress) throws Exception {
        BufferedImage image=QRCodeUtil.createImage(content, logoImage,bottomDes,
                needCompress);

        mkdirs(destPath);
        String file = picName+".png";
        ImageIO.write(image, FORMAT_NAME, new File(destPath+"/"+file));
    }


    /**
     * 生成二维码（最简单方式）
     * <p>
     * 不添加Logo和底部描述文字的简单二维码生成方式。
     * </p>
     *
     * @param content 内容
     * @param destPath 保存二维码地址（没有该目录会自动创建）
     * @throws Exception 生成二维码时发生错误
     */
    public static void encode(String content, String destPath) throws Exception {
        QRCodeUtil.encode(content, null,null,destPath, false);
    }


    /**
     * 生成二维码（以Logo路径，输出到流）
     * <p>
     * 根据Logo文件路径生成二维码并输出到指定流。
     * </p>
     *
     * @param content 内容
     * @param imgPath Logo图片地址，可为null
     * @param bottomDes 底部描述文字，可为null
     * @param output 输出流
     * @param needCompress 是否压缩Logo大小
     * @throws Exception 生成二维码时发生错误
     */
    public static void encodeOfPath(String content, @Nullable String imgPath, @Nullable String bottomDes,
                                    OutputStream output, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, CheckImageExist(imgPath), bottomDes,
                needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    /**
     * 解析二维码
     *
     * @param file 图片文件
     * @return 二维码内容，解析失败返回null
     * @throws Exception 解析二维码时发生错误
     */
    @Nullable
    public static String decode(File file) throws Exception {
        BufferedImage image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Map<DecodeHintType, Object> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        return result.getText();
    }

    /**
     * 解析二维码
     *
     * @param path 图片地址
     * @return 二维码内容，解析失败返回null
     * @throws Exception 解析二维码时发生错误
     */
    @Nullable
    public static String decode(String path) throws Exception {
        return QRCodeUtil.decode(new File(path));
    }

}