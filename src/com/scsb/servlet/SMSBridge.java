package com.scsb.servlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.iglomo.sms.service.SmsService;
import com.iglomo.sms.service.SmsServiceImpl;
import com.iglomo.sms.webservice.model.SMSResponse;
import com.scsb.bean.Order;
import com.scsb.bean.Smppuser;
import com.scsb.dao.DatabaseDAO;
import com.scsb.dao.OrderDAO;
import com.scsb.dao.SmppuserDAO;
import com.tecnick.htmlutils.htmlentities.HTMLEntities;

public class SMSBridge extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	

	private SMSResponse resItem;
	
	private SmsService smsService = new SmsServiceImpl();
	
	private Connection con;
	private PreparedStatement pt;
	private ResultSet rs;
	private String retstr[] = {"","系統已接受 等待發送","接收簡訊手機號碼格式錯誤","簡訊內容為空白","","廠商登入帳號或已加密之登入密碼錯誤","點數不足","","","","","系統問題","現在時間非可發送時間"};
	private String status = "0";
	@Override
	protected void finalize() {
		try {
			rs.close();
			pt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);	
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8"); 
		PrintWriter out = resp.getWriter();
		String username = req.getParameter("MemberID");
		String password = req.getParameter("Password");
		String number = req.getParameter("receiver");
		String upass="";
		String msg = req.getParameter("SMSMessage");
		String sql = new String();
		String secret = "";
		int upoints = 0;
		int uid = 0;
		int spent_points = 0;
		int rsn =0;
		String area = "0";
		int order_id=0;
		if( checkparam(username) == true )
		{
			status ="5";
			out.println("status=" + status + "&retstr=" + retstr[Integer.parseInt(status)]);
			System.out.println("Step A:" + sql);
			return;
		}
		username = username.replace(" ", "");
		sql = "select * from smppuser where username='"  + username + "'";
		con = DatabaseDAO.getConnection();
		try {
			pt = con.prepareStatement(sql);
			rs = pt.executeQuery();
			if(rs.next())
			{
				upass = rs.getString("password");
				upoints = rs.getInt("point");
				uid = rs.getInt("id");

			}
			else
			{
				status ="5";
				out.println("status=" + status + "&retstr=" + retstr[Integer.parseInt(status)]);
				System.out.println("Step B:" + sql);
				return;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally
		{
			try {
				rs.close();
				pt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		secret =MD5(username+":"+password+":"+number);
		if( secret.equals(MD5(username+":"+upass+":"+number)) == false)
		{
			status ="5";
			out.println("status=" + status + "&retstr=" + retstr[Integer.parseInt(status)]);
			System.out.println("Step C:" + sql);
			return;
		}
		
		if( upoints <= 0 )
		{
			status ="6";
			out.println("status=" + status + "&retstr=" + retstr[Integer.parseInt(status)]);
			System.out.println("Step D:" + sql);
			return;
		}
		
		try
		{
			Integer.parseInt(number);
		}
		catch(NumberFormatException e)
		{
			status ="2";
			out.println("status=" + status + "&retstr=" + retstr[Integer.parseInt(status)]);
			return;
		}
		if(number.charAt(0) == '0')
		{
			area="0";
			if( msg.length() > 70 )
			{
				if( msg.length() % 70 > 0 )
				{
					spent_points = 1 * ( msg.length() / 70 + 1);
				}
				else
				{
					spent_points = 1 * ( msg.length() / 70);
				}
			}
			else
			{
				spent_points = 1;
			}
		}
		else
		{
			area="1";
			if( msg.length() > 70 )
			{
				if( msg.length() % 70 > 0 )
				{
					spent_points = 3 * ( msg.length() / 70 + 1);
				}
				else
				{
					spent_points = 3 * ( msg.length() / 70);
				}
			}
			else
			{
				spent_points = 3;
			}
		}		
		sql = "insert into scsbsmpp.order(create_time, create_ip, spent_point,mode,req_msg,smppuser_id, number, area)"+
			" values (CURRENT_TIMESTAMP,'" + req.getRemoteAddr() + "','" + spent_points + "','1','" + msg + "','" + uid +"','" + number + "','" + area + "');";
		try {
			pt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
			rsn = pt.executeUpdate();
			if( rsn > 0 )
			{
				rs = pt.getGeneratedKeys();
				if( rs.next())	order_id=rs.getInt(1);
				rs.close();
				Document doc = DocumentHelper.createDocument();
				Element root = doc.addElement("SMSREQUEST"); // 建立 <自訂XML>
				Element root_username = root.addElement("USERNAME");
				root_username.addText("S2TTest");
				Element root_password = root.addElement("PASSWORD");
				root_password.addText("s2Ttest");
				Element root_orgcode = root.addElement("ORGCODE");
				root_orgcode.addText("0");				
				Element root_data = root.addElement("DATA");
				Element data_item = root_data.addElement("ITEM");
				Element item_schedule = data_item.addElement("SCHEDULE");
				item_schedule.addText("0");
				Element item_multiple = data_item.addElement("MULTIPLE");
				item_multiple.addText("0");
				Element item_MSG = data_item.addElement("MSG");
				item_MSG.addText(msg);
				Element item_number = data_item.addElement("PHONE");
				number=number.replaceFirst("0", "886");
				item_number.addText(number);
				Element root_remark = root.addElement("REMARK");
				root_remark.addText(" ");
			
				//	Element item_PHONE = root.addElement("PHONE");
				//	item_multiple.addText(o.getReq_msg());
			
				System.out.println(doc.asXML());
			
				String responseXML = this.smsService.send(doc.asXML());
				System.out.println(responseXML);
				try{
					this.resItem  = this.decodeResponse(responseXML);
					responseXML = this.encodeResponse(this.resItem);
					System.out.println(resItem.getUid());
					String rescode = resItem.getUid();
					rescode = rescode.replace("S2=", "");
					sql = "update scsbsmpp.order set uid ='" + rescode + "' where order_id ='" + order_id + "'";				
					pt=con.prepareStatement(sql);
					pt.executeUpdate();
				} catch (SQLException e) {
					System.out.println("SQL wrong!!\n\rError MSG:" + e.getLocalizedMessage() );
					System.out.println("SQL:" + sql + "\n\rResponse:" + resItem.getUid() + "\n\r");
					status ="11";
					out.println("status=" + status + "&retstr=" + retstr[Integer.parseInt(status)]);
				} catch(Exception e){
					System.out.println("Something wrong!!\n\rError MSG:" + e.getLocalizedMessage() );
					System.out.println("SQL:" + sql + "\n\rResponse:" + resItem.getUid() + "\n\r");
					status ="11";
					out.println("status=" + status + "&retstr=" + retstr[Integer.parseInt(status)]);
				}
				FileWriter fw = new FileWriter("D://OrderMessage//order-"+ order_id + "-" + System.currentTimeMillis()+".xml"); // 可自訂
				// 下面這行：預設自動換行、Tab 為 2 個空白
				// 	OutputFormat of = OutputFormat.createPrettyPrint(); // 格式化XML
				OutputFormat of = new OutputFormat(); // 格式化XML
				of.setIndentSize(4); // 設定 Tab 為 4 個空白
				of.setNewlines(true);// 設定 自動換行
				XMLWriter xw = new XMLWriter(fw, of);
				xw.write(doc);
				xw.close();
				fw.close();
				status ="1";
				out.println("status=" + status + "&retstr=" + retstr[Integer.parseInt(status)]);
			}else{
				status ="11";
				out.println("status=" + status + "&retstr=" + retstr[Integer.parseInt(status)]);
				System.out.println("Step F:" + sql);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			status ="11";
			out.println("status=" + status + "&retstr=" + retstr[Integer.parseInt(status)]);
			System.out.println("Step G:" + sql);
		}
		finally
		{
			try {
				pt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
	
	
	/***
	 *  Convert data from XML to bean.
	 *  Replace &lt; &gt; entities with < > characters.
	 */
	private SMSResponse decodeResponse(String responseXML) throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(SMSResponse.class);

		Unmarshaller um = context.createUnmarshaller();

		StringReader sr = new StringReader(
				HTMLEntities.unhtmlAngleBrackets(responseXML));

		SMSResponse res = (SMSResponse) um.unmarshal(sr);

		return res;

	}
	
	private String encodeResponse(SMSResponse res) throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(SMSResponse.class);

		Marshaller m = context.createMarshaller();

		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		StringWriter sw = new StringWriter();

		m.marshal(res, sw);

		return sw.toString();

	}

	private boolean checkparam(String str)
	{
		String blacklist[] = {"alter","delete","drop",
                "nchar","varchar","nvarchar",
                "fetch","insert","kill",
                "select","sysobjects","syscolumns",
                "update","alert","script","onmouseover",
				"--", ";", "/*", "*/", "@@", "@",
				"char", "begin", "cast", "create", "cursor",
				"declare", "delete", "drop", "end", "exec",
				"execute", "sys", "table", "update"};
		 for (int i = 0; i < blacklist.length; i++)
         {
             if (blacklist[i].contains(str) == true )
             {
            	 return true;
             }
		}
		return false;
	}
	
	public static String MD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
