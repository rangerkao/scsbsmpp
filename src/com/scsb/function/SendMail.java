package com.scsb.function;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.scsb.bean.Admin;
import com.scsb.bean.Smppuser;


public class SendMail {

	public void sendMemberVerificationMail(Smppuser smppuser,String link) {
		
		InputStream is = this.getClass().getResourceAsStream("/com/scsb/function/mailInfo.properties");
		Properties prop = new Properties();
		try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		final String host = prop.getProperty("host");
		final int port = Integer.parseInt(prop.getProperty("port"));
		final String username = prop.getProperty("username");
		final String password = prop.getProperty("password");
		String content = "★歡迎您成為本站的會員★<br/>"+
						 "您所設定的帳號："+smppuser.getUsername()+"<br/>"+
						 "您所設定的密碼："+smppuser.getPassword()+"<br/>"+
						 "為維護您的隱私與權利，請確認以上資料無誤並點擊下列網址以啟動您的帳號，謝謝。<br/>"+
						 "啟動帳號：<a href='"+link+"'>"+link+"</a><br/>"+
						 "如果上列連結無法使用，請您將下列網址複製並貼到瀏覽器的網址列中：<br/>"+link;
		
		Properties props = new Properties();
		props.put("mail.smtp.host",host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", port);

		Session session = Session.getInstance(props,new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(username, password);
			}});
		
		try{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(smppuser.getEmail()));
			message.setSubject("★歡迎您成為本站的會員★");
			MimeMultipart mul = new MimeMultipart();
			MimeBodyPart mbp = new MimeBodyPart();
			mbp.setContent(content,"text/html;charset=utf-8");
			mul.addBodyPart(mbp);
			message.setContent(mul);
			message.saveChanges();
			Transport transport = session.getTransport("smtp");
			transport.connect(host,port,username,password);
			Transport.send(message);
			transport.close();
			
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (javax.mail.MessagingException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
