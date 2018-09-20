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
		// ������Ϣ���ӿ���̨���Ի��
		String host = "r-2ze11122a9243ae4.redis.rds.aliyuncs.com";
		int port = 6379;
		if (jedis == null) {
			jedis = new Jedis(host, port);
			try {
				// ʵ������
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
     * ��ȡ�˺Ű�״̬
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
            System.out.println("���ID��"+item.getElement()+"�� ��ҵ÷�:"+Double.valueOf(item.getScore()).intValue());
        }  
		Connection conn=null;
	    PreparedStatement ps=null;
	    ResultSet rs=null;
		conn=new DBCPConnection().getConnection();
		String selectSql="select * from crm_stu_wechat where open_id='"+fromUserName+"'";
		if(conn==null){
			return "�������ݿ�ʧ��";
		}else{
			try {
				ps=conn.prepareStatement(selectSql);
				rs=ps.executeQuery();
				if(rs.next()){
					return "�û����Ѱ�";
				}else{
					return "������\"�û�����\"+��¼�û���  ��:�û�����fangw";
				}
			} catch (SQLException e) {
				return "��ѯ���ݿ�ʧ��";
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
	 * ���˺�
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
			return result = "�������ݿ�ʧ��";
		}
		int i = jedis.hset("user", fromUserName, userName).intValue();
	        if(i==1){
	        	result= "�û����󶨳ɹ�";
	        }else if (i==1){
	        	result= "�û����Ѱ󶨣������ظ���";
	        }
	    } catch (Exception e) {
	    	result= "�û�����ʧ�ܣ�δ֪����";
	    	return result;
	    }finally{
	    	quit();
	    }
			return result;
			*/
		return fromUserName +" ���ã��󶨵ĵ�¼��Ϊ��"+userName;
	}
	/**
	 * ����˺Ű�
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
	        	return "�û�������󶨳ɹ�";
	        }else{
	        	return "���û���������";
	        }
	    } catch (SQLException e) {
	    	return "�û��������ʧ�ܣ�δ֪����";
	    }finally{
	    	closeConnection(conn,ps,rs);
	    }
	    */
		return "���ɹ���"+userName;
	}
	/**
	 * �г����
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
			return "�������ݿ�ʧ��";
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
						sbStr="�������г�"; 
						return sbStr;
					}else{
						return "��δע�ᣬ���ڵ��Զ����ע��";
					}
				}else{
					return "�û���δ��,������\"�û�����\"+��¼�û���  ��:�û�����fangw";
				}
			} catch (SQLException e) {
				return "��ѯ���ݿ�ʧ��";
			}finally{
				closeConnection(conn,ps,rs);
			}
		}
		*/
		return fromUserName+"�����ã���ӳɹ�";
	}
	/**
	 * �г̲鿴
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
			return "�������ݿ�ʧ��";
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
						sb.append("����ʱ�䣺").append(rsTravel.getString("exam_time")).append("\n");
						sb.append("����ԺУ��").append(rsTravel.getString("exam_school")).append("\n");
						sb.append("���Եص㣺").append(rsTravel.getString("exam_area")).append("\n");
						sb.append("��ס���У�").append(rsTravel.getString("live_city")).append("\n");
						sb.append("\n");  
						sb.append("---------------------------------");
						sb.append("\n");  
					}
					if((sb.toString()==null||("".equals(sb.toString())))){
						return "���������г�";
					}else{
						return sb.toString();
					}
				}else{
					return "�û���δ��,������\"�û�����\"+��¼�û���  ��:�û�����fangw";
				}
			} catch (SQLException e) {
				return "��ѯ���ݿ�ʧ��";
			}finally{
				closeConnection(conn,ps,rs);
			}
		}
		*/
		return fromUserName+",�����ڵ��г��ǣ������ϰ�Ŷ";
	}
	/**
	 * �鿴������Ϣ
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
			return "�������ݿ�ʧ��";
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
						sb.append("�û���:").append(rsStu.getString("user_id")).append("\t").append("\n");
						sb.append("����:").append(rsStu.getString("stu_name")).append("\t").append("\n");
						if("0".equals(rsStu.getString("stu_sex"))){
							sb.append("�Ա�:").append("��").append("\t").append("\n");
						}else{
							sb.append("�Ա�:").append("Ů").append("\t").append("\n");
						}
						sb.append("�绰:").append(rsStu.getString("tel")).append("\t").append("\n");
						sb.append("��Դ��:").append(rsStu.getString("origin_area")).append("\t").append("\n");
						sb.append("��ĸ�绰:").append(rsStu.getString("parent_tel")).append("\t").append("\n");
						sb.append("���֤��:").append(rsStu.getString("idno")).append("\t").append("\n");
						sb.append("׼��֤��:").append(rsStu.getString("ticket_num")).append("\t").append("\n");
						if("1".equals(rsStu.getString("is_company"))){
							sb.append("�Ƿ�ĸ��ͬ:").append("��").append("\n");
						}else{
							sb.append("�Ƿ�ĸ��ͬ:").append("��").append("\n");
						}
						return sb.toString();
					}else{
						return "��δע�ᣬ���ڵ��Զ����ע��";
					}
				}else{
					return "�û���δ��,������\"�û�����\"+��¼�û���  ��:�û�����fangw";
				}
			} catch (SQLException e) {
				return "��ѯ���ݿ�ʧ��";
			}finally{
				closeConnection(conn,ps,rs);
			}
		}
		*/
		return "";
	}
	/**
	 * �޸�ѧ����Ϣҳ��
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
			return "�������ݿ�ʧ��";
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
						sbStr="����޸ĸ�����Ϣ";  
						return sbStr;
					}else{
						return "��δע�ᣬ���ڵ��Զ����ע��";
					}
				}else{
					return "�û���δ��,������\"�û�����\"+��¼�û���  ��:�û�����fangw";
				}
			} catch (SQLException e) {
				return "��ѯ���ݿ�ʧ��";
			}finally{
				closeConnection(conn,ps,rs);
			}
		}
		*/
		return "";
		
	}
	/**
	 * �޸��г�
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
			return "�������ݿ�ʧ��";
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
							sbStr="��������г�";  
							return sbStr;
						}else{
							sbStr="����޸��г�";  
							return sbStr;
						}
					}else{
						return "��δע�ᣬ���ڵ��Զ����ע��";
					}
					
				}else{
					return "�û���δ��,������\"�û�����\"+��¼�û���  ��:�û�����fangw";
				}
			} catch (SQLException e) {
				return "��ѯ���ݿ�ʧ��";
			}finally{
				closeConnection(conn,ps,rs);
			}
		}
		*/
		return fromUserName+"�����ã��޸ĳɹ�";
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
	    //�����header�еĸ�ʽ(�м���Ӣ�Ŀո�)ΪAuthorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("question", question);


	    try {
	    	/**
	    	* ��Ҫ��ʾ����:
	    	* HttpUtils���
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* ����
	    	*
	    	* ��Ӧ�����������
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	result = EntityUtils.toString(response.getEntity());
	    	//System.out.println(result);
	    	//��ȡresponse��body
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
	    //�����header�еĸ�ʽ(�м���Ӣ�Ŀո�)ΪAuthorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("num", phoneNum);
	    try {
	    	/**
	    	* ��Ҫ��ʾ����:
	    	* HttpUtils���
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* ����
	    	*
	    	* ��Ӧ�����������
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	//System.out.println("����:"+response.toString());
	    	result = EntityUtils.toString(response.getEntity());
	    	//��ȡresponse��body
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
	    //�����header�еĸ�ʽ(�м���Ӣ�Ŀո�)ΪAuthorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("type", query);
	    try {
	    	/**
	    	* ��Ҫ��ʾ����:
	    	* HttpUtils���
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* ����
	    	*
	    	* ��Ӧ�����������
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	//System.out.println("����:"+response.toString());
	    	result = EntityUtils.toString(response.getEntity());
	    	//��ȡresponse��body
	    	//System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return result;
	}
}
