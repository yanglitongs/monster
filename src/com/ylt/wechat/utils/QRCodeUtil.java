package com.ylt.wechat.utils;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/** 
 * �Զ������ɶ�ά�� 
 * @author admin 
 * 
 */
public class QRCodeUtil {
	private static final String CHARSET = "utf-8";    
    private static final String FORMAT_NAME = "PNG";    
    // ��ά��ߴ�    
    private static final int QRCODE_SIZE = 200;    
    // LOGO���    
    private static final int WIDTH = 60;    
    // LOGO�߶�    
    private static final int HEIGHT = 60;    
    
    private static BufferedImage createImage(String content, String imgPath,    
            boolean needCompress) throws Exception {    
        return createImage(content,  imgPath,    
                 needCompress,QRCODE_SIZE);    
    }    
    private static BufferedImage createImage(String content, String imgPath,    
            boolean needCompress,int qrcodeSize) throws Exception {    
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();    
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);    
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);    
        hints.put(EncodeHintType.MARGIN, 1);    
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,    
                BarcodeFormat.QR_CODE, qrcodeSize, qrcodeSize, hints);    
        int width = bitMatrix.getWidth();    
        int height = bitMatrix.getHeight();    
        BufferedImage image = new BufferedImage(width, height,    
                BufferedImage.TYPE_INT_RGB);    
        for (int x = 0; x < width; x++) {    
            for (int y = 0; y < height; y++) {    
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000    
                        : 0xFFFFFFFF);    
            }    
        }    
        if (imgPath == null || "".equals(imgPath)) {    
            return image;    
        }    
        // ����ͼƬ    
        QRCodeUtil.insertImage(image, imgPath, needCompress,qrcodeSize);    
        return image;    
    }   
    /**  
     * ����LOGO  
     *   
     * @param source  
     *            ��ά��ͼƬ  
     * @param imgPath  
     *            LOGOͼƬ��ַ  
     * @param needCompress  
     *            �Ƿ�ѹ��  
     * @throws Exception  
     */    
    private static void insertImage(BufferedImage source, String imgPath,    
            boolean needCompress,int qrcodeSize) throws Exception {    
        File file = new File(imgPath);    
        if (!file.exists()) {    
            return;    
        }    
        Image src = ImageIO.read(new File(imgPath));    
        int width = src.getWidth(null);    
        int height = src.getHeight(null);    
        if (needCompress) { // ѹ��LOGO    
            if (width > qrcodeSize/QRCODE_SIZE*WIDTH) {    
                width = qrcodeSize/QRCODE_SIZE*WIDTH;    
            }    
            if (height > qrcodeSize/QRCODE_SIZE*HEIGHT) {    
                height = qrcodeSize/QRCODE_SIZE*HEIGHT;    
            }    
            Image image = src.getScaledInstance(width, height,    
                    Image.SCALE_SMOOTH);    
            BufferedImage tag = new BufferedImage(width, height,    
                    BufferedImage.TYPE_INT_RGB);    
            Graphics g = tag.getGraphics();    
            g.drawImage(image, 0, 0, null); // ������С���ͼ    
            g.dispose();    
            src = image;    
        }    
        // ����LOGO    
        Graphics2D graph = source.createGraphics();    
        int x = (qrcodeSize - width) / 2;    
        int y = (qrcodeSize - height) / 2;    
        graph.drawImage(src, x, y, width, height, null);    
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);    
        graph.setStroke(new BasicStroke(3f));    
        graph.draw(shape);    
        graph.dispose();    
    }    
    
    /**  
     * ���ɶ�ά��(��ǶLOGO)  
     *   
     * @param content  
     *            ����  
     * @param imgPath  
     *            LOGO��ַ  
     * @param destPath  
     *            �洢��ַ  
     * @param needCompress  
     *            �Ƿ�ѹ��LOGO  
     * @throws Exception  
     */    
    public static void encode(String content, String imgPath, String destPath,    
            boolean needCompress) throws Exception {    
        BufferedImage image = QRCodeUtil.createImage(content, imgPath,    
                needCompress);    
    //    FileUtils.mkdirs(destPath);    
        FileUtils.mkdir(destPath, false);  
        ImageIO.write(image, FORMAT_NAME, new File(destPath));    
    }    
    /** 
     *  
     * @author eason     
     * @param content   ���� 
     * @param imgPath   LOGO��ַ  
     * @param destPath  �洢��ַ 
     * @param needCompress �Ƿ�ѹ��LOGO 
     * @param qrcodeSize ��ά���С ��� 
     * @throws Exception 
     */  
    public static void encode(String content, String imgPath, String destPath,    
            boolean needCompress,int qrcodeSize) throws Exception {    
        BufferedImage image = QRCodeUtil.createImage(content, imgPath,    
                needCompress,qrcodeSize);    
        FileUtils.mkdir(destPath, false);  
        ImageIO.write(image, FORMAT_NAME, new File(destPath));    
    }    
    
    
    /**  
     * ���ɶ�ά��(��ǶLOGO)  
     *   
     * @param content  
     *            ����  
     * @param imgPath  
     *            LOGO��ַ  
     * @param destPath  
     *            �洢��ַ  
     * @throws Exception  
     */    
    public static void encode(String content, String imgPath, String destPath)    
            throws Exception {    
        QRCodeUtil.encode(content, imgPath, destPath, false);    
    }    
    public static void encode(String content, String imgPath, String destPath,int qrcodeSize)    
            throws Exception {    
        QRCodeUtil.encode(content, imgPath, destPath, false);    
    }   
    /**  
     * ���ɶ�ά��  
     *   
     * @param content  
     *            ����  
     * @param destPath  
     *            �洢��ַ  
     * @param needCompress  
     *            �Ƿ�ѹ��LOGO  
     * @throws Exception  
     */    
    public static void encode(String content, String destPath,    
            boolean needCompress) throws Exception {    
        QRCodeUtil.encode(content, null, destPath, needCompress);    
    }    
    
    /**  
     * ���ɶ�ά��  
     *   
     * @param content  
     *            ����  
     * @param destPath  
     *            �洢��ַ  
     * @throws Exception  
     */    
    public static void encode(String content, String destPath) throws Exception {    
        QRCodeUtil.encode(content, null, destPath, false);    
    }    
    
    /**  
     * ���ɶ�ά��(��ǶLOGO)  
     *   
     * @param content  
     *            ����  
     * @param imgPath  
     *            LOGO��ַ  
     * @param output  
     *            �����  
     * @param needCompress  
     *            �Ƿ�ѹ��LOGO  
     * @throws Exception  
     */    
    public static void encode(String content, String imgPath,    
            OutputStream output, boolean needCompress) throws Exception {    
        BufferedImage image = QRCodeUtil.createImage(content, imgPath,    
                needCompress);    
        ImageIO.write(image, FORMAT_NAME, output);    
    }    
    /** 
     *  
     * @author eason     
     * @param content  ����  
     * @param imgPath  LOGO��ַ  
     * @param output ����� 
     * @param needCompress  �Ƿ�ѹ��LOGO  
     * @param qrcodeSize 
     * @throws Exception 
     */  
    public static void encode(String content, String imgPath,    
            OutputStream output, boolean needCompress,int qrcodeSize) throws Exception {    
        BufferedImage image = QRCodeUtil.createImage(content, imgPath,    
                needCompress,qrcodeSize);   
        ImageIO.write(image, FORMAT_NAME, output);    
    }    
    public static void encode(String content, OutputStream output,int qrcodeSize)    
            throws Exception {    
        QRCodeUtil.encode(content, null, output, false,qrcodeSize);    
    }    
    /**  
     * ���ɶ�ά��  
     *   
     * @param content  
     *            ����  
     * @param output  
     *            �����  
     * @throws Exception  
     */    
    public static void encode(String content, OutputStream output)    
            throws Exception {    
        QRCodeUtil.encode(content, null, output, false);    
    }    
    
    /**  
     * ������ά��  
     *   
     * @param file  
     *            ��ά��ͼƬ  
     * @return  
     * @throws Exception  
     */    
    public static String decode(File file) throws Exception {    
        BufferedImage image;    
        image = ImageIO.read(file);    
        if (image == null) {    
            return null;    
        }    
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(    
                image);    
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));    
        Result result;    
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();    
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);    
        result = new MultiFormatReader().decode(bitmap, hints);    
        String resultStr = result.getText();    
        return resultStr;    
    }    
    
    /**  
     * ������ά��  
     *   
     * @param path  
     *            ��ά��ͼƬ��ַ  
     * @return  
     * @throws Exception  
     */    
    public static String decode(String path) throws Exception {    
        return QRCodeUtil.decode(new File(path));    
    }    
    
    public static void main(String[] args) throws Exception {    
        String text = "{\"partId\":234}";    
        QRCodeUtil.encode(text, "/Users/yulong/git/myutils/logo_180x180.png", "/Users/yulong/git/myutils/logo3.png", false,600);    
    }    
}
