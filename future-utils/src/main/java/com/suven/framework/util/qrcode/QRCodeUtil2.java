package com.suven.framework.util.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class QRCodeUtil2 {
	
	private static int width = 200;// 图像宽度  
	private static int height = 200;// 图像高度  
	private static String format = "png";// 图像类型 
	private static final String CHARACTERSET = "utf-8"; //数据编码
	private static int descFontSize = 30;
	private static int BLACK = 0x000000;
	/**
	 * 生成二维码
	 * @param data 内容
	 * @param desc 注释
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 */
	public static String encode(Map<String,Object> data,String desc,File file) throws WriterException, IOException{
//		JSONObject json = new JSONObject(data);
//		String content = json.toJSONString();// 内容  
//		String content = get2(data);
		String content = "";
		Set<String> keySet = data.keySet();
		for (String key : keySet) {
			content += key +"," + data.get(key) + ";";
		}
        content = content.substring(0, content.length() - 1);

		desc = new String(desc.getBytes(),CHARACTERSET);

        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType,Object>();
        hints.put(EncodeHintType.CHARACTER_SET, CHARACTERSET);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,  
                BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
//        MatrixToImageWriter.writeToStream(bitMatrix, format, baos);// 输出图像  
//        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        
        int[] locationTopLeft = bitMatrix.getTopLeftOnBit();  
        int[] locationBottomRight = bitMatrix.getBottomRightOnBit();  
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        MatrixToImageWriter.writeToStream(bitMatrix, format, baos);// 输出图像  
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        BufferedImage bm = ImageIO.read(bais);
        
        Graphics g = bm.getGraphics();
        InputStream is = null;
        if(!StringUtils.isEmpty(desc)){  
            int whiteWidth = height - locationBottomRight[1];
//          width = info.getBottomEnd()[0]-infoF.getBottomStart()[0];
//          height = info.getBottomEnd()[1]+1;  
            Font font = new Font("宋体", Font.BOLD, descFontSize);  
            int fontHeight = g.getFontMetrics(font).getHeight();  
            //计算需要多少行  
            int lineNum = 1;  
            int currentLineLen = 0;
            for(int i = 0;i< desc.length();i++){
                char c = desc.charAt(i);  
                int charWidth = g.getFontMetrics(font).charWidth(c);  
                if(currentLineLen+charWidth > width){
                    lineNum++;  
                    currentLineLen = 0;  
                    continue;  
                }  
                currentLineLen += charWidth;  
            }  
            int totalFontHeight = fontHeight*lineNum;  
            int wordTopMargin = 0;  
            BufferedImage bm1 = new BufferedImage(width, height+totalFontHeight+wordTopMargin-whiteWidth, BufferedImage.TYPE_INT_RGB);  
            Graphics g1 = bm1.getGraphics();  
            if(totalFontHeight+wordTopMargin-whiteWidth>0){  
            g1.setColor(Color.WHITE);  
            g1.fillRect(0, height, width, totalFontHeight+wordTopMargin-whiteWidth);  
            }  
            g1.setColor(new Color(BLACK));  
            g1.setFont(font);  
            g1.drawImage(bm, 0, 0, null);  
            /*width = locationBottomRight[0]-locationTopLeft[0];  
            height = locationBottomRight[1]+1; */ 
            int cwidth = locationBottomRight[0] - locationTopLeft[0];
            int cheight = locationBottomRight[1] + 1;
            currentLineLen = 0;  
            int currentLineIndex = 0;  
            int baseLo = g1.getFontMetrics().getAscent();  
            for(int i=0;i<desc.length();i++){  
                String c = new String(desc.substring(i, i+1).getBytes(),CHARACTERSET);  
                int charWidth = g.getFontMetrics(font).stringWidth(c);  
                if(currentLineLen+charWidth>cwidth){  
                    currentLineIndex++;  
                    currentLineLen = 0;  
                    g1.drawString(c, currentLineLen + whiteWidth, cheight+baseLo+fontHeight*(currentLineIndex)+wordTopMargin);  
                    currentLineLen = charWidth;  
                    continue;  
                }  
                g1.drawString(c, currentLineLen+whiteWidth, cheight+baseLo+fontHeight*(currentLineIndex) + wordTopMargin);  
                currentLineLen += charWidth;  
            }  
            g1.dispose();  
            bm = bm1;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageOutputStream imOut = ImageIO.createImageOutputStream(os);
            ImageIO.write(bm,format,imOut);
//            is = new ByteArrayInputStream(os.toByteArray());
            FileOutputStream fos1=new FileOutputStream(file);
            fos1.write(os.toByteArray());
        }
//        String uploadFile = OssUtils.uploadFile(is,format);
//        return uploadFile;
        return null;

	}

//	public static int getWidth() {
//		return width;
//	}
//	public static void setWidth(int width) {
//		QRCodeUtil.width = width;
//	}
//	public static int getHeight() {
//		return height;
//	}
//	public static void setHeight(int height) {
//		QRCodeUtil.height = height;
//	}
//	public static String getFormat() {
//		return format;
//	}
//	public static void setFormat(String format) {
//		QRCodeUtil.format = format;
//	}
//	/**
//	 * 解析
//	 * @param data
//	 * @return
//	 */
//	public static Map<String,String> getMap(String data){
//		String[] params = data.split(";");
//		Map<String, String> map = new HashMap<>();
//		for (String param : params) {
//			String[] p = param.split(",");
//			map.put(p[0], p[1]);
//		}
//		return map;
//	}

}
