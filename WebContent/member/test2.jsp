<%@page import="com.scsb.dao.*" %>
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
Connection connection2 = null;
PreparedStatement pt2 = null;
ResultSet rs2 = null;
Connection connection3 = null;
PreparedStatement pt3 = null;
int rs3 = 0;
String sql = "";


try
{
	String rescode = "";
	sql = "select * from scsbsmpp.order where status=0";
	connection = DatabaseDAO.getConnection();
	pt=connection.prepareStatement(sql);
	rs=pt.executeQuery();
	while(rs.next())
	{
		rescode ="85=" + rs.getString("uid");
		sql ="select s.userid,i.msgid,phoneno,tries,status,donetime,m.createtime from smppuser s, messages m, msgitem i where s.userid=m.userid and i.msgid=m.msgid and s.userid='85266404045' and m.msgid='" + rescode +"'";
		System.out.println("Step A SQL:" + sql);
		try
		{
			connection2 = DriverManager.getConnection("jdbc:postgresql://192.168.10.199:5432/smppdb","smpper", "Smlpp3r");
		pt2=connection2.prepareStatement(sql);
		rs2=pt2.executeQuery();
		while(rs2.next())
		{
			int status = rs2.getInt("status");
			rescode = rescode.replace("85=", "");
			switch(status)
			{
				case '3':
				case '4':
				case '5':
				case '7':
				case '8':
					int points=rs.getInt("spent_point");
					sql = "update smppuser set point= point + " + points + " where id='"+ rs2.getString("smppuser_id") +"'";
					System.out.println("Step B SQL:" + sql);
					connection3 = DatabaseDAO.getConnection();
					pt3=connection3.prepareStatement(sql);
					rs3=pt3.executeUpdate();
					if( rs3 > 0)
					{
						FileWriter fw;
						try {
							fw = new FileWriter("D://OrderMessage//restore-" + System.currentTimeMillis()+".txt");
						
							fw.write("Restore Point to user:" + rs2.getString("smppuser_id") + " with Points:" + points);
							fw.flush();
							fw.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} // 可自訂
					}
					break;
				default:
					sql = "update scsbsmpp.order set status='" + rs2.getInt("status")+"' where uid='" + rescode + "'";
					System.out.println("Step C SQL:" + sql);
					try
					{
						connection3 = DatabaseDAO.getConnection();
						pt3=connection3.prepareStatement(sql);
						rs3=pt3.executeUpdate();
						
						if( rs3 > 0)
						{
							System.out.println("Updated!!");
						}
					}catch(Exception e)
					{
						System.out.println("Can't not create connection3, pt3!!");
					}
					finally
					{
						pt3.close();
						connection3.close();
					}
					break;
			}
		}
		}catch(Exception e)
		{
			System.out.println("Can't not create connection2, pt2!!");
		}
		finally
		{
			rs2.close();
			pt2.close();
			connection2.close();
		}
	}
}
catch(SQLException e)
{
	System.out.println("connection to DB error!\n\rError Code:" + e.getErrorCode() + "\n\rMessage:" + e.getLocalizedMessage());
}
finally
{
	try
	{
		rs.close();		
		pt.close();
		connection.close();
	}
	catch(Exception e)
	{
		System.out.println("Can't close connection, pt and rs!!");
	}
}

%>