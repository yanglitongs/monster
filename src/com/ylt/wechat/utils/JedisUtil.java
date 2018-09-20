package com.ylt.wechat.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

public class JedisUtil {
	public JedisUtil()
	{
		open();
	}
	public static Jedis jedis = null;

	public synchronized void open() {
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
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void quit() {
		jedis.quit();
		jedis = null;
	}

	public String ShowKeys(String key) {
		String result ="";
		String skey="*";
		if(key!=null||key!="")
			skey=key;
		 Set keys = jedis.keys(key);//列出所有的key，查找特定的key如：redis.keys("foo")  
         Iterator t1=keys.iterator() ;  
         while(t1.hasNext()){  
             Object obj1=t1.next(); 
             result+=obj1.toString()+";";
             System.out.println(key+"的结果："+result);  
         }
         return result;
	}
	public String returnkey(String key) {
		String result ="";
		result += jedis.ttl(key).toString();////TTL 返回给定key的剩余生存时间(time to live)(以秒为单位)
		return result;
	}
	public boolean SetStringValue(String key,String value)
	{
		jedis.setnx(key, value);
		return true;
	}
	public boolean keyexists(String key)
	{
		return jedis.exists(key);
	}
	public String DeleteKey(String key)
	{
		if(key==null||key=="")
			return "No key";
		String result ="";
		try{
			jedis.del(key);
			result="OK";
		}catch(Exception e)
		{
			result="NO";
		}
		return result;
	}
}
