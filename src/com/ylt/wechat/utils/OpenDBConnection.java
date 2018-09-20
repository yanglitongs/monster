package com.ylt.wechat.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OpenDBConnection {
	/**
	 * ���ڿ������ݿ�����
	 */
	public Connection OpenConnection(){
		Connection conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver") ;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/���ݿ�name", "username", "pwd");
			return conn;
			}catch(Exception e){
				e.printStackTrace();
				}
		return null ;
	}
	/**
	 * �����ͷ����ݿ���Դ
	 * @param conn :��ʾ���ݿ������
	 * @param stmt :��ʾsql���ִ�еĶ���
	 * @param rst  :��ʾ��ѯ�Ľ����
	 */
	public void close(Connection conn,Statement stmt,ResultSet rst){
		if(rst!=null){
			try {
				rst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				rst=null;
			}
		}
		if(stmt != null){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				stmt=null;
			}
		}
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				conn=null;
			}
		}
	}
	/**
	 * ���ڲ�ѯ����
	 */
	public String selectData(String sql){
		int count=0;
		String result="";
		Connection conn = OpenConnection();
		if(conn==null){
			return "�������ݿ�ʧ��";
		}
		StringBuffer sb=new StringBuffer();
		try {  
            Statement stmt = conn.createStatement();  
            ResultSet rs = stmt.executeQuery(sql);  
            while(rs.next()) {  
            	if(count>10){
            		break;
            	}
                //String name = rs.getString("user_id");  
                //String examTime = rs.getString("exam_time");  
                String examSchool = rs.getString("exam_school");  
                sb.append(examSchool);
                count++;
            }  
            result=sb.toString();  
        } catch (SQLException e) {  
        	result="�޷���ѯ";  
        }finally{
        	conn=null;  
        }
		return result;  
	}
	}
