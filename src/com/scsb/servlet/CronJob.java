package com.scsb.servlet;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.lang.time.DateUtils;

import com.scsb.dao.DatabaseDAO;
/**
* Cron Job.
*
* @author Daynight
* @version 1.0 02/22/2011
*/
public class CronJob{
	private final Timer timer = new Timer();
	private int minutes = 15;
	private Connection connection = null;
	private PreparedStatement pt = null;
	private ResultSet rs = null;
	private Connection connection2 = null;
	private PreparedStatement pt2 = null;
	private ResultSet rs2 = null;
	private PreparedStatement pt3 = null;
	private int rs3 = 0;

	public CronJob()
	{
		connection = DatabaseDAO.getConnection();
		try
		{
			Class.forName("org.postgresql.Driver").newInstance();
			connection2 = DriverManager.getConnection("jdbc:postgresql://192.168.10.199:5432/smppdb","smpper", "Smlpp3r");
		}
		catch(InstantiationException e)
		{
			System.out.println(e.getLocalizedMessage());
		} catch (IllegalAccessException e) {
			System.out.println(e.getLocalizedMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getLocalizedMessage());
		}catch(SQLException e)
		{
			System.out.println("Can't connect to postgresql!!\n\rError Code:" + e.getErrorCode() + "\n\rError Msg:" + e.getLocalizedMessage() );
		}
	}
	protected void finalize () 
	{
		try {
			connection.close();
			connection2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	/**
	 * Web Call method.
	 *
	 */
	public void getJobInfo() {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			String ctxminutes = (String) envCtx.lookup("Minutes");
			this.minutes = Integer.parseInt(ctxminutes);
			startScheduler();
		}
		catch (Exception e) {
		}
	}

	/**
	 * start schedular.
	 *
	 */
	private void startScheduler() {
		try {
			timer.schedule(new TimerTask() {
				public void run() {
					scheduleParse();
					//timer.cancel();
				}
				private void scheduleParse() {
					//write your business logic here
					int error_count=0;
					String sql = "";
					String rescode = "";
					Date dtNow = new Date();
					SimpleDateFormat fot = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date increment = DateUtils.addDays(dtNow, -1);
					try
					{
						sql = "select * from scsbsmpp.order where status<>2 and restored = 0 and uid is not null and create_time < '" + fot.format(increment) + "'";
						pt=connection.prepareStatement(sql);
						rs=pt.executeQuery();
						while(rs.next())
						{
							int status=0;
							int points=0;
							points=rs.getInt("spent_point");
							sql = "update smppuser set point= point + " + points + " where id='"+ rs.getString("smppuser_id") +"'";
							//connection3 = DatabaseDAO.getConnection();
							pt3=connection.prepareStatement(sql);
							rs3=pt3.executeUpdate();
							if( rs3 > 0)
							{
								FileWriter fw;
								try {
									fw = new FileWriter("G://OrderMessage//restore-" + System.currentTimeMillis()+".txt");
			
									fw.write("Restore Point to user:" + rs.getString("smppuser_id") + " with Points:" + points + "\n Phone_No:" + rs.getString("number") + " RESCODE:" + rs.getString("uid"));
									fw.flush();
									fw.close();
									System.out.println("Restore Point to user:" + rs.getString("smppuser_id") + " with Points:" + points + "\n Phone_No:" + rs.getString("number") + " RESCODE:" + rs.getString("uid"));
								} catch (IOException e) {
									e.printStackTrace();
								} // 可自訂
							}
							sql = "update scsbsmpp.order set restored='1' where uid='" + rs.getString("uid") + "'";
							try
							{
								//connection3 = DatabaseDAO.getConnection();
								pt3=connection.prepareStatement(sql);
								rs3=pt3.executeUpdate();
								if( rs3 > 0)
								{
									System.out.println("Updated!! Status=" + status + "\n Phone_No:" + rs.getString("number") + " RESCODE:" + rs.getString("uid") );
								}
							}catch(Exception e)
							{
								System.out.println("Can't not create connection3, pt3!!");
							}
							finally
							{
								pt3.close();
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
						}
						catch(Exception e)
						{
							System.out.println("Can't close connection, pt and rs!!");
						}
					}
					try
					{
						
						sql = "select * from scsbsmpp.order where status<>2 and restored = 0 and uid is not null";
						//connection = DatabaseDAO.getConnection();
						pt=connection.prepareStatement(sql);
						rs=pt.executeQuery();
						while(rs.next())
						{
							int status=0;
							int points=0;
							rescode ="85=" + rs.getString("uid");
							sql ="select s.userid,i.msgid,i.phoneno,i.tries,i.status as status,i.donetime,m.createtime from smppuser s, messages m, msgitem i where s.userid=m.userid and i.msgid=m.msgid and s.userid='85266404045' and m.msgid='" + rescode +"'";
							try
							{
								//Class.forName("org.postgresql.Driver");
								//connection2 = DriverManager.getConnection("jdbc:postgresql://192.168.10.199:5432/smppdb","smpper", "Smlpp3r");
								pt2=connection2.prepareStatement(sql);
								rs2=pt2.executeQuery();
							
								while(rs2.next())
								{
									status = rs2.getInt("status");
									System.out.println("Status=" + status);
									switch(status)
									{
										case 3:
										case 4:
										case 5:
										case 7:
										case 8:
										case 96:
											points=rs.getInt("spent_point");
											sql = "update smppuser set point= point + " + points + " where id='"+ rs.getString("smppuser_id") +"'";
											//connection3 = DatabaseDAO.getConnection();
											pt3=connection.prepareStatement(sql);
											rs3=pt3.executeUpdate();
											if( rs3 > 0)
											{
												FileWriter fw;
												try {
													fw = new FileWriter("G://OrderMessage//restore-" + System.currentTimeMillis()+".txt");
							
													fw.write("Restore Point to user:" + rs.getString("smppuser_id") + " with Points:" + points + "\n Phone_No:" + rs2.getString("phoneno") + " RESCODE:" + rescode);
													fw.flush();
													fw.close();
													System.out.println("Restore Point to user:" + rs.getString("smppuser_id") + " with Points:" + points + "\n Phone_No:" + rs2.getString("phoneno") + " RESCODE:" + rescode);
												} catch (IOException e) {
													e.printStackTrace();
												} // 可自訂
											}
											rescode = rescode.replace("85=", "");
											sql = "update scsbsmpp.order set status='" + rs2.getInt("status")+"',restored='1' where uid='" + rescode + "'";
											try
											{
												//connection3 = DatabaseDAO.getConnection();
												pt3=connection.prepareStatement(sql);
												rs3=pt3.executeUpdate();
												if( rs3 > 0)
												{
													System.out.println("Updated!! Status=" + status + "\n Phone_No:" + rs2.getString("phoneno") + " RESCODE:" + rescode );
												}
											}catch(Exception e)
											{
												System.out.println("Can't not create connection3, pt3!!");
											}
											finally
											{
												pt3.close();
											}
											break;
										default:
											rescode = rescode.replace("85=", "");
											sql = "update scsbsmpp.order set status='" + rs2.getInt("status")+"' where uid='" + rescode + "'";
											try
											{
												//connection3 = DatabaseDAO.getConnection();
												pt3=connection.prepareStatement(sql);
												rs3=pt3.executeUpdate();
												if( rs3 > 0)
												{
													System.out.println("Default Updated!! Status=" + status + "\n Phone_No:" + rs2.getString("phoneno") + " RESCODE:" + rescode );
												}
											}catch(Exception e)
											{
												System.out.println("Can't not create connection3, pt3!!");
											}
											finally
											{
												pt3.close();
											}
											break;
									}
								}
							}catch(Exception e)
							{
								System.out.println("Can't not Check Status!!(" +e.getLocalizedMessage()+")");
								error_count++;
								if(error_count > 10 ) return;
							}
							finally
							{
								rs2.close();
								pt2.close();
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
						}
						catch(Exception e)
						{
							System.out.println("Can't close connection, pt and rs!!");
						}
					}

				}
			}, 0, this.minutes * 60 * 1000);
		}
		catch (Exception e) {
			System.out.println("Can't start Cron Job!!");
		}
	}
}
