package com.suven.framework.util.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 制作带有图片生成二维码
 * 
 * @author Administrator
 * 
 */
public class QRCodeImage {
    // 代码实现
    // 图片宽度的一般
    private static  int IMAGE_WIDTH = 80;
    private static  int IMAGE_HEIGHT = 80;
    private static final int IMAGE_HALF_WIDTH = IMAGE_WIDTH / 2;
    private static final int FRAME_WIDTH = 2;
    private static final String  CHARACTER_SET = "UTF-8";
    private static int width = 400;
    private static int height = 450;
    private static String formatName = "png";

    public static QRCodeImage build(){
        return new QRCodeImage();
    }

    public static QRCodeImage build(int qrWidth,int qrHeight,int logoWidth,int logoHeight, String formatName){
        QRCodeImage qr =  new QRCodeImage();
        qr.width = qrWidth;
        qr.height = qrHeight;
        qr.formatName = formatName;
        qr.IMAGE_WIDTH = logoWidth;
        qr.IMAGE_HEIGHT = logoHeight;
        return qr;
    }

    // 二维码写码器
    private  MultiFormatWriter mutiWriter = new MultiFormatWriter();

    public  void encode(String content, String srcImagePath, String destImagePath) {
        try {
            BufferedImage bufferedImage = genBarcode(content, width, height, srcImagePath);
//            pressText("诗多多",  bufferedImage,formatName, Font.BOLD, Color.black, 30, width, height);
            ImageIO.write(bufferedImage, formatName, new File(destImagePath));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage genBarcode(String content, int width, int height, String srcImagePath) throws WriterException, IOException {
        // 读取源图像
        BufferedImage scaleImage = scale(srcImagePath, IMAGE_WIDTH, IMAGE_HEIGHT, true);
        int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
        for (int i = 0; i < scaleImage.getWidth(); i++) {
            for (int j = 0; j < scaleImage.getHeight(); j++) {
                srcPixels[i][j] = scaleImage.getRGB(i, j);
            }
        }

        Map<EncodeHintType, Object> hint = new HashMap<>();
        //设置UTF-8， 防止中文乱码
        hint.put(EncodeHintType.CHARACTER_SET, CHARACTER_SET);
        //设置二维码四周白色区域的大小
        hint.put(EncodeHintType.MARGIN, 2);
        //设置二维码的容错性
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //
        //生成二维码，记得调用multiFormatWriter.encode()时最后要带上hints参数，不然上面设置无效
        BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hint);

        // 二维矩阵转为一维像素数组
        int halfW = matrix.getWidth() / 2;
        int halfH = matrix.getHeight() / 2;
        int[] pixels = new int[width * height];

        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {
                // 读取图片
                if (x > halfW - IMAGE_HALF_WIDTH && x < halfW + IMAGE_HALF_WIDTH && y > halfH - IMAGE_HALF_WIDTH && y < halfH + IMAGE_HALF_WIDTH) {
                    pixels[y * width + x] = srcPixels[x - halfW + IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];
                }
                // 在图片四周形成边框
                else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH + IMAGE_HALF_WIDTH + FRAME_WIDTH) || (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH + IMAGE_HALF_WIDTH + FRAME_WIDTH) || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH - IMAGE_HALF_WIDTH + FRAME_WIDTH) || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH && y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH + IMAGE_HALF_WIDTH + FRAME_WIDTH)) {
                    pixels[y * width + x] = 0xfffffff;
                } else {
                    // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
                    pixels[y * width + x] = matrix.get(x, y) ? 0xff000000 : 0xfffffff;
                }
            }
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.getRaster().setDataElements(0, 0, width, height, pixels);

        return image;
    }

    /**
     * 把传入的原始图像按高度和宽度进行缩放，生成符合要求的图标
     * 
     * @param srcImageFile
     *            源文件地址
     * @param height
     *            目标高度
     * @param width
     *            目标宽度
     * @param hasFiller
     *            比例不对时是否需要补白：true为补白; false为不补白;
     * @throws IOException
     */
    private BufferedImage scale(String srcImageFile, int height, int width, boolean hasFiller) throws IOException {
        double ratio = 0.0; // 缩放比例
        File file = new File(srcImageFile);
        BufferedImage srcImage = ImageIO.read(file);
        Image destImage = srcImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
        // 计算比例
        if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {
            if (srcImage.getHeight() > srcImage.getWidth()) {
                ratio = ( Integer.valueOf(height)).doubleValue() / srcImage.getHeight();
            } else {
                ratio = (Integer.valueOf(width)).doubleValue() / srcImage.getWidth();
            }
            AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
            destImage = op.filter(srcImage, null);
        }
        if (hasFiller) {// 补白
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphic = image.createGraphics();
            graphic.setColor(Color.white);
            graphic.fillRect(0, 0, width, height);
            if (width == destImage.getWidth(null)) {
                graphic.drawImage(destImage, 0,
                        (height - destImage.getHeight(null)) / 2,
                        destImage.getWidth(null),
                        destImage.getHeight(null),
                        Color.white, null);
            }else{
                graphic.drawImage(destImage, (width - destImage.getWidth(null)) / 2,
                        0, destImage.getWidth(null),
                        destImage.getHeight(null),
                        Color.white, null);
            }

            graphic.dispose();
            destImage = image;
        }
        return (BufferedImage) destImage;
    }

    // 根据str,font的样式以及输出文件目录
    public BufferedImage getImage(String str, Font font,
                                         Integer width, Integer height) {
        // 创建图片
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.setClip(0, 0, width, height);
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);// 先用黑色填充整张图片,也就是背景
        g.setColor(Color.red);// 在换成黑色
        g.setFont(font);// 设置画笔字体
        /** 用于获得垂直居中y */
        Rectangle clip = g.getClipBounds();
        FontMetrics fm = g.getFontMetrics(font);
        int ascent = fm.getAscent();
        int descent = fm.getDescent();
        int y = (clip.height - (ascent + descent)) / 2 + ascent;
        for (int i = 0; i < 6; i++) {// 256 340 0 680
            g.drawString(str, i * 680, y);// 画出字符串
        }
        g.dispose();
        return image;
//    ImageIO.write(image, "png", outFile);// 输出png图片
    }
    /**
     * @param pressText 文字
     * param newImg    带文字的图片
     * @param image     需要添加文字的图片
     * @param fontStyle
     * @param color
     * @param fontSize
     * @param width
     * @param height
     * @为图片添加文字
     */
    public void pressText(String pressText,
                                 BufferedImage image, String formatName,
                                 int fontStyle, Color color,
                                 int fontSize, int width, int height) {

        //计算文字开始的位置
        //x开始的位置：（图片宽度-字体大小*字的个数）/2
        int startX = 0;
        try {
            startX = (width - (fontSize * pressText.getBytes(CHARACTER_SET).length));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //y开始的位置：图片高度-（图片高度-图片宽度）/2
        int startY = height - (height - width) / 2;

        System.out.println("startX: " + startX);
        System.out.println("startY: " + startY);
        System.out.println("height: " + height);
        System.out.println("width: " + width);
        System.out.println("fontSize: " + fontSize);
        System.out.println("pressText.length(): " + pressText.length());

        try {
            int imageW = image.getWidth();
            int imageH = image.getHeight();
            Graphics g = image.createGraphics();

            g.setColor(color);
            g.setFont(new Font("粗体", fontStyle, fontSize));
            g.drawString(pressText, startX, startY);
            g.drawImage(image, 0, 0, imageW, imageH, null);
            g.dispose();


            System.out.println("image press success");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    /**
     * 二维码解析
     * @param analyzePath    二维码路径
     * @return
     * @throws IOException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static String zxingCodeAnalyze(String analyzePath){
        MultiFormatReader formatReader = new MultiFormatReader();

        String resultStr = null;
        try {
            File file = new File(analyzePath);
            if (!file.exists())
            {
                return "二维码不存在";
            }
            BufferedImage image = ImageIO.read(file);
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType,Object> hints = new LinkedHashMap<DecodeHintType,Object>();
            // 解码设置编码方式为：utf-8，
            hints.put(DecodeHintType.CHARACTER_SET, CHARACTER_SET);
            //优化精度
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            //复杂模式，开启PURE_BARCODE模式
            hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);

            QRCodeReader reader = new QRCodeReader();
            Result result = null ;
            try {
                result = reader.decode(binaryBitmap,hints);
                resultStr = result.getText();
            } catch (ReaderException e) {
                e.printStackTrace();
            }

            System.out.println(resultStr);



        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr;
    }

    /**
     * 程序入口方法 main
     * 
     * @param args
     */
//    public static void main(String[] args) {
//        int fontStyle = Font.BOLD; //字体风格
//
//
//        //附加在图片上的文字信息
//        String text = "诗多多";
//        // 类名CodeImage.方法名encode 传入相关参数
//        // 参数1 二维码生成的跳转网站信息 参数
//        // 2 二维码生成宽 参数
//        // 3:二维码生成高度
//        // 参数4: 准备图片资源磁盘路径
//        // 参数5: 生成带有图片二维码 生成目录
//        //存放logo的文件夹
//        String path = "/Users/suven/project/workspace/openSource/top-suven/";
//        //用来存放带有logo+文字的二维码图片
//        String destImagePath = path + "newLogo.png";
//        QRCodeImage.build().encode("http://baidu.com",path+"logo.png", destImagePath);
//
//        String content =QRCodeImage.zxingCodeAnalyze(destImagePath);
//        System.out.println("========"+ content);
//    }

}
