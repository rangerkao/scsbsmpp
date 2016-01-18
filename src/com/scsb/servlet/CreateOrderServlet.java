package com.scsb.servlet;
import com.scsb.dao.SmppuserDAO;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

import com.scsb.bean.Order;
import com.scsb.bean.Smppuser;
import com.scsb.dao.DatabaseDAO;
import com.scsb.dao.OrderDAO;
import com.tecnick.htmlutils.htmlentities.HTMLEntities;
import com.iglomo.sms.service.SmsService;
import com.iglomo.sms.service.SmsServiceImpl;
import com.iglomo.sms.webservice.model.SMSResponse;




public class CreateOrderServlet  extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SMSResponse resItem;
	
	private SmsService smsService = new SmsServiceImpl();
	
	private Connection con;
	private PreparedStatement pt;
	private ResultSet rs;
	
	@Override
	protected void finalize() {
		try {
			rs.close();
			pt.close();
			con.close();	
		} catch (SQLException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		
			resp.setContentType("text/html; charset=utf-8"); 
			HttpSession session = req.getSession();
			PrintWriter out = resp.getWriter();	
			Smppuser smppuser = (Smppuser)session.getAttribute("smppuser");
			Order order = (Order)req.getAttribute("create_order");
			Order new_order = null;
			OrderDAO dao = new OrderDAO();
			String[] req_send_number = null; 
			long index = 0;
			int spent_point_default =0;
			int spent_point_real =0;
			int point =  Integer.parseInt(smppuser.getPoint());
			//System.out.println("剩餘點數："+point);
			String[] smsmsg = null;
			int smsmsg_count=0;
			int smsmsg_length=0;
			String tmpstr="";
			req_send_number = req.getParameter("req_send_number").split("\n");
			int spent_point_total=0;
			int spent_point_check=0;
			for(String number : order.getReq_send_number())
			{
				if( req.getParameter("sms_mode").equals("2") == true)
			    {
			    	if( order.getReq_msg().length() % 67 >0 )
			    	{
			    		spent_point_check = order.getReq_msg().length() / 67 + 1;
			    	}
			    	else
			    	{
			    		spent_point_check = order.getReq_msg().length() / 67 ;
			    	}
			    }
			    else
			    {
			    	if( order.getReq_msg().length() % 70 >0 )
			    	{
			    		smsmsg_count = spent_point_check = order.getReq_msg().length() / 70 + 1;
			    	}
			    	else
			    	{
			    		smsmsg_count = spent_point_check = order.getReq_msg().length() / 70 ;
			    	}
			    }
				if( number.charAt(0)=='0' && number.charAt(1)=='9' || number.charAt(0)=='8' && number.charAt(1)=='8' && number.charAt(2)=='6' && number.charAt(3)=='9')
			    {
					spent_point_check = 1 * spent_point_check;
			    }
			    else
			    {
			    	spent_point_check = 3 * spent_point_check;
			    }
				spent_point_total += spent_point_check;
			}
			if(spent_point_total > point){
				out.println("<script languate='javascript'>alert('點數不足!');location.href='order.jsp';</script>");
				return;
			}
			//2015/02/25 by streit
			//no need to limit
			//if( req_send_number.length > 100 )
			//{
			//	out.println("<script languate='javascript'>alert('一次最多1百個電話號碼!');location.href='order.jsp';</script>");
			//	return;
			//)else
			//{			
				System.out.println("smsmsg_count:" + smsmsg_count);
				if((req.getParameter("sms_mode").equals("1") == true) && ( smsmsg_count > 1))
				{
				
					for(String number : order.getReq_send_number())
					{
						smppuser = (Smppuser)session.getAttribute("smppuser");
						point =  Integer.parseInt(smppuser.getPoint());
						new_order = order;
						new_order.setReq_send_number2(number);
						smsmsg = new String[smsmsg_count];
						tmpstr = new_order.getReq_msg();
						smsmsg_length=tmpstr.length();
						for( int i=0;i<smsmsg_count;i++)
						{
							if((i+1)*70 > smsmsg_length)
							{
								smsmsg[i]=tmpstr.substring(i*70);
							}
							else
							{
								smsmsg[i]=tmpstr.substring(i*70,(i+1)*70);
							}
						}
						
						for( int i=0;i<smsmsg_count;i++)
						{
							//System.out.println(number);
							new_order.setReq_msg(smsmsg[i]);
							spent_point_default = new_order.getSpent_point();							
							System.out.println("Point:"+ point + " spent_point_default:" +spent_point_default);
						    if( new_order.getReq_msg().length() % 70 >0 )
						    {
						    	spent_point_real = new_order.getReq_msg().length() / 70 + 1;
						    }
						    else
						    {
						    	spent_point_real = new_order.getReq_msg().length() / 70 ;
						    }
							
							if(spent_point_real > point){
								out.println("<script languate='javascript'>alert('點數不足!');location.href='order.jsp';</script>");
								return;
							}
					
							if( number.charAt(0)=='0' && number.charAt(1)=='9' || number.charAt(0)=='8' && number.charAt(1)=='8' && number.charAt(2)=='6' && number.charAt(3)=='9')
							{
								spent_point_real = 1 * spent_point_real;
							}
							else
							{
								spent_point_real = 3 * spent_point_real;
							}
							new_order.setSpent_point(spent_point_real);
							//System.out.println("該筆點數："+spent_point_real);							
							index = dao.create(new_order);
			
							if (index != 0 ){
								Order o = dao.getOrder(index);
								o.setSmppuser(new_order.getSmppuser());
							
								Document doc = DocumentHelper.createDocument();
							
								Element root = doc.addElement("SMSREQUEST"); // 建立 <自訂XML>
				
								Element root_username = root.addElement("USERNAME");
								root_username.addText("85266404045");
				
								Element root_password = root.addElement("PASSWORD");
								root_password.addText("85266404045");
				
								Element root_orgcode = root.addElement("ORGCODE");
								root_orgcode.addText(o.getSmppuser().getOrganization().getCode());				
				
				
								Element root_data = root.addElement("DATA");
				

								Element data_item = root_data.addElement("ITEM");
								Element item_schedule = data_item.addElement("SCHEDULE");
								item_schedule.addText(new_order.getReq_schedule());
								Element item_multiple = data_item.addElement("MULTIPLE");
								item_multiple.addText(new_order.getReq_multiple());
								Element item_MSG = data_item.addElement("MSG");
								item_MSG.addText(new_order.getReq_msg());

								Element item_number = data_item.addElement("PHONE");
								if(number.charAt(0)=='0'){
									number=number.replaceFirst("0", "886");
									item_number.addText(number);
								}else{
									item_number.addText(number);
								}	
				
								Element root_remark = root.addElement("REMARK");
								if(new_order.getReq_remark().length()==0){
									//System.out.println("Step a");
									root_remark.addText(" ");
								}else{
									//System.out.println("Step b");
									root_remark.addText(new_order.getReq_remark());	
								}
				
								//Element item_PHONE = root.addElement("PHONE");
								//item_multiple.addText(o.getReq_msg());
				
								System.out.println(doc.asXML());
				
								String responseXML = this.smsService.send(doc.asXML());
								String sql= null;
								System.out.println(responseXML);
								try{
									this.resItem  = this.decodeResponse(responseXML);
				
									responseXML = this.encodeResponse(this.resItem);
				
									System.out.println("Response Code='"+resItem.getUid() + "'");
									String rescode = resItem.getUid();
									rescode = rescode.replace("85=", "");
									sql = "update scsbsmpp.order set uid ='" + rescode + "' where order_id ='" + o.getId() + "'";				
									con=DatabaseDAO.getConnection();
									pt=con.prepareStatement(sql);
									pt.executeUpdate();
									pt.close();
									con.close();
								} catch (SQLException e) {
									System.out.println("SQL wrong!!\n\rError MSG:" + e.getLocalizedMessage() );
									System.out.println("SQL:" + sql + "\n\rResponse:" + resItem.getUid() + "\n\r");
								} catch(Exception e){
									System.out.println("Something wrong!!\n\rError MSG:" + e.getLocalizedMessage() );
									System.out.println("SQL:" + sql + "\n\rResponse:" + resItem.getUid() + "\n\r");
								}
								FileWriter fw = new FileWriter("G://OrderMessage//order-"+o.getId() + "-" + 
		        										System.currentTimeMillis()+".xml"); // 可自訂
								// 下面這行：預設自動換行、Tab 為 2 個空白
								// OutputFormat of = OutputFormat.createPrettyPrint(); // 格式化XML
								OutputFormat of = new OutputFormat(); // 格式化XML
								of.setIndentSize(4); // 設定 Tab 為 4 個空白
								of.setNewlines(true);// 設定 自動換行
								XMLWriter xw = new XMLWriter(fw, of);
								xw.write(doc);
								xw.close();
								fw.close();
								SmppuserDAO sudao = new SmppuserDAO();
								Smppuser smppuser2 = sudao.getSmppuser(smppuser.getId());
								smppuser2.setOrganization(smppuser.getOrganization());
								session.setAttribute("smppuser", smppuser2);
								out.println("<script languate='javascript'>alert('訊息資料已傳出處理中!');location.href='order.jsp';</script>");
							}else{
								out.println("<script languate='javascript'>alert('發送失敗!');location.href='order.jsp';</script>");
							}
						}
					}
				}
				else
				{
					for(String number : order.getReq_send_number())
					{
						smppuser = (Smppuser)session.getAttribute("smppuser");
						point =  Integer.parseInt(smppuser.getPoint());
						new_order = order;
						new_order.setReq_send_number2(number);
						//System.out.println(number);
						spent_point_default = new_order.getSpent_point();
						System.out.println("Point:"+ point + " spent_point_default:" +spent_point_default);
					    if( req.getParameter("sms_mode").equals("2") == true)
					    {
					    	if( new_order.getReq_msg().length() % 67 >0 )
					    	{
					    		spent_point_real = new_order.getReq_msg().length() / 67 + 1;
					    	}
					    	else
					    	{
					    		spent_point_real = new_order.getReq_msg().length() / 67 ;
					    	}
					    }
					    else
					    {
					    	if( new_order.getReq_msg().length() % 70 >0 )
					    	{
					    		spent_point_real = new_order.getReq_msg().length() / 70 + 1;
					    	}
					    	else
					    	{
					    		spent_point_real = new_order.getReq_msg().length() / 70 ;
					    	}
					    }
						
						if(spent_point_real > point){
							out.println("<script languate='javascript'>alert('點數不足!');location.href='order.jsp';</script>");
							return;
						}
					
						if( number.charAt(0)=='0' && number.charAt(1)=='9' || number.charAt(0)=='8' && number.charAt(1)=='8' && number.charAt(2)=='6' && number.charAt(3)=='9')
						{
							spent_point_real = 1 * spent_point_real;
						}
						else
						{
							spent_point_real = 3 * spent_point_real;
						}
						new_order.setSpent_point(spent_point_real);
						//System.out.println("該筆點數："+spent_point_real);
						index = dao.create(new_order);
			
						if (index != 0 ){
							Order o = dao.getOrder(index);
							o.setSmppuser(new_order.getSmppuser());
							
							Document doc = DocumentHelper.createDocument();
				
							Element root = doc.addElement("SMSREQUEST"); // 建立 <自訂XML>
				
							Element root_username = root.addElement("USERNAME");
							root_username.addText("85266404045");
				
							Element root_password = root.addElement("PASSWORD");
							root_password.addText("85266404045");
				
							Element root_orgcode = root.addElement("ORGCODE");
							root_orgcode.addText(o.getSmppuser().getOrganization().getCode());				
				
				
							Element root_data = root.addElement("DATA");
				

							Element data_item = root_data.addElement("ITEM");
							Element item_schedule = data_item.addElement("SCHEDULE");
							item_schedule.addText(new_order.getReq_schedule());
							Element item_multiple = data_item.addElement("MULTIPLE");
							item_multiple.addText(new_order.getReq_multiple());
							Element item_MSG = data_item.addElement("MSG");
							item_MSG.addText(new_order.getReq_msg());

							Element item_number = data_item.addElement("PHONE");
							if(number.charAt(0)=='0'){
							number=number.replaceFirst("0", "886");
							item_number.addText(number);
							}else{
							item_number.addText(number);
							}	
				
							Element root_remark = root.addElement("REMARK");
							if(new_order.getReq_remark().length()==0){
								//System.out.println("Step a");
								root_remark.addText(" ");
							}else{
								//System.out.println("Step b");
								root_remark.addText(new_order.getReq_remark());	
							}
				
							//				Element item_PHONE = root.addElement("PHONE");
							//				item_multiple.addText(o.getReq_msg());
				
							System.out.println(doc.asXML());
				
							String responseXML = this.smsService.send(doc.asXML());
							String sql= null;
							System.out.println(responseXML);
							try{
								this.resItem  = this.decodeResponse(responseXML);
				
								responseXML = this.encodeResponse(this.resItem);
				
								System.out.println("Response Code='" + resItem.getUid() + "'");
								String rescode = resItem.getUid();
								rescode = rescode.replace("85=", "");
								sql = "update scsbsmpp.order set uid ='" + rescode + "' where order_id ='" + o.getId() + "'";				
								con=DatabaseDAO.getConnection();
								pt=con.prepareStatement(sql);
								pt.executeUpdate();
								pt.close();
								con.close();
							} catch (SQLException e) {
								System.out.println("SQL wrong!!\n\rError MSG:" + e.getLocalizedMessage() );
								System.out.println("SQL:" + sql + "\n\rResponse:" + resItem.getUid() + "\n\r");
							} catch(Exception e){
								System.out.println("Something wrong!!\n\rError MSG:" + e.getLocalizedMessage() );
								System.out.println("SQL:" + sql + "\n\rResponse:" + resItem.getUid() + "\n\r");
							}
							FileWriter fw = new FileWriter("G://OrderMessage//order-"+o.getId() + "-" + 
		        										System.currentTimeMillis()+".xml"); // 可自訂
							// 下面這行：預設自動換行、Tab 為 2 個空白
							// OutputFormat of = OutputFormat.createPrettyPrint(); // 格式化XML
							OutputFormat of = new OutputFormat(); // 格式化XML
							of.setIndentSize(4); // 設定 Tab 為 4 個空白
							of.setNewlines(true);// 設定 自動換行
							XMLWriter xw = new XMLWriter(fw, of);
							xw.write(doc);
							xw.close();
							fw.close();
							SmppuserDAO sudao = new SmppuserDAO();
							Smppuser smppuser2 = sudao.getSmppuser(smppuser.getId());
							smppuser2.setOrganization(smppuser.getOrganization());
							session.setAttribute("smppuser", smppuser2);
							out.println("<script languate='javascript'>alert('訊息資料已傳出處理中!');location.href='order.jsp';</script>");
						}else{
							out.println("<script languate='javascript'>alert('發送失敗!');location.href='order.jsp';</script>");
						}
					}
				}
			//}
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

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}
}
