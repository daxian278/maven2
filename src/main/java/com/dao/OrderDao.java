package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.model.Order;
import com.util.StringUtil;

public class OrderDao {
    public int order_goodsAdd(Connection con ,Order order) throws Exception{
        String sql="insert into `order_goods` values(null,?,?,?,?,?,?)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, order.getOrderId());
        pstmt.setFloat(2, order.getGoodsTotalPrice());
        pstmt.setInt(3, order.getGoodsId());
        pstmt.setFloat(4, order.getGoodsPrice());
        pstmt.setInt(5, order.getGoodsNum());
        pstmt.setString(6, order.getGoodsName());
        return pstmt.executeUpdate();
    }

    public int order_infoAdd(Connection con ,Order order) throws Exception{
        String sql="insert into `order_info` values(?,?,?,?,?)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, order.getOrderId());
        pstmt.setInt(2, order.getOrderStatus());
        pstmt.setInt(3, order.getOrderNum());
        pstmt.setFloat(4, order.getOrderTotalMoney());
        pstmt.setString(5, order.getUserName());

        return pstmt.executeUpdate();
    }
    public ResultSet orderList(Connection con,Order order) throws Exception{
        StringBuffer sb=new StringBuffer("select * from `order_info`");
        if(StringUtil.isNotEmpty(order.getOrderId())){
            sb.append(" and orderId like '%"+order.getOrderId()+"%'");
        }
        if(StringUtil.isNotEmpty(order.getUserName())){
            sb.append(" and userName like '%"+order.getUserName()+"%'");
        }
        PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        return pstmt.executeQuery();
    }

    public ResultSet orderGoodsList(Connection con,Order order) throws Exception{
        StringBuffer sb=new StringBuffer("select * from `order_goods`");
        if(StringUtil.isNotEmpty(order.getOrderId())){
            sb.append(" and orderId like '%"+order.getOrderId()+"%'");
        }
        PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        return pstmt.executeQuery();
    }

    public int orderStatusModify(Connection con,Order order) throws Exception{
        String sql="update order_info set orderStatus=? where orderId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setInt(1, order.getOrderStatus());
        pstmt.setString(2, order.getOrderId());
        return pstmt.executeUpdate();
    }
    //ͬʱɾ��order_goods��irder_info���ű�����й�orderId������ ɾ��˳��Ҫ��֤���������˳�򣬲����Ի���˳��
    public int deleteOrder(Connection con,Order order) throws Exception{
        String sql="delete from order_goods where orderId=?";
        PreparedStatement pstmt1=con.prepareStatement(sql);
        pstmt1.setString(1, order.getOrderId());
        pstmt1.executeUpdate();
        sql="delete from order_info where orderId=?";
        PreparedStatement pstmt2=con.prepareStatement(sql);
        pstmt2.setString(1, order.getOrderId());
        return pstmt2.executeUpdate();
    }
}

