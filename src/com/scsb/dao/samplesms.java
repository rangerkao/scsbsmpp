package com.scsb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class samplesms {
	private Connection con;
	private PreparedStatement pt;
	private PreparedStatement pt2;
	private Statement st;
	private ResultSet rs;
	private ResultSet rs2;
	private ResultSet rs3;
	public ArrayList<String[]> getsamples(String order_id)
	{
		con=DatabaseDAO.getConnection();
		ArrayList<String[]> list = new ArrayList<String[]>();
		try{
			pt=con.prepareStatement("select a.ID,a.Content from samplesms as a, user_sample as b where b.uid='" + order_id + "' and a.id=b.sid");
			rs=pt.executeQuery();
			
			while(rs.next()){
				String[] samples = new String[2];
				samples[0] = rs.getString("ID");
				samples[1] = rs.getString("Content");
				list.add(samples);			
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DatabaseDAO.closeRs(rs);			
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeCon(con);
		}
		return list;
	}
	
	public void setsample(String sid,String cont)
	{
		con=DatabaseDAO.getConnection();
		int rtn=0;
		try
		{
			pt=con.prepareStatement("update samplesms set Content = '" + cont + "' where ID='" + sid + "'");
			rtn = pt.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			//DatabaseDAO.closeRs(rs);			
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeCon(con);
		}
		
	}
	
	public void addsample(String uid,String cont )
	{
		con=DatabaseDAO.getConnection();
		String sql = null;
		int numero = 0;
		String lid = null;
		try{
			st = con.createStatement();
			numero = st.executeUpdate("insert into samplesms(Content) values ('" + cont + "');");	    
		    rs = st.executeQuery("SELECT LAST_INSERT_ID() as LID;");
		    if( rs.next() ) 
		    {
		    	numero=st.executeUpdate("insert into user_sample(uid,sid) values ('" + uid + "','" + rs.getString("LID") + "');");
		    	if( numero == 0 )
				{
					throw new SQLException("Creating user sample failed, no rows affected.");
				}
	        } else {
	        	throw new SQLException("Creating user sample failed, no generated key obtained.");
	        }
		    st.close();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DatabaseDAO.closeRs(rs);
			DatabaseDAO.closeRs(rs2);
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closePt(pt2);
			DatabaseDAO.closeCon(con);
		}
	}
	public void deletesample(String uid,String sid)
	{
		con = DatabaseDAO.getConnection();
		String sql = null;
		try{
			sql = "delete from samplesms where ID='" + sid +"'";
			System.out.println(sql);
			pt=con.prepareStatement(sql);
			int rtn=pt.executeUpdate();
			if( rtn == 0)
			{
				throw new SQLException("delete samplesms failed, no rows affected.");
			}
			sql = "delete from user_sample where SID = '" + sid + "' and UID = '" + uid + "'";
			System.out.println(sql);
			pt=con.prepareStatement(sql);
			rtn=pt.executeUpdate();
			if( rtn == 0)
			{
				throw new SQLException("delete user_sample failed, no rows affected.");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			//DatabaseDAO.closeRs(rs);			
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeCon(con);
		}
		
	}
}

