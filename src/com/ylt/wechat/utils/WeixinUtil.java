package com.ylt.wechat.utils;
import java.io.BufferedReader;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStream;  
import java.net.ConnectException;  
import java.net.URL;  
  
import javax.net.ssl.HttpsURLConnection;  
import javax.net.ssl.SSLContext;  
import javax.net.ssl.SSLSocketFactory;  
import javax.net.ssl.TrustManager;  

import com.ylt.wechat.pojo.AccessToken;
import com.ylt.wechat.pojo.Menu;
  
import net.sf.json.JSONObject;   
public class WeixinUtil {
//	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);  
	// 获取access_token的接口地址（GET） 限200（次/天）  
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";  
	// 菜单创建（POST） 限100（次/天）  
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	public final static String UserInfo_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
	
    /** 
     * 发起https请求并获取结果 
     *  
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param outputStr 提交的数据 
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
     */  
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {  
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try {  
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
            TrustManager[] tm = { new MyX509TrustManager() };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
            // 从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();  
  
            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
            httpUrlConn.setSSLSocketFactory(ssf);  
  
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod(requestMethod);  
  
            if ("GET".equalsIgnoreCase(requestMethod))  
                httpUrlConn.connect();  
  
            // 当有数据需要提交时  
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
            System.out.println(buffer.toString());
            jsonObject = JSONObject.fromObject(buffer.toString());  
        } catch (ConnectException ce) {  
            System.out.println("Weixin server connection timed out.");
        } catch (Exception e) {  
        	System.err.println("https request error:{}");
//            log.error("https request error:{}", e);  
        }  
        return jsonObject;  
    }  
    /** 
	 * 获取access_token 
	 *  
	 * @param appid 凭证 
	 * @param appsecret 密钥 
	 * @return 
	 */  
	public static AccessToken getAccessToken(String appid, String appsecret) {  
	    AccessToken accessToken = null;  
	  
	    String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
	    System.out.println("获取accessToken："+requestUrl);
	    
	    JSONObject jsonObject = httpRequest(requestUrl, "GET", null);  
	    // 如果请求成功  
	    if (null != jsonObject) {  
	        try {  
	            accessToken = new AccessToken();  
	            accessToken.setToken(jsonObject.getString("access_token"));  
	            accessToken.setExpiresIn(jsonObject.getInt("expires_in"));  
	        } catch (Exception e) {  
	            accessToken = null;  
	            // 获取token失败  
	            System.out.println("获取token失败 errcode:"+jsonObject.getInt("errcode")+"errmsg:"+jsonObject.getString("errmsg"));
//	            log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
	        }  
	    }  
	    return accessToken;  
	}
	
	/** 
	 * 获取access_token 
	 *  
	 * @param appid 凭证 
	 * @param appsecret 密钥 
	 * @return ACCESS_TOKEN&openid=OPENID
	 */  
	public static String getUserInfo(String AccessToken, String OpenId) {  
	    String UserInfo = "";  
	  
	    String requestUrl = access_token_url.replace("ACCESS_TOKEN", AccessToken).replace("OPENID", OpenId);
	    //System.out.println("获取accessToken："+requestUrl);
	    
	    JSONObject jsonObject = httpRequest(requestUrl, "GET", null);  
	    // 如果请求成功  
	    if (null != jsonObject) {  
	        try { 
	        	UserInfo+="OpenId："+jsonObject.getString("openid")+"\n";
	        	UserInfo+="用户名称："+jsonObject.getString("nickname")+"\n";
	        	UserInfo+="性别："+jsonObject.getString("sex")+"\n";
	        	UserInfo+="语言："+jsonObject.getString("language")+"\n";
	        	UserInfo+="所在城市："+jsonObject.getString("country")+" "+jsonObject.getString("province")+" "+jsonObject.getString("city")+"\n";
	        	UserInfo+="头像地址："+jsonObject.getString("headimgurl")+"\n";
	        	UserInfo+="subscribe_time："+jsonObject.getString("subscribe_time")+"\n";  
	        } catch (Exception e) {  
	            // 获取token失败  
	            System.out.println("获取token失败 errcode:"+jsonObject.getInt("errcode")+"errmsg:"+jsonObject.getString("errmsg"));
//	            log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
	        }  
	    }  
	    return UserInfo;  
	}
	
	
	/** 
	 * 创建菜单 
	 *  
	 * @param menu 菜单实例 
	 * @param accessToken 有效的access_token 
	 * @return 0表示成功，其他值表示失败 
	 */  
	public static int createMenu(Menu menu, String accessToken) {  
	    int result = 0;  
	  
	    // 拼装创建菜单的url  
	    String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
	    System.out.println("创建自定义菜单url:"+url);
	    // 将菜单对象转换成json字符串  
	    String jsonMenu = JSONObject.fromObject(menu).toString();  
	    // 调用接口创建菜单  
	    JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);
	    System.out.println("接口创建菜单结果"+jsonObject);
	  
	    if (null != jsonObject) {  
	        if (0 != jsonObject.getInt("errcode")) {  
	            result = jsonObject.getInt("errcode"); 
	            System.out.println("创建菜单失败errcode:"+jsonObject.getInt("errcode")+"errmsg:"+jsonObject.getString("errmsg"));
//	            log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
	        }  
	    }  
	  
	    return result;  
	} 
}
