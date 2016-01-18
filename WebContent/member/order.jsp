<%@page import="com.scsb.bean.Smppuser" %> 
<%@page import="com.scsb.bean.PhoneBook"%>
<%@page import="com.scsb.bean.Phone"%>
<%@page import="com.scsb.dao.samplesms"%>
<%@page import="java.util.ArrayList"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
Smppuser smppuser2 = (Smppuser)session.getAttribute("smppuser"); 
PhoneBook pbook = new PhoneBook();
samplesms samples = new samplesms();

%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../css/head.css" />
<link href="../css/jquery-ui.css" rel="stylesheet">
  <script type="text/javascript" src="../js/jquery.min.js"></script>
  <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
  <link href="../css/jquery-ui-timepicker-addon.css" rel='stylesheet'>
  <script type="text/javascript" src="../js/jquery-ui-timepicker-addon.js"></script>
  <script type='text/javascript' src='../js/jquery-ui-sliderAccess.js'></script>
  <link href="/scsbsmpp/images/favicon.ico" rel="SHORTCUT ICON">
  
<title>簡訊發送系統</title>
</head>
<script language="Javascript">
//string.Blength() 傳回字串的byte長度 
String.prototype.Blength = function() { 
	var arr = this.match(/[^\x00-\xff]/ig); 
	return  arr == null ? this.length : this.length + arr.length; 
} 
//<!-- Begin 
function textCounter(field, countfield, maxlimit) { 
	if (field.value.length > maxlimit) {
	field.value = field.value.substring(0,maxlimit);
	}else {
	countfield.value = maxlimit - field.value.length;
	}
	};
	// End -->
//<!-- Begin 
function textCounter1(field, countfield, maxlimit) { 
	if (field.value.length > maxlimit) {
	field.value = field.value.substring(0,maxlimit);
	}else {
	countfield.value = maxlimit - field.value.length;
	}
	};
	// End -->
function CaculateNumbers()
{
	var numbers = document.getElementById("req_send_number").value;
	var number,inner=0,outter=0;
	if( numbers.length > 0 && numbers.indexOf("\n") > 0 )
	{
	    var numarray = new Array();
	    numarray = numbers.split("\n");
		while(number = numarray.pop())
		{
			if( number.charAt(0) == "0" && number.charAt(1) == "9" && number.length >= 10 || number.charAt(0) == "8" && number.charAt(1) == "8" && number.charAt(2) == "6" && number.charAt(3) == "9" && number.length >= 12 )
			{
				inner++;			
			}
			else if ( numbers.charAt(0) == '+' && numbers.length >= 10 )
			{
				outter++;
			}
			else if ( number.charAt(0) != "0" && number.charAt(1) != "9" && number.length >= 10 || number.charAt(0) != "8" && number.charAt(1) != "8" && number.charAt(2) != "6" && number.charAt(3) != "9" && number.length >= 12 )
			{
				outter++;			
			}
		}
	}
	else
	{
		if( numbers.charAt(0) == "0" && numbers.charAt(1) == "9" && numbers.length >= 10 || numbers.charAt(0) == '8' && numbers.charAt(1) == '8' && numbers.charAt(2) == '6' && numbers.charAt(3) == '9' && numbers.length >= 12 )
		{
			inner++;			
		}
		else if ( numbers.charAt(0) == '+' && numbers.length >= 10 )
		{
			outter++;
		}
		else if ( numbers.charAt(0) != "0" && numbers.charAt(1) != "9" && numbers.length >= 10 || numbers.charAt(0) != '8' && numbers.charAt(1) != '8' && numbers.charAt(2) != '6' && numbers.charAt(3) != '9' && numbers.length >= 12 )
		{
			outter++;			
		}
	}
	document.getElementById('sms_inner').value = inner;
	document.getElementById('sms_outter').value = outter;
	
}

function CaculatePoints()
{
	var sms_text;
	var sms_mode;
	for( i=0;i<document.getElementsByName('sms_mode').length;i++)
	{
		//alert("step a");
		if( document.getElementsByName('sms_mode')[i].checked)
		{
			sms_mode = document.getElementsByName('sms_mode')[i].value;
			break;
		}
	}
	var sms_inner = document.getElementById('sms_inner').value;
	var sms_outter = document.getElementById('sms_outter').value;
	var sms_numbers;
	
	switch(sms_mode)
	{
		case '1':
			sms_text = document.getElementById('req_msg').value.length;
			if( sms_text == 0 )
			{
				sms_numbers =0;
			}
			else if(sms_text % 70 > 0 )
			{
				sms_numbers = parseInt(sms_text / 70 + 1);	
				var inners = sms_numbers * (1 * sms_inner);
				var outters = sms_numbers * (1 * sms_outter);
				document.getElementById('sms_inner').value = inners;
				document.getElementById('sms_outter').value = outters;
			}
			else
			{
				sms_numbers = parseInt(sms_text / 70);
				var inners = sms_numbers * (1 * sms_inner);
				var outters = sms_numbers * (1 * sms_outter);
				document.getElementById('sms_inner').value = inners;
				document.getElementById('sms_outter').value = outters;
			}
			//alert("mode:" + sms_mode + " numbers:" + sms_numbers);
			break;
		case '2':
			sms_text = document.getElementById('req_msg1').value.length;
			if( sms_text == 0 )
			{
				sms_numbers =0;
			}
			else if( (sms_text % 67) > 0 )
			{
				sms_numbers = parseInt(sms_text / 67 + 1);	
			}
			else
			{
				sms_numbers = parseInt(sms_text / 67);
			}
			//alert("mode:" + sms_mode + " numbers:" + sms_numbers);
			break;
		
	}
	
	
	
	var points = sms_numbers * (1 * sms_inner + 3 * sms_outter);
	//alert(sms_mode + " " + sms_numbers + " " + sms_inner + " " + sms_outter + " " +points);
	
	document.getElementById('spent_points').value = parseInt(points);
	
}
function change_msg()
{
	document.getElementById('req_msg').value = document.getElementById('samples').options[document.getElementById('samples').selectedIndex].text;
	textCounter(document.getElementById('req_msg'),document.getElementById('req_form').remLen,300);
	CaculatePoints();
}
function change_msg1()
{
	document.getElementById('req_msg1').value = document.getElementById('samples1').options[document.getElementById('samples1').selectedIndex].text;
	textCounter1(document.getElementById('req_msg1'),document.getElementById('req_form').remLen1,300);
	CaculatePoints();
}
function add_phone()
{
	var mode;
	for( i=0;i<document.getElementsByName('mode').length;i++)
	{
		
		if( document.getElementsByName('mode')[i].checked)
		{
			mode = document.getElementsByName('mode')[i].value;
			break;
		}
	}

	if( mode == '2' )		
	{
		if( document.getElementById('req_send_number').value == "")
		{
			document.getElementById('req_send_number').value = document.getElementById('phonebook').value;
		}
		else
		{
			document.getElementById('req_send_number').value += "\n" + document.getElementById('phonebook').value;
		}
	}
	else
	{
		document.getElementById('req_send_number').value = document.getElementById('phonebook').value;
	}
	CaculateNumbers();
	CaculatePoints();
}
function add_group()
{
	var group = document.getElementById("groups").value;
	var mode;
	for( i=0;i<document.getElementsByName('mode').length;i++)
	{
		
		if( document.getElementsByName('mode')[i].checked)
		{
			mode = document.getElementsByName('mode')[i].value;
			break;
		}
	}
	
	for( i=0;i<phonebooks.length;i++)
	{
		if( group.match(phonebooks[i][1])) 
		{
			if( mode == '2' )		
			{
				if( document.getElementById('req_send_number').value == "")
				{
					document.getElementById('req_send_number').value = phonebooks[i][0];
				}
				else
				{
					document.getElementById('req_send_number').value += "\n" + phonebooks[i][0];
				}
			}
			else
			{
				alert("群組加入僅限多門號發送！！");
			}
		}
	}
	CaculateNumbers();
	CaculatePoints();
}
function chk()
{
	var now = new Date();
	var schdule_time = new Date(document.getElementById('req_schedule').value);
	if (document.getElementById('req_schedule').value == '' )
	{
	   alert('請輸入預約時間或輸入0表示不預約');
	   location.href='order.jsp';
	   return false;
	}
	else if(document.getElementById('req_schedule').value != '0' && schdule_time.getTime() < now.getTime())
	{
		alert('預約時間不可早於現在');
		location.href='order.jsp';
		return false;
	}
	else
	{
		return true;
	}
}
</script>

<script language="Javascript">
function searchshow(type)
{
	switch(type)
	{
		case '1':
			document.getElementById('message').style.display="";
			document.getElementById('long_message').style.display="none";			
			break;
		case '2':
			document.getElementById('message').style.display="none";
			document.getElementById('long_message').style.display="";			
			break;
	}
}
</script>

<script language="Javascript">
function modeshow(type)
{
	switch(type)
	{
		case '1':
			document.getElementById('mode_1').style.display="";
			document.getElementById('mode_2').style.display="none";
			break;
		case '2':
			document.getElementById('mode_1').style.display="none";
			document.getElementById('mode_2').style.display="";
			break;
	}
}
</script>

<style></style>

<body>
<div id="main">
<%@include file="../head.jsp" %>
<div id="site_content">
      <div id="content">
	<form id="req_form" action="createOrder.do" method="post" >
	<input type="hidden" id="smppuser_com" name="smppuser_com" value="<%=smppuser.getCompany() %>">
		<div id="content">
			<table style="margin-top: -10%;">
				<tr>
					<td width="100">模式：</td>
					<td>
						<input id="mode" type="radio" onclick="modeshow(this.value);" name="mode" value="1" <% if ( request.getParameter("mode") != null && request.getParameter("mode").equals("1") ) { out.println("checked"); } else { out.println("checked"); } %>>單筆門號<br/>
      					<input id="mode" type="radio" onclick="modeshow(this.value);" name="mode" value="2" <% if ( request.getParameter("mode") != null && request.getParameter("mode").equals("2") ) out.println("checked"); %>>多筆門號<br/>
						<!--<input type="radio" id='mode' name="mode" value="1"  checked="checked">單筆門號
						<input type="radio" id='mode' name="mode" value="2">多筆門號
						<input type="radio" name="mode" value="3">複合門號 -->
						</td>
				</tr>						
				<tr>
					<td>預約發送：</td>
					<td><input type="text" id="req_schedule" name="req_schedule" value="<% if( request.getParameter("req_schedule") != null ){ out.println(request.getParameter("req_schedule")); } else {out.println("0");} %>" >
						<script language="JavaScript">
    						$(document).ready(function(){ 
     						var opt={dateFormat: 'yy/mm/dd',
	       					showSecond: true,
               				timeFormat: 'HH:mm:ss'               				
              				};
      						$('#req_schedule').datetimepicker(opt);
      						$('#req_schedule').datetimepicker("0","setDate");
      						$('#req_schedule').datetimepicker().on("blur", function (e) {
      						    var curDate = $(this).val();
      						    try {
      						    	var r = $.datepicker.parseDateTime("yy/mm/dd","HH:mm:ss", curDate);
      						    } catch(e) {
      						        alert('Not VALID!');
      						        $(this).focus();
      						    }
      						});
      						});
  						</script>
						<br/><pre>請輸入簡訊發送時間(務必大於目前時間，範例：2013/12/31 01:00:00,0表示不預約)</pre></td>
				</tr>							
																		
				<tr>
					<td>發送號碼：</td>
					<td><textarea id="req_send_number" name="req_send_number" cols="60" rows="8" onchange="CaculateNumbers(); CaculatePoints();" value="<% if( request.getParameter("req_send_number") != null ){ out.println(request.getParameter("req_send_number")); } else {out.println("");}%>" style="resize : none;"></textarea>
					    <br/>
					    <div id="mode_1" style="display: none;">
					    <%
					    ArrayList<Phone> phones = pbook.getPhones(smppuser2);					    
					    
					    out.println("<select id='phonebook' name='phonebook'>");
					    out.println("<option value='0'>&nbsp;</option>");
					    for( Phone phone : phones)
					    {
					    	out.println("<option value='" + phone.getNumber() + "'>" + phone.getDescription() + "</option>");
					    }
					    out.println("</select>");
					    out.println("<script language=\"Javascript\">");
					    out.println("var phonebooks = [");
					    for( Phone phone2 : phones)
					    {
					    	out.println("['"  + phone2.getNumber() + "','" + phone2.getGroup() + "'],");
					    }
					    out.println("];");
					    out.println("</script>");
					    %>
					    <input type="button" id="phone_add" name="phone_add" value="加入" onClick="add_phone();">
					    </div>
					    
					    <div id="mode_2" style="display: none;"> 
					    <%
					    ArrayList<String> groups = pbook.getGroups(smppuser2);					    
					    
					    out.println("<select id='groups' name='groups'>");
					    out.println("<option value='0'>&nbsp;</option>");
					    for( String group : groups)
					    {
					    	out.println("<option value='" + group + "'>" + group + "</option>");
					    }
					    out.println("</select>");
					    %>
					    <input type="button" id="group_add" name="group_add" value="群組加入" onClick="add_group();">
						</div>
						<br/><pre>國內電話請用09開頭(1點/則)，國際電話請用國碼開頭(3點/則)</pre><pre>發送多筆時，每筆電話輸入後請換下一行</pre>
					</td>
				</tr>
				<tr>
					<td>簡訊內容：</td>
					<td>
						<input id="sms_mode" type="radio" onclick="searchshow(this.value);" name="sms_mode" value="1" <% if ( request.getParameter("sms_mode") != null && request.getParameter("sms_mode").equals("1") ) { out.println("checked"); } else { out.println("checked"); }%>>短簡訊<br/>
      					<input id="sms_mode" type="radio" onclick="searchshow(this.value);" name="sms_mode" value="2" <% if ( request.getParameter("sms_mode") != null && request.getParameter("sms_mode").equals("2") ) out.println("checked"); %>>長簡訊<br/>
						
						<div id="message" style="display: none;">
						<textarea cols="45" id="req_msg" name="req_msg" rows="8" wrap="on"  onKeyDown="textCounter(this.form.req_msg,this.form.remLen,300);" onKeyUp="textCounter(this.form.req_msg,this.form.remLen,300);" onchange="CaculateNumbers();CaculatePoints();" style="resize : none;" ><% if( request.getParameter("req_msg") != null ){ out.println(request.getParameter("req_msg")); } else {out.println("");} %></textarea>
						<br/><%
					 	ArrayList<String[]> list = samples.getsamples(smppuser2.getId());
					 	out.println("<select id='samples' name='samples' onchange='change_msg();'>");
					 	out.println("<option value='0'>&nbsp;</option>");
					 	for( String[] samplesms : list)
						{
							out.println("<option value='" + samplesms[0] + "'>" + samplesms[1] + "</option>");
						}
					 	out.println("</select>");
					 	%>
					 	<pre>(每則簡訊以70個字為單位計價)</pre>
					 	共可輸入300字，還剩<input readonly type=text id=remLen name=remLen size=3 maxlength=3 value="300">字。
					 	</div>
					 	
					 	<div id="long_message" style="display: none;">
						<textarea cols="45" id="req_msg1" name="req_msg1" rows="8" wrap="on"  onKeyDown="textCounter1(this.form.req_msg1,this.form.remLen1,300);" onKeyUp="textCounter1(this.form.req_msg1,this.form.remLen1,300);" onchange="CaculateNumbers();CaculatePoints();" style="resize : none;" ><% if( request.getParameter("req_msg1") != null ){ out.println(request.getParameter("req_msg1")); } else {out.println("");} %></textarea>
					 	<br/><%
					 	ArrayList<String[]> list1 = samples.getsamples(smppuser2.getId());
					 	out.println("<select id='samples1' name='samples1' onchange='change_msg1();'>");
					 	out.println("<option value='0'>&nbsp;</option>");
					 	for( String[] samplesms : list)
						{
							out.println("<option value='" + samplesms[0] + "'>" + samplesms[1] + "</option>");
						}
					 	out.println("</select>");
					 	%>
					 	<pre>(每則簡訊以67個字為單位計價)</pre>
					 	共可輸入300字，還剩<input readonly type=text id=remLen1 name=remLen1 size=3 maxlength=3 value="300">字。
					 	</div>
					 	
					</td>
				</tr>
				<tr>
					<td>消耗點數/簡訊數量：</td>
					<td>消耗<input readonly type=text id="spent_points" value="0">點<br/></b>
					國內簡訊共<input readonly type=text id="sms_inner" value="0">則<br/>
					國際簡訊共<input readonly type=text id="sms_outter" value="0">則</td>
				</tr>
				<tr>
					<td>備註：<br/></td>
					<td>
						<textarea cols="45" id="req_remark" name="req_remark" rows="8" style="resize : none;"></textarea>
					</td>	
				</tr>			
				
			</table>
		<input type=submit onclick="return chk();" value=" 發送簡訊 " name="submit"> 
		<input type=reset value=" 重填 " name="reset"> 
		</div>
		
	</form>
 </div>
    </div>
</div>
<%@include file="../foot.jsp" %>   
</body>
</html>