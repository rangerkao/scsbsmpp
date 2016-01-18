package com.scsb.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class TextBufferedImageServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        //產生隨機密碼
    	resp.setHeader("Cache-Control","no-cache");
    	HttpSession session = req.getSession();
        String validCode = getRandom(4);
        //System.out.println("validCode="+validCode);
    	session.setAttribute("validCode", validCode);
        OutputStream out = resp.getOutputStream();
        try
        {
            JPEGImageEncoder encode = JPEGCodec.createJPEGEncoder(out);
            BufferedImage bi = CreateBufferedImage(validCode);
            encode.encode(bi);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * 把隨機碼寫到圖片上返回
     * 
     * @param randomPassword //隨機碼
     * @return
     */
    private BufferedImage CreateBufferedImage(String randomPassword)
    {
        int width = 80;
        int height = 40;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.clearRect(0, 0, width, height);
        // 設定背景色
        g2d.setColor(getRandColor(200,250));
        g2d.fillRect(0, 0, width, height);
        
        
        // 隨機產生155條干擾線，使圖象中的認證碼不易被其它程序探測到
        Random random = new Random();
        g2d.setColor(getRandColor(160,200));
        g2d.setFont(getFont());
        for (int i=0;i<155;i++)
        {
        	int x = random.nextInt(width);
        	int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g2d.drawLine(x,y,x+xl,y+yl);
        }
        // 取隨機產生的認證碼(4位數字)
        String sRand="";
        for (int i=0;i<4;i++){
            String rand = randomPassword.substring(i, i+1);
            sRand+=rand;
            // 將認證碼顯示到圖象中
            g2d.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));//調用函數出來的顏色相同，可能是因為種子太接近，所以只能直接生成
            g2d.drawString(rand,14*i+12,30);
        }
        
        g2d.dispose();
        return bufferedImage;
    }
    private Color getRandColor(int fc, int bc) {// 給定範圍獲得隨機顏色
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
    
    private Font getFont() {
		Random random = new Random();
		Font font[] = new Font[5];
		font[0] = new Font("Ravie", Font.PLAIN, 25);
		font[1] = new Font("Antique Olive Compact", Font.PLAIN, 25);
		font[2] = new Font("Forte", Font.PLAIN, 25);
		font[3] = new Font("Wide Latin", Font.PLAIN, 25);
		font[4] = new Font("Gill Sans Ultra Bold", Font.PLAIN, 23);
		return font[random.nextInt(5)];
	}
    /**
	 * 產生隨機碼(字母+數字)
	 * 
	 * @param length
	 *            隨機碼長度
	 * @return 隨機碼
	 */
	private  String getRandom(int length) {
		String randomCode = "";
		for (int i = 0; i < length; i++) {
			randomCode += getRandChar();
		}
		return randomCode;
	}
	public  String getRandChar() {
		int rand = (int) Math.round(Math.random() * 2);
		long itmp = 0;
		char ctmp = '\u0000';
		// 根據rand的值來決定來生成一個大寫字母、小寫字母和數字
		switch (rand) {
		// 生成大寫字母
		case 1:
			itmp = Math.round(Math.random() * 25 + 65);
			ctmp = (char) itmp;
			return String.valueOf(ctmp);
			// 生成小寫字母
		case 2:
			itmp = Math.round(Math.random() * 25 + 97);
			ctmp = (char) itmp;
			return String.valueOf(ctmp);
			// 生成數字
		default:
			itmp = Math.round(Math.random() * 9);
			return String.valueOf(itmp);
		}
	}
}
