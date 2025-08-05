package com.suven.framework.file.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 
 * ClassName: OSSUploadUtil
 * Description: 阿里云OSS文件上传工具类
 * @author AggerChen
 * date 2016年11月3日 下午12:03:24
 */
public class QRCodeUtil {

    private static class OSSUpload {
        private static final QRCodeUtil INSTANCE = new QRCodeUtil();
    }

    public static QRCodeUtil getInstance() {
        QRCodeUtil oss =  OSSUpload.INSTANCE;
        return oss;
    }

    private QRCodeUtil(){}


    /**
     * 生成不带logo的二维码图片
     * @param qrUrl
     * @param no
     * @return
     */
    public static BufferedImage drawLogoQRCode(String qrUrl , int no) {
        return drawLogoQRCode(qrUrl,no,null,400, 400);
    }


    public static BufferedImage drawLogoQRCode(String qrUrl , int no , InputStream logoCodeUrl , int qrWidth, int qrHeight) {

        try {
            String qrCodeUrl = qrUrl.contains("?") ? (qrUrl + "&code=" + no ) : (qrUrl + "?code=" + no );
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            BitMatrix bm = multiFormatWriter.encode(qrCodeUrl , BarcodeFormat.QR_CODE, qrWidth, qrHeight, hints);
            BufferedImage image = new BufferedImage(qrWidth, qrHeight, BufferedImage.TYPE_INT_RGB);

            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
            for (int x = 0; x < qrWidth; x++) {
                for (int y = 0; y < qrHeight; y++) {
                    image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            int width = image.getWidth();
            int height = image.getHeight();
            if (Objects.nonNull(logoCodeUrl) ) {
                // 构建绘图对象
                Graphics2D g = image.createGraphics();
                // 读取Logo图片
                BufferedImage logo = ImageIO.read(logoCodeUrl);
                // 开始绘制logo图片
                g.drawImage(logo, width * 2 / 7, height * 2 / 7, width * 2 / 5, height * 2 / 5, null);
                g.dispose();
                logo.flush();
            }
            image.flush();
            return image;
//            ImageIO.write(image, "jpg", new File(url + no + ".jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 用于设置QR二维码参数
     */
    private static Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>() {
        private static final long serialVersionUID = 1L;

        {
            put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
            put(EncodeHintType.CHARACTER_SET, "utf-8");// 设置编码方式
            put(EncodeHintType.MARGIN, 0);
        }
    };


}