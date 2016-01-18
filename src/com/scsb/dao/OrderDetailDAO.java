package com.scsb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scsb.bean.Order;
import com.scsb.bean.OrderDetail;
import com.scsb.dao.DatabaseDAO;

public class OrderDetailDAO {
		private Connection con;
		private PreparedStatement pt;
		private ResultSet rs;
		

		public ArrayList<OrderDetail> getOrderDetail(long id){
			ArrayList<OrderDetail> list = new ArrayList<OrderDetail>();
			OrderDetail detail = null;
			con=DatabaseDAO.getConnection();
			try{

				pt=con.prepareStatement("select * from `orderdetail` od join `order` o on (od.order_id=o.order_id) "+
				                        "where od.order_id = ?");
				pt.setLong(1, id);
				rs=pt.executeQuery();
				
				while(rs.next()){
					detail = new OrderDetail();
					detail.setId(rs.getInt("od.orderdetail_id"));
					detail.setReq_send_number(rs.getString("od.req_send_number"));
					detail.setOrder_id(rs.getInt("od.order_id"));
					
					list.add(detail);
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
