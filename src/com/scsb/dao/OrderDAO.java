package com.scsb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.scsb.dao.DatabaseDAO;
import com.scsb.bean.Phone;
import com.scsb.bean.Smppuser;
import com.scsb.bean.Order;

public class OrderDAO {

	private Connection con;
	private PreparedStatement pt;
	private ResultSet rs;
	public long create(Order order){
		long i = 0;
		con=DatabaseDAO.getConnection();
		try{
			for (String number : order.getReq_send_number()){
				pt = con.prepareStatement("insert into scsbsmpp.order(create_time, create_ip, spent_point, " +
						"mode ,req_msg ,req_schedule ,req_multiple, req_remark, status, smppuser_id, uid, number, area, smppuser_com)"+
						" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			
				Smppuser smppuser = order.getSmppuser();
			
				pt.setTimestamp(1, new Timestamp(order.getCreateTime()));
				pt.setString(2, order.getCreateIp());
				/*if(number.charAt(0)=='0'|| number.charAt(0)=='+'){
					pt.setInt(1, 1);
					}else{
					pt.setInt(1, 3);
					}*/
				pt.setInt(3,order.getSpent_point());
				System.out.println("該筆扣點:"+order.getSpent_point());
				pt.setInt(4, order.getMode());
				pt.setString(5, order.getReq_msg());
				pt.setString(6, order.getReq_schedule());
				pt.setString(7, order.getReq_multiple());
				pt.setString(8, order.getReq_remark());;
				pt.setInt(9, order.getStatus());
				pt.setInt(10, Integer.parseInt(smppuser.getId()));
				pt.setString(11, order.getUid());
				pt.setString(12, number);
				if(number.charAt(0)=='0' && number.charAt(1)=='9' || number.charAt(0)=='8' && number.charAt(1)=='8' && number.charAt(2)=='6' && number.charAt(3)=='9')
				{
					//System.out.println("Step Y:" + number.charAt(0));
					pt.setInt(13, 0);
				}
				else if(number.charAt(0)=='0' && number.charAt(1)=='2')
				{
					pt.setInt(13, 2);	
				}
				else
				{
					//System.out.println("Step Z:" + number.charAt(0));
					pt.setInt(13, 1);
				}
				pt.setString(14, order.getSmppuser_com());
				//System.out.println("Step X:" + number.charAt(0));
			
				//System.out.println("SQL:" + con.);
				i=pt.executeUpdate();
			
				if( i != 0 ){
					rs = pt.getGeneratedKeys();
					if (rs.next()) {
						i = rs.getLong(1);
					}
				}

			
//				if ( i != 0 ){				
//					for (String number1 : order.getReq_send_number()){
//						pt = con.prepareStatement("insert into scsbsmpp.orderdetail(req_send_number, order_id)"+
//							" values (?,?)");
//						pt.setString(1, number1);
//						pt.setLong(2, i);
//						pt.executeUpdate();
//					}
//				}
			
				if ( i != 0 ){
					pt = con.prepareStatement("update scsbsmpp.smppuser set point=point-? where id=? ", Statement.RETURN_GENERATED_KEYS);
					pt.setInt(1, order.getSpent_point());
					System.out.println("應扣點數:"+order.getSpent_point());
					/*if(number.charAt(0)=='0'|| number.charAt(0)=='+'){
						pt.setInt(1, 1);
						}else{
						pt.setInt(1, 3);
						}*/
					pt.setInt(2, Integer.parseInt(smppuser.getId()));
					pt.executeUpdate();
				}
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
	
	public Order getOrder(long order_id){
		Order order = null;
		Smppuser smppuser = null;
		con=DatabaseDAO.getConnection();
		try{
			pt=con.prepareStatement("select * from scsbsmpp.order where order_id=?");
			pt.setLong(1, order_id);
			rs=pt.executeQuery();
			while(rs.next()){
				order = new Order();
				order.setId(rs.getInt("order_id"));
				order.setCreateTime(rs.getLong("create_time"));
				order.setCreateIp(rs.getString("create_ip"));
				order.setSpent_point(rs.getInt("spent_point"));
				order.setMode(rs.getInt("mode"));
				order.setReq_msg(rs.getString("req_msg"));
				order.setReq_schedule(rs.getString("req_schedule"));
				order.setReq_multiple(rs.getString("req_multiple"));
				order.setReq_remark(rs.getString("req_remark"));
				order.setRes_data(rs.getString("res_data"));
				order.setRes_response(rs.getString("res_response"));
				order.setStatus(rs.getInt("status"));
				order.setUid(rs.getString("uid"));
				order.setArea(rs.getInt("area"));
				order.setSmppuser_com(rs.getString("smppuser_com"));
				order.setSmppuser(smppuser);
			}
			
			pt=con.prepareStatement("select count(order_id) as num from scsbsmpp.orderdetail where order_id=?");
			pt.setLong(1, order_id);
			rs=pt.executeQuery();
			
			if(rs.next()){
				int num = rs.getInt("num");
				String[] req_send_number = new String[num]; 
				
				pt=con.prepareStatement("select * from scsbsmpp.orderdetail where order_id=?");
				pt.setLong(1, order_id);
				rs=pt.executeQuery();

				for(int i = 0 ; i < num ; i++){
					rs.next();
					req_send_number[i] = rs.getString("req_send_number");
				}
				
				order.setReq_send_number(req_send_number);
				
			}			

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DatabaseDAO.closeCon(con);
			DatabaseDAO.closePt(pt);
			DatabaseDAO.closeRs(rs);
		}
		return order;
	}
	
	public ArrayList<Order> getOrders(String stype,String search_str) {
		ArrayList<Order> list = new ArrayList<Order>();
		Order order = null;
		con = DatabaseDAO.getConnection();
		String sql = null;
		try{
			if( stype.equals("1") )
			{
				sql = "select * from `order` o where area = '" + search_str + "'";
			}
			else if( stype.equals("2"))
			{
				String[] date = search_str.split(",");
				sql = "select * from `order` o where create_time >='" + date[0] + "' and create_time <='" + date[1] + "';";
			}
			else if( stype.equals("3"))
			{
				sql = "select * from `order` o where number like '%" + search_str + "%'";
			}
			else if( stype.equals("4"))
			{
				System.out.println(search_str);
				if(search_str.equals("5")){
					sql = "select * from `scsbsmpp`.`order` o where status = 5 OR status = 3 OR status = 4 OR status = 7 OR status = 8";
				}else if(search_str.equals("2")){
					sql = "select * from `order` o  where status = '" + search_str + "' or status = 9";
				}else if(search_str.equals("0")){
					sql = "select * from `order` o  where status = '" + search_str + "' or status = 1 or status = 6 or status = 97 or status = 98 or status = 99";
				}
			}
			else if( stype.equals("5"))
			{
				sql = "select * from `order` o  where smppuser_id like '%" + search_str + "%'";
			}
			else if( stype.equals("6"))
			{
				sql = "select * from `order` o  where smppuser_com like '%" + search_str + "%'";
			}
			System.out.println(sql);
			pt=con.prepareStatement(sql);
			rs=pt.executeQuery();
			
			while(rs.next()){
				order = new Order();
				order.setId(rs.getInt("o.order_id"));
				order.setCreateTime(rs.getLong("o.create_time"));
				order.setCreateTime2(rs.getString("o.create_time"));
				order.setCreateIp(rs.getString("o.create_ip"));
				order.setSpent_point(rs.getInt("o.spent_point"));
				order.setMode(rs.getInt("o.mode"));
				order.setReq_msg(rs.getString("o.req_msg"));
				order.setReq_schedule(rs.getString("o.req_schedule"));
				order.setReq_multiple(rs.getString("o.req_multiple"));
				order.setReq_remark(rs.getString("o.req_remark"));
				order.setRes_data(rs.getString("o.res_data"));
				order.setRes_response(rs.getString("o.res_response"));
				order.setStatus(rs.getInt("o.status"));
				order.setUid(rs.getString("uid"));
				order.setNumber(rs.getString("number"));
				order.setArea(rs.getInt("area"));
				order.setSmppuser_com(rs.getString("smppuser_com"));
				order.setSmppuser_id(rs.getString("smppuser_id"));
				list.add(order);
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
	
	public ArrayList<Order> getOrders(){
		ArrayList<Order> list = new ArrayList<Order>();
		Order order = null;
		Smppuser smppuser = null;
		con=DatabaseDAO.getConnection();
		try{

			pt=con.prepareStatement("select * from `order`");
			rs=pt.executeQuery();

			while(rs.next()){
				order = new Order();
				order.setId(rs.getInt("order_id"));
				order.setCreateTime(rs.getLong("create_time"));
				order.setCreateTime2(rs.getString("create_time"));
				order.setCreateIp(rs.getString("create_ip"));
				order.setSpent_point(rs.getInt("spent_point"));
				order.setMode(rs.getInt("mode"));
				order.setReq_msg(rs.getString("req_msg"));
				order.setReq_schedule(rs.getString("req_schedule"));
				order.setReq_multiple(rs.getString("req_multiple"));
				order.setReq_remark(rs.getString("req_remark"));
				order.setRes_data(rs.getString("res_data"));
				order.setRes_response(rs.getString("res_response"));
				order.setStatus(rs.getInt("status"));
				order.setSmppuser_id(rs.getString("smppuser_id"));
				order.setUid(rs.getString("uid"));
				order.setArea(rs.getInt("area"));
				order.setNumber(rs.getString("number"));
				order.setSmppuser_com(rs.getString("smppuser_com"));
				list.add(order);
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
	
	public ArrayList<Order> getOrder(Smppuser smppuser,String stype,String search_str) {
		ArrayList<Order> list = new ArrayList<Order>();
		Order order = null;
		con = DatabaseDAO.getConnection();
		String sql = null;
		try{
			if( stype.equals("1") )
			{
				sql = "select * from `order` o  where o.smppuser_id = ? and area = '" + search_str + "'";
			}
			else if( stype.equals("2"))
			{
				String[] date = search_str.split(",");
				sql = "select * from `order` o  where o.smppuser_id = ? and create_time >='" + date[0] + "' and create_time <='" + date[1] + "';";
			}
			else if( stype.equals("3"))
			{
				sql = "select * from `order` o  where o.smppuser_id = ? and number like '%" + search_str + "%'";
			}
			else if( stype.equals("4"))
			{
				System.out.println(search_str);
				if(search_str.equals("5")){
					sql = "select * from `scsbsmpp`.`order` o where (status = 5 OR status = 3 OR status = 4 OR status = 7 OR status = 8) and o.smppuser_id=?";
				}else if(search_str.equals("2")){
					sql = "select * from `order` o  where (status = '" + search_str + "' OR status = 9) and o.smppuser_id = ?";
				}else if(search_str.equals("0")){
					sql = "select * from `order` o  where (status = '" + search_str + "' OR status = 1 OR status = 6 OR status = 97 OR status = 98 OR status = 99) and o.smppuser_id = ?";
				}
			}
			pt=con.prepareStatement(sql);
			pt.setInt(1, Integer.parseInt(smppuser.getId()));
			rs=pt.executeQuery();
			
			while(rs.next()){
				order = new Order();
				order.setId(rs.getInt("o.order_id"));
				order.setCreateTime(rs.getLong("o.create_time"));
				order.setCreateTime2(rs.getString("o.create_time"));
				order.setCreateIp(rs.getString("o.create_ip"));
				order.setSpent_point(rs.getInt("o.spent_point"));
				order.setMode(rs.getInt("o.mode"));
				order.setReq_msg(rs.getString("o.req_msg"));
				order.setReq_schedule(rs.getString("o.req_schedule"));
				order.setReq_multiple(rs.getString("o.req_multiple"));
				order.setReq_remark(rs.getString("o.req_remark"));
				order.setRes_data(rs.getString("o.res_data"));
				order.setRes_response(rs.getString("o.res_response"));
				order.setStatus(rs.getInt("o.status"));
				order.setSmppuser(smppuser);
				order.setUid(rs.getString("uid"));
				order.setNumber(rs.getString("number"));
				order.setArea(rs.getInt("area"));
				list.add(order);
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
	
	public ArrayList<Order> getOrder(Smppuser smppuser){
		ArrayList<Order> list = new ArrayList<Order>();
		Order order = null;
		con=DatabaseDAO.getConnection();
		try{

			pt=con.prepareStatement("select * from `order` o  where o.smppuser_id = ?");
			pt.setInt(1, Integer.parseInt(smppuser.getId()));
			rs=pt.executeQuery();

			while(rs.next()){
				order = new Order();
				order.setId(rs.getInt("o.order_id"));
				order.setCreateTime(rs.getLong("o.create_time"));
				order.setCreateTime2(rs.getString("o.create_time"));
				order.setCreateIp(rs.getString("o.create_ip"));
				order.setSpent_point(rs.getInt("o.spent_point"));
				order.setMode(rs.getInt("o.mode"));
				order.setReq_msg(rs.getString("o.req_msg"));
				order.setReq_schedule(rs.getString("o.req_schedule"));
				order.setReq_multiple(rs.getString("o.req_multiple"));
				order.setReq_remark(rs.getString("o.req_remark"));
				order.setRes_data(rs.getString("o.res_data"));
				order.setRes_response(rs.getString("o.res_response"));
				order.setStatus(rs.getInt("o.status"));
				order.setSmppuser(smppuser);
				order.setUid(rs.getString("uid"));
				order.setNumber(rs.getString("number"));
				order.setArea(rs.getInt("area"));
				list.add(order);
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
	
}
