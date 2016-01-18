package com.scsb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.scsb.bean.Smppuser;
import com.scsb.bean.PointRecord;;

public class PointRecordDAO {
	private Connection con;
	private PreparedStatement pt;
	private ResultSet rs;

	public PointRecord getPointRecord(long point_id){
		PointRecord pointRecord = null;
		Smppuser smppuser = null;
		con=DatabaseDAO.getConnection();
		try{

			pt=con.prepareStatement("select * from `point_record` p where p.point_id = ?");
			pt.setLong(1, point_id);
			rs=pt.executeQuery();

			while(rs.next()){
				pointRecord = new PointRecord();
				pointRecord.setId(rs.getInt("p.point_id"));
				pointRecord.setAddAmount(rs.getInt("p.add_amount"));
				pointRecord.setAddPrice(rs.getInt("p.add_price"));
				pointRecord.setAddTime(rs.getLong("p.add_time"));
				pointRecord.setAdd_remark(rs.getString("p.add_remark"));
				pointRecord.setSmppuser_com(rs.getString("p.smppuser_com"));
				pointRecord.setSmppuser(smppuser);
					
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DatabaseDAO.closeCon(con);
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeRs(rs);
		}
		return pointRecord;
	}

	public ArrayList<PointRecord> getPointRecords(){
		ArrayList<PointRecord> list = new ArrayList<PointRecord>();
		PointRecord pointRecord = null;
		con=DatabaseDAO.getConnection();
		try{

			pt=con.prepareStatement("select * from `point_record`");
			rs=pt.executeQuery();

			while(rs.next()){
				pointRecord = new PointRecord();
				pointRecord.setId(rs.getInt("point_id"));
				pointRecord.setAddAmount(rs.getInt("add_amount"));
				pointRecord.setAddPrice(rs.getInt("add_price"));
				pointRecord.setAddTime(rs.getLong("add_time"));
				pointRecord.setAddTime2(rs.getString("add_time"));
				pointRecord.setAdd_remark(rs.getString("add_remark"));
				pointRecord.setSmppuser_com(rs.getString("smppuser_com"));
				pointRecord.setSmppuser_id(rs.getInt("smppuser_id"));
				
				list.add(pointRecord);
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
	
	public ArrayList<PointRecord> getPointRecords(String stype,String search_str){
		ArrayList<PointRecord> list = new ArrayList<PointRecord>();
		PointRecord pointRecord = null;
		String sql = new String();
		con=DatabaseDAO.getConnection();
		try{
			if( stype.equals("1") )
			{
				sql = "select * from `point_record` where smppuser_id = '" + search_str + "'";
			}
			else if( stype.equals("2") )
			{
				String[] date = search_str.split(",");
				sql = "select * from `point_record` where add_time >='" + date[0] + "' and add_time <= '" + date[1] + "'";
			}
			else if( stype.equals("3") )
			{
				sql = "select * from `point_record` where smppuser_com like '%" + search_str + "%'";
			}
			System.out.println(sql);	
			pt=con.prepareStatement(sql);
			//pt.setInt(1, Integer.parseInt(smppuser.getId()));
			rs=pt.executeQuery();

			while(rs.next()){
				pointRecord = new PointRecord();
				pointRecord.setId(rs.getInt("point_id"));
				pointRecord.setAddAmount(rs.getInt("add_amount"));
				pointRecord.setAddPrice(rs.getInt("add_price"));
				pointRecord.setAddTime(rs.getLong("add_time"));
				pointRecord.setAddTime2(rs.getString("add_time"));
				pointRecord.setAdd_remark(rs.getString("add_remark"));
				pointRecord.setSmppuser_com(rs.getString("smppuser_com"));
				pointRecord.setSmppuser_id(rs.getInt("smppuser_id"));
				
				list.add(pointRecord);
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
	
	public ArrayList<PointRecord> getPointRecord(Smppuser smppuser){
		ArrayList<PointRecord> list = new ArrayList<PointRecord>();
		PointRecord pointRecord = null;
		con=DatabaseDAO.getConnection();
		try{

			pt=con.prepareStatement("select * from `point_record` p where p.smppuser_id = ?");
			pt.setInt(1, Integer.parseInt(smppuser.getId()));
			rs=pt.executeQuery();

			while(rs.next()){
				pointRecord = new PointRecord();
				pointRecord.setId(rs.getInt("p.point_id"));
				pointRecord.setAddAmount(rs.getInt("p.add_amount"));
				pointRecord.setAddPrice(rs.getInt("p.add_price"));
				pointRecord.setAddTime(rs.getLong("p.add_time"));
				pointRecord.setAddTime2(rs.getString("p.add_time"));
				pointRecord.setAdd_remark(rs.getString("p.add_remark"));
				pointRecord.setSmppuser_id(rs.getInt("smppuser_id"));
				
				list.add(pointRecord);
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
	
	public ArrayList<PointRecord> getPointRecord(Smppuser smppuser,String stype,String search_str){
		ArrayList<PointRecord> list = new ArrayList<PointRecord>();
		PointRecord pointRecord = null;
		String sql = new String();
		con=DatabaseDAO.getConnection();
		try{
			if( stype.equals("1") )
			{
				sql = "select * from `point_record` where smppuser_id = '" + search_str + "'";
			}
			else if( stype.equals("2") )
			{
				String[] date = search_str.split(",");
				sql = "select * from `point_record` where smppuser_id = '" + smppuser.getId() + "' and add_time >='" + date[0] + "' and add_time <= '" + date[1] + "'";
			}
			System.out.println(sql);	
			pt=con.prepareStatement(sql);
			//pt.setInt(1, Integer.parseInt(smppuser.getId()));
			rs=pt.executeQuery();

			while(rs.next()){
				pointRecord = new PointRecord();
				pointRecord.setId(rs.getInt("point_id"));
				pointRecord.setAddAmount(rs.getInt("add_amount"));
				pointRecord.setAddPrice(rs.getInt("add_price"));
				pointRecord.setAddTime(rs.getLong("add_time"));
				pointRecord.setAddTime2(rs.getString("add_time"));
				pointRecord.setAdd_remark(rs.getString("add_remark"));
				pointRecord.setSmppuser_id(rs.getInt("smppuser_id"));
				
				list.add(pointRecord);
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

	public long add(PointRecord pointRecord){
		long i=0; 
		con=DatabaseDAO.getConnection();
		try{
			
			pt = con.prepareStatement("insert into scsbsmpp.point_record(add_amount,add_price,add_time,add_remark,smppuser_com,smppuser_id)"
									+ "value(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			Smppuser smppuser = pointRecord.getSmppuser();
			
			pt.setInt(1, pointRecord.getAddAmount());
			pt.setInt(2, pointRecord.getAddPrice());
			pt.setTimestamp(3, new Timestamp(pointRecord.getAddTime()));
			pt.setString(4, pointRecord.getAdd_remark());
			pt.setString(5, pointRecord.getSmppuser_com());
			pt.setInt(6, pointRecord.getSmppuser_id());
			
			//System.out.println(pt.toString());
			i=pt.executeUpdate();
			if ( i != 0 ){
				rs = pt.getGeneratedKeys();
				if (rs.next()) {
				    i = rs.getLong(1);
				}
			}
			if ( i != 0 ){
				pt = con.prepareStatement("update scsbsmpp.smppuser set point=point+? where id=? ");
				pt.setInt(1, pointRecord.getAddAmount());
				pt.setInt(2, pointRecord.getSmppuser_id());
				pt.executeUpdate();
			}

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DatabaseDAO.closeCon(con);
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeRs(rs);
		}
		return i;
	}
}
