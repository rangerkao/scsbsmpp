package com.scsb.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.scsb.dao.DatabaseDAO;

public class PhoneBook {
	private ArrayList<Phone> phones = new ArrayList<Phone>();
	private Smppuser smppuser;	
	private Connection con;
	private PreparedStatement pt;
	private Statement st;
	private ResultSet rs;

	public ArrayList<Phone> getPhones(Smppuser user) {
		this.smppuser = user;
		con = DatabaseDAO.getConnection();
		
		try{
			pt=con.prepareStatement("select a.phone_id,a.number,a.description,a.create_time,a.phone_group from phone as a, phonebook as b where b.smppuser_id='" + smppuser.getId() + "' and a.phone_id=b.phone_id");
			rs=pt.executeQuery();

			while(rs.next()){
				Phone uph = new Phone();
				uph.setID(rs.getInt("phone_id"));
				uph.setDescription(rs.getString("description"));
				uph.setNumber(rs.getString("number"));
				uph.setCreatTime(rs.getString("create_time"));
				uph.setGroup(rs.getString("phone_group"));
				phones.add(uph);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DatabaseDAO.closeRs(rs);			
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeCon(con);
		}
		return phones;
	}
	public ArrayList<Phone> getPhones(Smppuser user,String stype,String search_str) {
		this.smppuser = user;
		con = DatabaseDAO.getConnection();
		String sql = null;
		try{
			if( stype.equals("1") )
			{
				sql = "select a.phone_id,a.number,a.description,a.create_time,a.phone_group from phone as a, phonebook as b where b.smppuser_id='" + smppuser.getId() + "' and a.phone_id=b.phone_id and number like '%" + search_str + "%'";
			}
			else if( stype.equals("2"))
			{
				sql = "select a.phone_id,a.number,a.description,a.create_time,a.phone_group from phone as a, phonebook as b where b.smppuser_id='" + smppuser.getId() + "' and a.phone_id=b.phone_id and phone_group like '%" + search_str + "%'";
			}
			pt=con.prepareStatement(sql);
			rs=pt.executeQuery();
			
			while(rs.next()){
				Phone uph = new Phone();
				uph.setID(rs.getInt("phone_id"));
				uph.setDescription(rs.getString("description"));
				uph.setNumber(rs.getString("number"));
				uph.setCreatTime(rs.getString("create_time"));
				uph.setGroup(rs.getString("phone_group"));
				phones.add(uph);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DatabaseDAO.closeRs(rs);			
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeCon(con);
		}
		return phones;
	}
	public void setPhones(ArrayList<Phone> phone_list) {
		this.phones = phone_list;
	}
	public void addPhone(String uid,Phone newphone) {
		con=DatabaseDAO.getConnection();
		int numero = 0;
		try{
			st = con.createStatement();
			numero = st.executeUpdate("insert into phone(number,description,phone_group) values ('" + newphone.getNumber() + "','" + newphone.getDescription() + "','" + newphone.getGroup() + "');");	    
		    rs = st.executeQuery("SELECT LAST_INSERT_ID() as LID;");
		    if( rs.next() ) 
		    {
		    	numero=st.executeUpdate("insert into phonebook(phone_id,smppuser_id) values ('" + rs.getString("LID") + "','" + uid + "');");
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
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeCon(con);
		}
	}
	public void editPhone(Phone phone) {
		con = DatabaseDAO.getConnection();
		String sql = null;
		if ( phone.getID() == 0)
		{
			return;
		}
		
		if( phone.getNumber() != null && phone.getDescription() != null && phone.getGroup() != null)
		{
			sql = "update phone set number='" + phone.getNumber() + "', description='" + phone.getDescription() + "', phone_group='" + phone.getGroup() +  "' where phone_id ='" + phone.getID()+ "'"; 
		}
		else if( phone.getNumber() != null )
		{
			sql = sql + " number='" + phone.getNumber() + "' where phone_id ='" + phone.getID()+ "'";
		}
		else if( phone.getDescription() != null )
		{
			sql = sql + " description='" + phone.getDescription() + "' where phone_id ='" + phone.getID()+ "'";
		}
		else if( phone.getGroup() != null )
		{
			sql = sql + " phone_group='" + phone.getGroup() + "' where phone_id ='" + phone.getID()+ "'";
		}
			
		try{
			pt=con.prepareStatement(sql);
			int rtn=pt.executeUpdate();
			if( rtn == 0)
			{
				throw new SQLException("Update phone failed, no rows affected.");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DatabaseDAO.closeRs(rs);			
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeCon(con);
		}
	}
	public void setSmppuser(Smppuser smppuser) {
		this.smppuser = smppuser;
	}
	public void deletePhone(String uid,String phone_id)
	{
		con = DatabaseDAO.getConnection();
		String sql = null;
		try{
			sql = "delete from phone where phone_id='" + phone_id +"'";
			pt=con.prepareStatement(sql);
			int rtn=pt.executeUpdate();
			if( rtn == 0)
			{
				throw new SQLException("delete phone failed, no rows affected.");
			}
			sql = "delete from phonebook where phone_id = '" + phone_id + "' and smppuser_id = '" + uid + "'";
			pt=con.prepareStatement(sql);
			rtn=pt.executeUpdate();
			if( rtn == 0)
			{
				throw new SQLException("delete phonebook failed, no rows affected.");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DatabaseDAO.closeRs(rs);			
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeCon(con);
		}
		
	}
	
	public void deleteGroup(String uid,String group_str,String phone_id)
	{
		con = DatabaseDAO.getConnection();
		String sql = null;
		try{
			sql = "delete from phone where phone_group='" + group_str +"'";
			pt=con.prepareStatement(sql);
			int rtn=pt.executeUpdate();
			if( rtn == 0)
			{
				throw new SQLException("delete phone failed, no rows affected.");
			}
			sql = "delete from phonebook where phone_id = '" + phone_id + "' and smppuser_id = '" + uid + "'";
			pt=con.prepareStatement(sql);
			rtn=pt.executeUpdate();
			if( rtn == 0)
			{
				throw new SQLException("delete phonebook failed, no rows affected.");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DatabaseDAO.closeRs(rs);			
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeCon(con);
		}
		
	}
	
	public ArrayList<String> getGroups(Smppuser user) {
		this.smppuser = user;
		con = DatabaseDAO.getConnection();
		ArrayList<String> groups = new ArrayList<String>();
		try{
			pt=con.prepareStatement("select a.phone_group from phone as a, phonebook as b where b.smppuser_id='" + smppuser.getId() + "' and a.phone_id=b.phone_id and a.phone_group is not null group by a.phone_group");
			rs=pt.executeQuery();

			while(rs.next()){
				String group = new String();
				group = rs.getString("phone_group");
				groups.add(group);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DatabaseDAO.closeRs(rs);			
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeCon(con);
		}
		return groups;
	}
}
