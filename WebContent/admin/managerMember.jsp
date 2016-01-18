<%@page import="com.scsb.dao.SmppuserDAO"%>
<%@page import="com.scsb.bean.Admin"%>
<%@page import="com.scsb.bean.Smppuser"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.scsb.function.GetValue"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
		Admin admin2 = (Admin)session.getAttribute("admin");
    	SmppuserDAO smppuserDAO = new SmppuserDAO();
    	ArrayList<Smppuser> list = smppuserDAO.getSmppusers();
    	
    	String action = request.getParameter("action");
    	String str = new String();

    	if( action != null && action.length() > 0 )
    	{
    		if( action.equals("search") )
    		{
    			
    			if((request.getParameter("stype") != null ))
    			{
    				
    				int type = Integer.valueOf(request.getParameter("stype"));
    				switch(type)
    				{
    					case 1:
    						str = request.getParameter("uid");
    						break;
    					case 2:
    						str = request.getParameter("com");
    						break;
    				}
    				
    				out.println("stype:" + request.getParameter("stype") + " str:" + str);
    				list = smppuserDAO.getSmppusers(request.getParameter("stype"),str);
    			}
    		}
    	}
    	else
    	{
    		list = smppuserDAO.getSmppusers();
    	}
    %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>會員管理</title>
</head>
<script type="text/javascript" src="../js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="../js/jquery-migrate-1.0.0.min.js"></script>
<script type="text/javascript" src="../js/jquery-twzipcode-1.4.1.js"></script>
<script type="text/javascript" src="../js/jquery-validate.js"></script>
<script type="text/javascript" src="../js/jquery-validate-additional-methods.js"></script>
<script type="text/javascript" src="../js/jquery-maskedinput.min.js"></script>
<link rel="stylesheet" type="text/css" href="/scsbsmpp/css/reset.css" />
<link rel="stylesheet" type="text/css" href="/scsbsmpp/css/head_admin.css" />
<link href="/scsbsmpp/images/favicon.ico" rel="SHORTCUT ICON">
<script type="text/javascript">
$(function(){
	   
	   $('.detail').click(function(){ 
			location.href = 'detailMember.jsp?id='+$(this).val();
	  });
	});
</script>
<script type="text/javascript">
$(function(){
	   
	   $('.edit').click(function(){ 
			location.href = 'editPassword.jsp?id='+$(this).val();
	  });
	});
</script>
<script language="Javascript">
function searchshow(type)
{
	switch(type)
	{
		case '1':
			document.getElementById('uid_search').style.display="";
			document.getElementById('com_search').style.display="none";
			break;
		case '2':
			document.getElementById('uid_search').style.display="none";
			document.getElementById('com_search').style.display="";
			break;
	}
}
</script>

<style type="text/css">

#frame{
	border:0px groove #B4C3F0; 
	margin:-400px 0 0 220px;
 }
#bg{
   /*background-color:rgba(25,7,45,0.5);*/
 }

</style>
<body>
<div id="wrap">
	<div style="float:right;"><%@include file="../login_admin.jsp" %></div>
	<div id="content">
  	<%@include file="../head_admin-left.jsp" %>
  	<div class="right folat-r">
      <div id="right-content">
        <div id="top">
        </div>
        
        <div class="section">
          <h2></h2>
          <div id="bg" style="overflow:auto; height:800px; width:950px; margin-left: -25%">
      <form id="search" action="managerMember.jsp" method="post">
      <input type="hidden" name="action" value="search">
      <input type="radio" onclick="searchshow(this.value);" name="stype" value="1" <% if ( request.getParameter("stype") != null && request.getParameter("stype").equals("1") ) out.println("checked"); %>>依會員編號<br/>
      <input type="radio" onclick="searchshow(this.value);" name="stype" value="2" <% if ( request.getParameter("stype") != null && request.getParameter("stype").equals("1") ) out.println("checked"); %>>依公司名稱<br/>
      <div id="uid_search" style="display: none;"><input name="uid" type="text" value="0" checked></div>
      <div id="com_search" style="display: none;"><input name="com" type="text" value="0" checked></div>
      <input type="submit" value="搜尋"><br/>
      </form>
      <%
	out.println("<a>"+"stype:" + request.getParameter("stype") + " str:" + str+"</a>");
	%>
	<table border="5" cellpadding="8" frame="void" rules="none" style="width:100%;">
		<tr>
			<th>編號</th>
			<th>使用者名稱</th>
			<th>公司名稱</th>
			<th>姓名</th>
			<th>性別</th>
			<th>電話</th>
			<th></th>
		</tr>
		

		<%
		for(Smppuser s : list){
		%>
			<tr>
				<td><%=s.getId() %></td>
				<td><%=s.getUsername() %></td>
				<td><%=s.getCompany() %></td>
				<td><%=s.getName()%></td>
				<td><%
							int g = Integer.parseInt(s.getGender());
							if(g==0){
								out.println("男性");
							}else if(g==1){
								out.println("女性");
							}%>
				</td>
				<td><%=s.getPhone() %></td>
				<td><button class="detail" value="<%=s.getId()%>">詳細</button></td>
				<td><button class="edit" value="<%=s.getId()%>">修改密碼</button></td>
			</tr>
			
		<%
		}
		%>
	</table>
</div>
        </div>
      </div>
    </div>
  	</div>
  </div>

</body>
</html>