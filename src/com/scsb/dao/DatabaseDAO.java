package com.scsb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseDAO {
	private static Connection con;
	private static final String DRIVER ="com.mysql.jdbc.Driver";
	private static final String URL ="jdbc:mysql://192.168.10.252:3306/scsbsmpp?characterEncoding=utf8";
	
	private static final String NAME="scsbsmppAdmin";
	private static final String PASSWORD="adm";
	
	static{
		try{
			Class.forName(DRIVER);
			
		}catch(ClassNotFoundException e){
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	public static Connection getConnection(){
		try{
			con = DriverManager.getConnection(URL,NAME,PASSWORD);
			//con = DriverManager.Connection(URL,NAME,PASSWORD);
		}catch(SQLException e){
			//e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
		}
		return con;
	}
	
	public static void closeCon(Connection con){
		try{
			if(con!=null)con.close();
		}catch(SQLException e){
			//e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	public static void closePt(PreparedStatement pt){
		try{
			if(pt!=null)pt.close();
		}catch(SQLException e){
			//e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	public static void closeRs(ResultSet rs){
		try{
			if(rs!=null)rs.close();
		}catch(SQLException e){
			//e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
		}
	}
}
