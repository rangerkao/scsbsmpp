package com.scsb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scsb.dao.DatabaseDAO;
import com.scsb.bean.PointRecord;
import com.scsb.bean.Smppuser;
import com.scsb.bean.Log;
import com.scsb.bean.Organization;

public class SmppuserDAO {
	private Connection con;
	private PreparedStatement pt;
	private ResultSet rs;
	
	public boolean findSmppuser(String username){
		boolean flag=false;
		con=DatabaseDAO.getConnection();
		try{
			pt=con.prepareStatement("select * from smppuser where username =?");
			pt.setString(1, username);
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
	public String findSmppuserid(String username){
		String uid=null;
		con=DatabaseDAO.getConnection();
		try{
			pt=con.prepareStatement("select * from smppuser where username =?");
			pt.setString(1, username);
			rs=pt.executeQuery();
			while(rs.next()){
				uid=rs.getString(0);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DatabaseDAO.closeCon(con);
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeRs(rs);
		}
		return uid;
	}
	
	public boolean findPoint(String username,String point){
		boolean flag=false;
		con=DatabaseDAO.getConnection();
		try{
			pt=con.prepareStatement("select * from smppuser where username =? and point =?");
			pt.setString(1, username);
			pt.setString(2, point);
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
	
	public ArrayList<Smppuser> getSmppusers(){
		ArrayList<Smppuser> list = new ArrayList<Smppuser>();
		Smppuser smppuser = null;
		Organization organization = null;
		con=DatabaseDAO.getConnection();
		try{
			pt=con.prepareStatement("select * from smppuser");
			rs=pt.executeQuery();
			
			while(rs.next()){
				smppuser = new Smppuser();
				smppuser.setId(rs.getString("id"));
				smppuser.setUsername(rs.getString("username"));
				smppuser.setPassword(rs.getString("password"));
				smppuser.setCompany(rs.getString("company"));
				smppuser.setName(rs.getString("name"));
				smppuser.setGender(rs.getString("gender"));
				smppuser.setEmail(rs.getString("email"));
				smppuser.setAddress(rs.getString("address"));
				smppuser.setPhone(rs.getString("phone"));
				smppuser.setMobile(rs.getString("mobile"));
				smppuser.setFax(rs.getString("fax"));
				smppuser.setRid(rs.getString("rid"));
				smppuser.setPoint(rs.getString("point"));
				smppuser.setVerify_email(rs.getString("verify_email"));
				smppuser.setVerify_email_code(rs.getString("verify_email_code"));
				smppuser.setVerify_mobile(rs.getString("verify_mobile"));
				smppuser.setVerify_mobile_code(rs.getString("verify_mobile_code"));
				smppuser.setStatus(rs.getString("status"));
				smppuser.setCreate_ip(rs.getString("create_ip"));
				smppuser.setCreate_time(rs.getString("create_time"));
				smppuser.setLogin_ip(rs.getString("login_ip"));
				smppuser.setLogin_time(rs.getString("login_time"));
				smppuser.setEdit_ip(rs.getString("edit_ip"));
				smppuser.setEdit_time(rs.getString("edit_time"));
				smppuser.setPostcode(rs.getString("postcode"));
				//organization = new Organization();
				//organization.setId(rs.getInt("org_id"));
				//organization.setName(rs.getString("org_name"));
				//organization.setCode(rs.getString("org_code"));
				smppuser.setOrganization(organization);
				
				list.add(smppuser);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DatabaseDAO.closeCon(con);
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeRs(rs);
		}
		return list;
	}
	
	public ArrayList<Smppuser> getSmppusers(String stype,String search_str){
		ArrayList<Smppuser> list = new ArrayList<Smppuser>();
		Smppuser smppuser = null;
		Organization organization = null;
		String sql = new String();
		con=DatabaseDAO.getConnection();
		try{
			if( stype.equals("1") )
			{
				sql = "select * from `smppuser` where id = '" + search_str + "'";
			}else if( stype.equals("2") )
			{
				sql = "select * from `smppuser` where company like '%" + search_str + "%'";
			}
			System.out.println(sql);	
			pt=con.prepareStatement(sql);
			//pt.setInt(1, Integer.parseInt(smppuser.getId()));
			rs=pt.executeQuery();

			while(rs.next()){
				smppuser = new Smppuser();
				smppuser.setId(rs.getString("id"));
				smppuser.setUsername(rs.getString("username"));
				smppuser.setPassword(rs.getString("password"));
				smppuser.setCompany(rs.getString("company"));
				smppuser.setName(rs.getString("name"));
				smppuser.setGender(rs.getString("gender"));
				smppuser.setEmail(rs.getString("email"));
				smppuser.setAddress(rs.getString("address"));
				smppuser.setPhone(rs.getString("phone"));
				smppuser.setMobile(rs.getString("mobile"));
				smppuser.setFax(rs.getString("fax"));
				smppuser.setRid(rs.getString("rid"));
				smppuser.setPoint(rs.getString("point"));
				smppuser.setVerify_email(rs.getString("verify_email"));
				smppuser.setVerify_email_code(rs.getString("verify_email_code"));
				smppuser.setVerify_mobile(rs.getString("verify_mobile"));
				smppuser.setVerify_mobile_code(rs.getString("verify_mobile_code"));
				smppuser.setStatus(rs.getString("status"));
				smppuser.setCreate_ip(rs.getString("create_ip"));
				smppuser.setCreate_time(rs.getString("create_time"));
				smppuser.setLogin_ip(rs.getString("login_ip"));
				smppuser.setLogin_time(rs.getString("login_time"));
				smppuser.setEdit_ip(rs.getString("edit_ip"));
				smppuser.setEdit_time(rs.getString("edit_time"));
				smppuser.setPostcode(rs.getString("postcode"));
				//organization = new Organization();
				//organization.setId(rs.getInt("org_id"));
				//organization.setName(rs.getString("org_name"));
				//organization.setCode(rs.getString("org_code"));
				smppuser.setOrganization(organization);
				
				list.add(smppuser);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DatabaseDAO.closeCon(con);
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeRs(rs);
		}
		return list;
	}
	
	public Smppuser getSmppuser(String id){
		Smppuser smppuser = null;
		Organization organization = null;
		con=DatabaseDAO.getConnection();
		try{
			pt=con.prepareStatement("select * from smppuser where id =?");
			pt.setString(1, id);
			rs=pt.executeQuery();
			if(rs.next()){
				smppuser = new Smppuser();
				smppuser.setId(rs.getString("id"));
				smppuser.setUsername(rs.getString("username"));
				smppuser.setPassword(rs.getString("password"));
				smppuser.setCompany(rs.getString("company"));
				smppuser.setName(rs.getString("name"));
				smppuser.setGender(rs.getString("gender"));
				smppuser.setEmail(rs.getString("email"));
				smppuser.setAddress(rs.getString("address"));
				smppuser.setPhone(rs.getString("phone"));
				smppuser.setMobile(rs.getString("mobile"));
				smppuser.setFax(rs.getString("fax"));
				smppuser.setRid(rs.getString("rid"));
				smppuser.setVerify_email(rs.getString("verify_email"));
				smppuser.setVerify_email_code(rs.getString("verify_email_code"));
				smppuser.setVerify_mobile(rs.getString("verify_mobile"));
				smppuser.setVerify_mobile_code(rs.getString("verify_mobile_code"));
				smppuser.setStatus(rs.getString("status"));
				smppuser.setCreate_ip(rs.getString("create_ip"));
				smppuser.setCreate_time(rs.getString("create_time"));
				smppuser.setLogin_ip(rs.getString("login_ip"));
				smppuser.setLogin_time(rs.getString("login_time"));
				smppuser.setEdit_ip(rs.getString("edit_ip"));
				smppuser.setEdit_time(rs.getString("edit_time"));
				smppuser.setPostcode(rs.getString("postcode"));
				smppuser.setPoint(rs.getString("point"));
				//organization = new Organization();
				//organization.setId(rs.getInt("org_id"));
				//organization.setName(rs.getString("org_name"));
				//organization.setCode(rs.getString("org_code"));
				smppuser.setOrganization(organization);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DatabaseDAO.closeCon(con);
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeRs(rs);
		}
		return smppuser;
	}
	
	public boolean verifyMail(String username, String verify_email_code, String ip, String time){
		boolean flag=false;
		con=DatabaseDAO.getConnection();
		try{
			pt=con.prepareStatement("select * from smppuser where username =? and verify_email_code =?");
			pt.setString(1, username);
			pt.setString(2, verify_email_code);
			rs=pt.executeQuery();
			if(rs.next()){
				flag=true;
			}
			
			if(flag==true){
				pt=con.prepareStatement("update smppuser set verify_email =?, edit_ip =?, edit_time =? where username =?");
				pt.setInt(1, 1);
				pt.setString(2,ip);
				pt.setString(3, time);
				pt.setString(4, username);
				pt.executeUpdate();
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
	
	public int save(Smppuser smppuser){
		int i=0;
		con=DatabaseDAO.getConnection();
		try{
			
			pt = con.prepareStatement("insert into smppuser(username,password,name,gender,"+
			"email,address,phone,fax,rid,point,verify_email,verify_email_code,verify_mobile,verify_mobile_code,"+
			"status,create_ip,create_time,login_ip,login_time,edit_ip,edit_time,postcode,org_id,company,mobile)"+
			" value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			pt.setString(1, smppuser.getUsername());
			pt.setString(2, smppuser.getPassword());
			pt.setString(3, smppuser.getName());
			pt.setInt(4, Integer.parseInt(smppuser.getGender()));
			pt.setString(5, smppuser.getEmail());
			pt.setString(6, smppuser.getAddress());
			pt.setString(7, smppuser.getPhone());
			pt.setString(8, smppuser.getFax());
			pt.setString(9, smppuser.getRid());
			pt.setInt(10, Integer.parseInt(smppuser.getPoint()));
			pt.setInt(11, Integer.parseInt(smppuser.getVerify_email()));
			pt.setString(12, smppuser.getVerify_email_code());
			pt.setInt(13, Integer.parseInt(smppuser.getVerify_mobile()));
			pt.setString(14, smppuser.getVerify_mobile_code());
			pt.setInt(15, Integer.parseInt(smppuser.getStatus()));
			pt.setString(16, smppuser.getCreate_ip());
			pt.setString(17, smppuser.getCreate_time());
			pt.setString(18, smppuser.getLogin_ip());
			pt.setString(19, smppuser.getLogin_time());
			pt.setString(20, smppuser.getEdit_ip());
			pt.setString(21, smppuser.getEdit_time());
			pt.setInt(22, Integer.parseInt(smppuser.getPostcode()));
			pt.setInt(23, Integer.parseInt(smppuser.getOrg_id()));
			pt.setString(24, smppuser.getCompany());
			pt.setString(25, smppuser.getMobile());
			
			
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
	
	public int edit(Smppuser smppuser){
		
		int i=0;
		con=DatabaseDAO.getConnection();
		try{
			
			pt=con.prepareStatement("update smppuser set password =?, gender =?, "+
			"email =?, address =?, phone =?,fax =?,rid =?, "+
			"verify_email =?, verify_email_code =?, verify_mobile =?, "+
			"verify_mobile_code =?, edit_ip =?, edit_time =?, "+
			"postcode =?, status =?, name =?, company =?, mobile =? where id =?");	
			pt.setString(1, smppuser.getPassword());
			pt.setInt(2, Integer.parseInt(smppuser.getGender()));
			pt.setString(3, smppuser.getEmail());
			pt.setString(4, smppuser.getAddress());
			pt.setString(5, smppuser.getPhone());
			pt.setString(6, smppuser.getFax());
			pt.setString(7, smppuser.getRid());
			pt.setInt(8, Integer.parseInt(smppuser.getVerify_email()));
			pt.setString(9, smppuser.getVerify_email_code());
			pt.setInt(10, Integer.parseInt(smppuser.getVerify_mobile()));
			pt.setString(11, smppuser.getVerify_mobile_code());
			pt.setString(12, smppuser.getEdit_ip());
			pt.setString(13, smppuser.getEdit_time());
			pt.setInt(14, Integer.parseInt(smppuser.getPostcode()));
			pt.setInt(15, Integer.parseInt(smppuser.getStatus()));
			pt.setString(16, smppuser.getName());
			pt.setString(17, smppuser.getCompany());
			pt.setString(18, smppuser.getMobile());
			pt.setInt(19, Integer.parseInt(smppuser.getId()));

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
	
public int editPassword(Smppuser smppuser){
		
		int i=0;
		con=DatabaseDAO.getConnection();
		try{
			
			pt=con.prepareStatement("update smppuser set password =? where id =?");	
			pt.setString(1, smppuser.getPassword());
			pt.setInt(2, Integer.parseInt(smppuser.getId()));

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
	
	public Smppuser login(String username, String password, String ip, String time){
		Smppuser smppuser = null;
		Organization organization = null;
		Log log = null;
		con=DatabaseDAO.getConnection();
		try{
			pt=con.prepareStatement("select s.id,"
					+ " s.username, s.password, s.company, s.name, s.gender, s.email, s.address, s.phone, s.mobile,"
					+ " s.fax, s.rid, s.point, s.verify_email, s.verify_email_code," 
					+ " s.verify_mobile, s.verify_mobile_code, s.status, s.create_ip, s.create_time,"
					+ " s.login_ip, s.login_time, s.edit_ip, s.edit_time, s.postcode,"
					+ " o.org_id, o.org_name, o.org_code"
					+ " from scsbsmpp.smppuser s join scsbsmpp.organization o on s.org_id = o.org_id"
					+ " where s.username=? and s.password=? ");
			pt.setString(1, username);
			pt.setString(2, password);
			System.out.println(pt.toString());
			rs=pt.executeQuery();
			
			if(rs.next()){
				smppuser = new Smppuser();
				organization = new Organization();
				smppuser.setId(rs.getString("id"));
				smppuser.setUsername(rs.getString("username"));
				smppuser.setPassword(rs.getString("password"));
				smppuser.setCompany(rs.getString("company"));
				smppuser.setName(rs.getString("name"));
				smppuser.setGender(rs.getString("gender"));
				smppuser.setEmail(rs.getString("email"));
				smppuser.setAddress(rs.getString("address"));
				smppuser.setPhone(rs.getString("phone"));
				smppuser.setMobile(rs.getString("mobile"));
				smppuser.setFax(rs.getString("fax"));
				smppuser.setRid(rs.getString("rid"));
				smppuser.setPoint(rs.getString("point"));
				smppuser.setVerify_email(rs.getString("verify_email"));
				smppuser.setVerify_email_code(rs.getString("verify_email_code"));
				smppuser.setVerify_mobile(rs.getString("verify_mobile"));
				smppuser.setVerify_mobile_code(rs.getString("verify_mobile_code"));
				smppuser.setStatus(rs.getString("status"));
				smppuser.setCreate_ip(rs.getString("create_ip"));
				smppuser.setCreate_time(rs.getString("create_time"));
				smppuser.setLogin_ip(rs.getString("login_ip"));
				smppuser.setLogin_time(rs.getString("login_time"));
				smppuser.setEdit_ip(rs.getString("edit_ip"));
				smppuser.setEdit_time(rs.getString("edit_time"));
				smppuser.setPostcode(rs.getString("postcode"));
				organization.setId(rs.getInt("o.org_id"));
				organization.setName(rs.getString("o.org_name"));
				organization.setCode(rs.getString("o.org_code"));
				smppuser.setOrganization(organization);
				
				pt=con.prepareStatement("update smppuser set login_ip =?, login_time =? where username =? and password =?");
				pt.setString(1, ip);
				pt.setString(2, time);
				pt.setString(3, username);
				pt.setString(4, password);
				pt.executeUpdate();
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DatabaseDAO.closeCon(con);
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeRs(rs);
		}
		return smppuser;
	}
}
