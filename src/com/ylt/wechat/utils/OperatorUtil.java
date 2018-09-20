package com.ylt.wechat.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

public class OperatorUtil {
	public static Jedis jedis = null;
	public String open() {
		String status="";
		// 连接信息，从控制台可以获得
		String host = "r-2ze11122a9243ae4.redis.rds.aliyuncs.com";
		int port = 6379;
		if (jedis == null) {
			jedis = new Jedis(host, port);
			try {
				// 实例密码
				String authString = jedis.auth("Yanglt6952339");// password
				if (!authString.equals("OK")) {
					System.err.println("AUTH Failed: " + authString);
					jedis = null;
					status = "NO";
				}
				else
				{
					jedis.select(0);
					status = "OK";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return status;
	}
	public void quit() {
		jedis.quit();
		jedis = null;
	}
    /**
     * 获取账号绑定状态
     * @param fromUserName
     * @return
     */
	public String getBindStatus(String fromUserName){
		
		/*
		open();
		Set<Tuple> scoreList =jedis.zrevrangeWithScores(fromUserName, 0, -1);
		jedis.getSet(key, value)
		String ab="";
		for (Tuple item : scoreList) {
			if(item.getElement()=="name")
				ab = item.get.getScore();
            System.out.println("玩家ID："+item.getElement()+"， 玩家得分:"+Double.valueOf(item.getScore()).intValue());
        }  
		Connection conn=null;
	    PreparedStatement ps=null;
	    ResultSet rs=null;
		conn=new DBCPConnection().getConnection();
		String selectSql="select * from crm_stu_wechat where open_id='"+fromUserName+"'";
		if(conn==null){
			return "连接数据库失败";
		}else{
			try {
				ps=conn.prepareStatement(selectSql);
				rs=ps.executeQuery();
				if(rs.next()){
					return "用户名已绑定";
				}else{
					return "请输入\"用户名绑定\"+登录用户名  如:用户名绑定fangw";
				}
			} catch (SQLException e) {
				return "查询数据库失败";
			}finally{
				closeConnection(conn,ps,rs);
			}
		}
		*/
		return "";
	}
	public List<String> getAllUser(){
		List<String> list = new ArrayList<String>();
		/*
		Map<String,String> map= jedis.hgetAll("user");
		
		for(int i=0;i<map.size();i++)
		{
			
		}
			try {
				ps=conn.prepareStatement(selectSql);
				rs=ps.executeQuery();
				while(rs.next()){
					list.add(rs.getString("USERNAME"));
				}
			} catch (SQLException e) {
				return null;
			}finally{
				closeConnection(conn,ps,rs);
			}
		}
		*/
		return list;
	}
	/**
	 * 绑定账号
	 * @param fromUserName
	 * @param userName
	 * @return
	 */
	public String bindAccount(String fromUserName,String userName){

		/*
		String result="";

		try{
			result = open();
		if("NO".equals(result)){
			return result = "连接数据库失败";
		}
		int i = jedis.hset("user", fromUserName, userName).intValue();
	        if(i==1){
	        	result= "用户名绑定成功";
	        }else if (i==1){
	        	result= "用户名已绑定，无需重复绑定";
	        }
	    } catch (Exception e) {
	    	result= "用户名绑定失败，未知错误";
	    	return result;
	    }finally{
	    	quit();
	    }
			return result;
			*/
		return fromUserName +" 您好：绑定的登录名为："+userName;
	}
	/**
	 * 解除账号绑定
	 * @param fromUserName
	 * @param userName
	 * @return
	 */
	public String unBindAccount(String userName){
		/*
		Connection conn=null;
	    PreparedStatement ps=null;
	    ResultSet rs=null;
		conn = new DBCPConnection().getConnection();
	    int i = 0;
	    String sql=" delete from crm_stu_wechat where user_name= ?";
	    try {
	    	ps = conn.prepareStatement(sql);
	    	ps.setString(1, userName);
	        i = ps.executeUpdate();
	        if(i>0){
	        	return "用户名解除绑定成功";
	        }else{
	        	return "该用户名不存在";
	        }
	    } catch (SQLException e) {
	    	return "用户名解除绑定失败，未知错误";
	    }finally{
	    	closeConnection(conn,ps,rs);
	    }
	    */
		return "解绑成功："+userName;
	}
	/**
	 * 行程添加
	 * @param fromUserName
	 * @return
	 */
	public String addTravel(String fromUserName){
		/*
		Connection conn=null;
	    PreparedStatement ps=null;
	    ResultSet rs=null;
		String sbStr="";
		conn = new DBCPConnection().getConnection();
		String selectSql="select * from crm_stu_wechat where open_id='"+fromUserName+"'";
		if(conn==null){
			return "连接数据库失败";
		}else{
			try {
				ps=conn.prepareStatement(selectSql);
				rs=ps.executeQuery();
				if(rs.next()){
					String userName=rs.getString("user_name");  
					String selectTravelSql="select * from crm_student where user_id='"+userName+"'";
					Connection connTravel= new DBCPConnection().getConnection();
					PreparedStatement psTravel=connTravel.prepareStatement(selectTravelSql);
					ResultSet rsTravel=psTravel.executeQuery();
					if(rsTravel.next()){
						String name=rsTravel.getString("id");
						sbStr="点击添加行程"; 
						return sbStr;
					}else{
						return "您未注册，请在电脑端完成注册";
					}
				}else{
					return "用户名未绑定,请输入\"用户名绑定\"+登录用户名  如:用户名绑定fangw";
				}
			} catch (SQLException e) {
				return "查询数据库失败";
			}finally{
				closeConnection(conn,ps,rs);
			}
		}
		*/
		return fromUserName+"，您好，添加成功";
	}
	/**
	 * 行程查看
	 * @param fromUserName
	 * @return
	 */
	public String viewTravel(String fromUserName){
		/*
		Connection conn=null;
	    PreparedStatement ps=null;
	    ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		conn = new DBCPConnection().getConnection();
		String selectSql="select * from crm_stu_wechat where open_id='"+fromUserName+"'";
		if(conn==null){
			return "连接数据库失败";
		}else{
			try {
				ps=conn.prepareStatement(selectSql);
				rs=ps.executeQuery();
				if(rs.next()){
					String userName=rs.getString("user_name");  
					String selectTravelSql="select * from crm_student_info where user_id='"+userName+"'";
					Connection connTravel= new DBCPConnection().getConnection();
					PreparedStatement psTravel=connTravel.prepareStatement(selectTravelSql);
					ResultSet rsTravel=psTravel.executeQuery();
					while(rsTravel.next()){
						sb.append("考试时间：").append(rsTravel.getString("exam_time")).append("\n");
						sb.append("考试院校：").append(rsTravel.getString("exam_school")).append("\n");
						sb.append("考试地点：").append(rsTravel.getString("exam_area")).append("\n");
						sb.append("居住城市：").append(rsTravel.getString("live_city")).append("\n");
						sb.append("\n");  
						sb.append("---------------------------------");
						sb.append("\n");  
					}
					if((sb.toString()==null||("".equals(sb.toString())))){
						return "暂无您的行程";
					}else{
						return sb.toString();
					}
				}else{
					return "用户名未绑定,请输入\"用户名绑定\"+登录用户名  如:用户名绑定fangw";
				}
			} catch (SQLException e) {
				return "查询数据库失败";
			}finally{
				closeConnection(conn,ps,rs);
			}
		}
		*/
		return fromUserName+",您近期的行程是：加油上班哦";
	}
	/**
	 * 查看个人信息
	 * @return
	 */
	public String viewStuInfo(String fromUserName){
		/*
		Connection conn=null;
	    PreparedStatement ps=null;
	    ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		conn = new DBCPConnection().getConnection();
		String selectSql="select * from crm_stu_wechat where open_id='"+fromUserName+"'";
		if(conn==null){
			return "连接数据库失败";
		}else{
			try {
				ps=conn.prepareStatement(selectSql);
				rs=ps.executeQuery();
				if(rs.next()){
					String userName=rs.getString("user_name");  
					String selectStuSql="select * from crm_student where user_id='"+userName+"'";
					Connection connStu= new DBCPConnection().getConnection();
					PreparedStatement psStu=connStu.prepareStatement(selectStuSql);
					ResultSet rsStu=psStu.executeQuery();
					if(rsStu.next()){
						sb.append("用户名:").append(rsStu.getString("user_id")).append("\t").append("\n");
						sb.append("姓名:").append(rsStu.getString("stu_name")).append("\t").append("\n");
						if("0".equals(rsStu.getString("stu_sex"))){
							sb.append("性别:").append("男").append("\t").append("\n");
						}else{
							sb.append("性别:").append("女").append("\t").append("\n");
						}
						sb.append("电话:").append(rsStu.getString("tel")).append("\t").append("\n");
						sb.append("生源地:").append(rsStu.getString("origin_area")).append("\t").append("\n");
						sb.append("父母电话:").append(rsStu.getString("parent_tel")).append("\t").append("\n");
						sb.append("身份证号:").append(rsStu.getString("idno")).append("\t").append("\n");
						sb.append("准考证号:").append(rsStu.getString("ticket_num")).append("\t").append("\n");
						if("1".equals(rsStu.getString("is_company"))){
							sb.append("是否父母陪同:").append("是").append("\n");
						}else{
							sb.append("是否父母陪同:").append("否").append("\n");
						}
						return sb.toString();
					}else{
						return "您未注册，请在电脑端完成注册";
					}
				}else{
					return "用户名未绑定,请输入\"用户名绑定\"+登录用户名  如:用户名绑定fangw";
				}
			} catch (SQLException e) {
				return "查询数据库失败";
			}finally{
				closeConnection(conn,ps,rs);
			}
		}
		*/
		return "";
	}
	/**
	 * 修改学生信息页面
	 * @param fromUserName
	 * @return
	 */
	public String editStuInfo(String fromUserName){
		/*
		Connection conn=null;
	    PreparedStatement ps=null;
	    ResultSet rs=null;
		String sbStr="";
		conn = new DBCPConnection().getConnection();
		String selectSql="select * from crm_stu_wechat where open_id='"+fromUserName+"'";
		if(conn==null){
			return "连接数据库失败";
		}else{
			try {
				ps=conn.prepareStatement(selectSql);
				rs=ps.executeQuery();
				if(rs.next()){
					String userName=rs.getString("user_name");  
					String selectStuSql="select * from crm_student where user_id='"+userName+"'";
					Connection connStu= new DBCPConnection().getConnection();
					PreparedStatement psStu=connStu.prepareStatement(selectStuSql);
					ResultSet rsStu=psStu.executeQuery();
					if(rsStu.next()){
						String name=rsStu.getString("id");
						sbStr="点击修改个人信息";  
						return sbStr;
					}else{
						return "您未注册，请在电脑端完成注册";
					}
				}else{
					return "用户名未绑定,请输入\"用户名绑定\"+登录用户名  如:用户名绑定fangw";
				}
			} catch (SQLException e) {
				return "查询数据库失败";
			}finally{
				closeConnection(conn,ps,rs);
			}
		}
		*/
		return "";
		
	}
	/**
	 * 修改行程
	 * @param fromUserName
	 * @return
	 */
	public String editTravel(String fromUserName){
		/*
		Connection conn=null;
	    PreparedStatement ps=null;
	    ResultSet rs=null;
		String sbStr="";
		conn = new DBCPConnection().getConnection();
		String selectSql="select * from crm_stu_wechat where open_id='"+fromUserName+"'";
		if(conn==null){
			return "连接数据库失败";
		}else{
			try {
				ps=conn.prepareStatement(selectSql);
				rs=ps.executeQuery();
				if(rs.next()){
					String userName=rs.getString("user_name");  
					String selectStuSql="select * from crm_student where user_id='"+userName+"'";
					Connection connStu= new DBCPConnection().getConnection();
					PreparedStatement psStu=connStu.prepareStatement(selectStuSql);
					ResultSet rsStu=psStu.executeQuery();
					if(rsStu.next()){
						String selectTravelSql="select * from crm_student_info where user_id='"+userName+"'";
						Connection connTravel= new DBCPConnection().getConnection();
						PreparedStatement psTravel=connTravel.prepareStatement(selectTravelSql);
						ResultSet rsTravel=psTravel.executeQuery();
						String name=rsStu.getString("id");
						if(!rsTravel.next()){
							sbStr="点击新增行程";  
							return sbStr;
						}else{
							sbStr="点击修改行程";  
							return sbStr;
						}
					}else{
						return "您未注册，请在电脑端完成注册";
					}
					
				}else{
					return "用户名未绑定,请输入\"用户名绑定\"+登录用户名  如:用户名绑定fangw";
				}
			} catch (SQLException e) {
				return "查询数据库失败";
			}finally{
				closeConnection(conn,ps,rs);
			}
		}
		*/
		return fromUserName+"，您好，修改成功";
	}
	public void closeConnection(Connection conn,PreparedStatement ps,ResultSet rs){
		if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                rs=null;
            }
        }
        
        if(ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                ps=null;
            }
        }
        
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                conn=null;
            }
        }
	}
	public String getUserInfo(String UserOpenID){
		
		return WeixinUtil.getUserInfo(UserOpenID,UserOpenID);
	}
	public String ShowKeys(String key)
	{
		if(key==null||key=="")
			key="*";
		String result="";
		JedisUtil jedis = new JedisUtil();
		jedis.open();
		try {
			result = jedis.ShowKeys(key);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	public String ZhinengHuida(String question)
	{
		String result="";
		String host = "http://jisuznwd.market.alicloudapi.com";
	    String path = "/iqa/query";
	    String method = "GET";
	    String appcode = "bc4a71f042d04617bf36fdf245b7fd34";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("question", question);


	    try {
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	result = EntityUtils.toString(response.getEntity());
	    	//System.out.println(result);
	    	//获取response的body
	    	//System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return result;
	}
	public String Guishudi(String phoneNum)
	{
		String result="";
		String host = "http://showphone.market.alicloudapi.com";
	    String path = "/6-1";
	    String method = "GET";
	    String appcode = "bc4a71f042d04617bf36fdf245b7fd34";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("num", phoneNum);
	    try {
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	//System.out.println("哈哈:"+response.toString());
	    	result = EntityUtils.toString(response.getEntity());
	    	//获取response的body
	    	//System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return result;
	}

	public String Xinwentoutiao(String query)
	{
		String result="";
		String host = "http://toutiao-ali.juheapi.com";
	    String path = "/toutiao/index";
	    String method = "GET";
	    String appcode = "bc4a71f042d04617bf36fdf245b7fd34";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("type", query);
	    try {
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	//System.out.println("哈哈:"+response.toString());
	    	result = EntityUtils.toString(response.getEntity());
	    	//获取response的body
	    	//System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return result;
	}
}
