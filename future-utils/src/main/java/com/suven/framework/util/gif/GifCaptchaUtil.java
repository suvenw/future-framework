package com.suven.framework.util.gif;

import com.suven.framework.util.random.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

public class GifCaptchaUtil {
    private static final Logger logger = LoggerFactory.getLogger(GifCaptchaUtil.class);

    private Font font = new Font("宋体", Font.BOLD, 20); // 字体
    private int width = 120; // 验证码显示长度
    private int height = 40; // 验证码显示高度
    private String validCode = ""; // 当前的字符串
    private int delay = 100; // 帧延迟 (默认100)
    private int quality = 10;//量化器取样间隔 - 默认是10ms
    private int repeat = 0; // 帧循环次数
    private int minColor =0;//设置随机颜色时，最小的取色范围
    private int maxColor = 255;//设置随机颜色时，最大的取色范围
    private int right = 5; //设置字符最右边的相对位置---相对原始位置 ，默认为0
    private int LENGTH = 5;//验证码长度

    /**
     * 空参构造函数
     */
    public GifCaptchaUtil() {
    }

    /**
     * 可以设置验证码宽度，高度的构造函数
     * @param width -验证码宽度
     * @param height -验证码高度
     */
    public GifCaptchaUtil(int width, int height) {
        this.width = width;
        this.height = height;
    }
    /**
     *
     * @param width -验证码宽度
     * @param height -验证码高度
     * @param font -字体
     */
    public GifCaptchaUtil(int width, int height, Font font) {
        this(width, height);
        this.font = font;
    }

    /**
     * @param width -验证码宽度
     * @param height -验证码高度
     * @param font -字体
     * @param delay -帧延迟
     */
    public GifCaptchaUtil(int width, int height, Font font, int delay) {
        this(width, height,font);
        this.delay = delay;
    }

    public Font getFont() {
        return font;
    }

    /**
     * 设置字体
     * @param font
     */
    public void setFont(Font font) {
        this.font = font;
    }

    public int getWidth() {
        return width;
    }
    /**
     * 设置验证码宽度
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * 设置验证码高度
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    public String getValidCode() {
        return validCode;
    }

    /**
     * 设置验证码字符
     * @param chars
     */
    public void setValidCode(String chars) {
        this.validCode = chars;
    }

    public int getDelay() {
        return delay;
    }

    /**
     * 设置每一帧之间的延迟时间,或改变它的后续帧(适用于最后一帧添加)。
     * @param delay 单位是毫秒
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getQuality() {
        return quality;
    }

    /**
     * 设置图像的颜色量化(转换质量 由GIF规范允许的最大256种颜色)。
     * 低的值(最小值= 1)产生更好的颜色,但处理显著缓慢。
     * 10是默认,并产生良好的颜色而且有以合理的速度。
     * 值更大(大于20)不产生显著的改善速度
     * @param quality 大于1
     */
    public void setQuality(int quality) {
        if(quality<1){
            quality=1;
        }
        this.quality = quality;
    }

    public int getRepeat() {
        return repeat;
    }

    /**
     * 设置GIF帧应该播放的次数。
     * 默认是 0; 0意味着无限循环。
     * 必须在添加的第一个图像之前被调用。
     * @param repeat 必须大于等于0
     */
    public void setRepeat(int repeat) {
        if (repeat>=0) {
            this.repeat = repeat;
        }
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getMaxColor() {
        return maxColor;
    }

    public void setMaxColor(int maxColor) {
        this.maxColor = maxColor;
    }

    public int getMinColor() {
        return minColor;
    }

    public void setMinColor(int minColor) {
        this.minColor = minColor;
    }

    /**
     * 给定一个输出流 输入图片
     * @param os
     */
    public void out(OutputStream os) {
        try {
            AnimatedGifEncoder gifEncoder = new AnimatedGifEncoder();// gif编码类
            //生成字符
            gifEncoder.start(os);
            gifEncoder.setQuality(quality);//设置量化器取样间隔
            gifEncoder.setDelay(delay);//设置帧延迟
            gifEncoder.setRepeat(repeat);//帧循环次数
            BufferedImage frame;
            char[] rands = createValidCodeChar();
            Color fontcolor[] = new Color[validCode.length()];
            for (int i = 0; i < validCode.length(); i++) {
                fontcolor[i] = getRandomColor(minColor,maxColor);
            }
            for (int i = 0; i < validCode.length(); i++) {
                frame = graphicsImage(fontcolor, rands, i);
                gifEncoder.addFrame(frame);
                frame.flush();
            }
            gifEncoder.finish();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                logger.error("验证码生成异常",e);
            }
        }

    }

    /**
     * 画随机码图
     *
     * @param fontcolor 随机字体颜色
     * @param strs      字符数组
     * @param flag      透明度使用
     * @return BufferedImage
     */
    private BufferedImage graphicsImage(Color[] fontcolor, char[] strs, int flag) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //或得图形上下文
        Graphics2D g2d=image.createGraphics();
        //Graphics2D g2d = (Graphics2D) image.getGraphics();
        //利用指定颜色填充背景
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        AlphaComposite ac;
        float y = (height >> 1) + (font.getSize() >> 1) ;// 字符的y坐标
        float m = (width-(validCode.length()*font.getSize()))/ validCode.length();
        float x = m/2;//字符的x坐标
        g2d.setFont(font);
        for (int i = 0; i < validCode.length(); i++) {
            ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getPellucidity(flag, i));
            g2d.setComposite(ac);
            g2d.setColor(fontcolor[i]);
            g2d.drawOval(RandomUtils.num(width), RandomUtils.num(height), RandomUtils.num(5,30), 5 + RandomUtils.num(5,30));//绘制椭圆边框
            g2d.drawString(strs[i] + "",x+(font.getSize()+m)*i+right,y);
        }
        g2d.dispose();
        return image;
    }

    /**
     * 获取透明度,从0到1,自动计算步长
     * @return float 透明度
     */
    private float getPellucidity(int i, int j) {
        int num = i + j;
        float r = (float) 1 / validCode.length(), s = (validCode.length() + 1) * r;
        return num > validCode.length() ? (num * r - s) : num * r;
    }


    /**
     * 生成随机字符数组
     * @return 字符数组
     */
    protected char[] createValidCodeChar() {
        validCode = RandomUtils.randomString2(LENGTH);
        return validCode.toCharArray();
    }

    /**
     * 通过给定范围获得随机的颜色
     * @return Color 获得随机的颜色
     */
    protected Color getRandomColor(int min, int max) {
        if (min > 255) {
            min = 255;
        }
        if (max > 255) {
            max = 255;
        }
        if(min<0){
            min=0;
        }
        if(max<0){
            max=0;
        }
        if(min>max){
            min=0;
            max=255;
        }
        return new Color(min + RandomUtils.num(max - min), min + RandomUtils.num(max - min), min + RandomUtils.num(max - min));
    }
}