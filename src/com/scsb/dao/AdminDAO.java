package com.scsb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.scsb.bean.Admin;

public class AdminDAO {
	private Connection con;
	private PreparedStatement pt;
	private ResultSet rs;
	
	public boolean findAdmin(String account){
		boolean flag=false;
		con=DatabaseDAO.getConnection();
		try{
			pt=con.prepareStatement("select * from admin where account =?");
			pt.setString(1, account);
			rs=pt.executeQuery();
			if(rs.next()){
				flag=true;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DatabaseDAO.closeCon(con);
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeRs(rs);
		}
		return flag;
	}
	
	public int save(Admin admin){
		int i=0;
		con=DatabaseDAO.getConnection();
		try{
			
			pt = con.prepareStatement("insert into admin(account,password,name)"+
			" value(?,?,?)");
			
			pt.setString(1, admin.getAccount());
			pt.setString(2, admin.getPassword());
			pt.setString(3, admin.getName());
			
			//System.out.println(pt.toString());
			i=pt.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DatabaseDAO.closeCon(con);
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeRs(rs);
		}
		return i;
	}
	

	public int edit(Admin admin){
		
		int i=0;
		con=DatabaseDAO.getConnection();
		try{
			
			pt=con.prepareStatement("update admin set password = ?, name =? where id =?");	
			pt.setString(1, admin.getPassword());
			pt.setString(2, admin.getName());
			pt.setInt(3, Integer.parseInt(admin.getId()));

			//System.out.println(pt.toString());
			i=pt.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DatabaseDAO.closeCon(con);
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeRs(rs);
		}
		return i;		
	}	
	
	public Admin login(String account, String password, String ip, String time){
		Admin admin = null;
		con=DatabaseDAO.getConnection();
		try{
			pt=con.prepareStatement("select * from admin where account=? and password=?");
			pt.setString(1, account);
			pt.setString(2, password);
			rs=pt.executeQuery();
			
			if(rs.next()){
				admin = new Admin();
				admin.setId(rs.getString("id"));
				admin.setAccount(rs.getString("account"));
				admin.setPassword(rs.getString("password"));
				admin.setName(rs.getString("name"));
			
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DatabaseDAO.closeCon(con);
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeRs(rs);
		}
		return admin;
	}
	

}
