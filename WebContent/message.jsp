<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>簡訊發送系統</title>
</head>
<script language="Javascript">
//<!-- Begin 
function textCounter(field, countfield, maxlimit) { 
if (field.value.length > maxlimit) 
field.value = field.value.substring(0,maxlimit); 
else 
countfield.value = maxlimit - field.value.length; 
} 
// End --> 

</script>
<body>
<%@include file="head.jsp" %>
<div id="site_content">
      <%@include file="news.jsp" %>
      <form action="createMessage.do" method="post">
      <div id="content">
        <table>
        <tr>
        <th>發送號碼：</th>
        <th><input type="text" id="phoneno" name="pnoneno"></th>
        </tr>
        <tr>
        <td>簡訊內容：<br>(每則簡訊限制字數為333個字)</td>
        <td>
        <textarea cols="45" id="msgbody" name="msgbody" rows="8" wrap="on" onKeyDown="textCounter(this.form.msgbody,this.form.remLen,333);" onKeyUp="textCounter(this.form.msgbody,this.form.remLen,333);"></textarea>
        </td>
        <tr>
        <td>剩餘字數：</td>
        <td>共可輸入333字，還剩<input readonly type=text name=remLen size=3 maxlength=333 value="333">字。</td>
        </tr>
        </table>
      </div>
      <input type=submit value=" 發送簡訊 " name="submit"> <input type=reset value=" 重填 " name="reset"> 
      </form>
    </div>
    
    
    
<br><br> 

    
    
<%@include file="foot.jsp" %>
</body>
</html>