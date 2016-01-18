<%@page import="java.io.*" %>
<%@page import="java.util.*" %>
<%@page import="javax.naming.*" %>
<%@page import="java.util.Timer" %>
<%@page import="java.util.TimerTask" %>
<%@page import="java.sql.*" %>
<%@page import="java.text.*" %>
<%@page import="org.postgresql.*" %>
<%
Class.forName("org.postgresql.Driver");

Connection connection = null;
PreparedStatement pt = null;
ResultSet rs = null;
String sql = null;
int columnsNumber = 0;
sql ="select s.userid,i.msgid,seq,schedule,phoneno,msgbody,tries,status,donetime,m.createtime,orgcode from smppuser s, messages m, msgitem i where s.userid=m.userid and i.msgid=m.msgid and s.userid='85266404045'";
try
{
connection = DriverManager.getConnection("jdbc:postgresql://192.168.10.199:5432/smppdb","smpper", "Smlpp3r");
//connection = source.getConnection();
pt=connection.prepareStatement(sql);
rs=pt.executeQuery();
ResultSetMetaData rsmd = rs.getMetaData();

columnsNumber = rsmd.getColumnCount();
out.println("<table>");
out.println("<tr>");
for( int i=1;i<=columnsNumber;i++)
{		
	out.println("<td>" + rsmd.getColumnName(i) + "</td>");
}
out.println("</tr>");
while(rs.next())
{
	out.println("<tr>");
	for( int i=1;i<=columnsNumber;i++)
	{		
		out.println("<td>" + rs.getString(i) + "</td>");
	}
	out.println("</tr>");
}
out.println("</table>");
connection.close();
} catch(SQLException e)
{
	out.println("connection to DB error!\n\rError Code:" + e.getErrorCode() + "\n\rMessage:" + e.getLocalizedMessage());
	out.println("\n\r columnsNumber:" + columnsNumber + "\n\rSQL:" + sql);
}

%>