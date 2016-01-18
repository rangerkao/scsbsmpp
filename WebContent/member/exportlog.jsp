<%@page import="com.scsb.dao.OrderDAO"%>
<%@page import="com.scsb.bean.Order"%>
<%@page import="com.scsb.bean.Smppuser"%>
<%@page import="java.util.*"%>
<%@page import="java.util.Date"%>
<%@page import="java.io.*"%>
<%@page import="java.text.*"%>
<%@page import="jxl.*"%>
<%@page import="jxl.write.*"%>

<%@ page language="java" contentType="application/unknown; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Smppuser smppuser2 = (Smppuser)session.getAttribute("smppuser"); 
	OrderDAO oDAO = new OrderDAO();
	ArrayList<Order> list = oDAO.getOrder(smppuser2);
	//目前時間
	Date date = new Date();
	//設定日期格式
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
	//進行轉換
	String dateString = sdf.format(date);
	String strFileName = dateString + ".xls";
	response.reset();
	response.setHeader("Content-disposition","attachment; filename=" + strFileName);
	OutputStream os = response.getOutputStream();
	jxl.write.WritableWorkbook wwb = jxl.Workbook.createWorkbook(os);
	jxl.write.WritableSheet ws = wwb.createSheet("log",0);
	jxl.write.Number label_num;
	jxl.write.Label label_str;
	jxl.write.WritableCellFormat floatFormat;
	
	int numrow=0;
	label_str = new jxl.write.Label(0,numrow,"訂單編號");
	ws.addCell(label_str);
	label_str = new jxl.write.Label(1,numrow,"花費點數");
	ws.addCell(label_str);
	label_str = new jxl.write.Label(2,numrow,"發送時間");
	ws.addCell(label_str);
	label_str = new jxl.write.Label(3,numrow,"訊息內容");
	ws.addCell(label_str);
	label_str = new jxl.write.Label(4,numrow,"備註");
	ws.addCell(label_str);
	numrow++;
	for(Order o : list){
		label_str = new jxl.write.Label(0,numrow,String.valueOf(o.getId()));
		ws.addCell(label_str);
		label_str = new jxl.write.Label(1,numrow,String.valueOf(o.getSpent_point()));
		ws.addCell(label_str);
		label_str = new jxl.write.Label(2,numrow,o.getCreateTime2());
		ws.addCell(label_str);
		label_str = new jxl.write.Label(3,numrow,o.getReq_msg());
		ws.addCell(label_str);
		label_str = new jxl.write.Label(4,numrow,o.getReq_remark());
		ws.addCell(label_str);
		numrow++;	
		}
	wwb.write();
	wwb.close();
	os.flush();
	os.close();
%>