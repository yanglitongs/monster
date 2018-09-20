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
	// ��ȡaccess_token�Ľӿڵ�ַ��GET�� ��200����/�죩  
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";  
	// �˵�������POST�� ��100����/�죩  
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	public final static String UserInfo_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
	
    /** 
     * ����https���󲢻�ȡ��� 
     *  
     * @param requestUrl �����ַ 
     * @param requestMethod ����ʽ��GET��POST�� 
     * @param outputStr �ύ������ 
     * @return JSONObject(ͨ��JSONObject.get(key)�ķ�ʽ��ȡjson���������ֵ) 
     */  
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {  
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try {  
            // ����SSLContext���󣬲�ʹ������ָ�������ι�������ʼ��  
            TrustManager[] tm = { new MyX509TrustManager() };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
            // ������SSLContext�����еõ�SSLSocketFactory����  
            SSLSocketFactory ssf = sslContext.getSocketFactory();  
  
            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
            httpUrlConn.setSSLSocketFactory(ssf);  
  
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // ��������ʽ��GET/POST��  
            httpUrlConn.setRequestMethod(requestMethod);  
  
            if ("GET".equalsIgnoreCase(requestMethod))  
                httpUrlConn.connect();  
  
            // ����������Ҫ�ύʱ  
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // ע������ʽ����ֹ��������  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
  
            // �����ص�������ת�����ַ���  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // �ͷ���Դ  
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
	 * ��ȡaccess_token 
	 *  
	 * @param appid ƾ֤ 
	 * @param appsecret ��Կ 
	 * @return 
	 */  
	public static AccessToken getAccessToken(String appid, String appsecret) {  
	    AccessToken accessToken = null;  
	  
	    String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
	    System.out.println("��ȡaccessToken��"+requestUrl);
	    
	    JSONObject jsonObject = httpRequest(requestUrl, "GET", null);  
	    // �������ɹ�  
	    if (null != jsonObject) {  
	        try {  
	            accessToken = new AccessToken();  
	            accessToken.setToken(jsonObject.getString("access_token"));  
	            accessToken.setExpiresIn(jsonObject.getInt("expires_in"));  
	        } catch (Exception e) {  
	            accessToken = null;  
	            // ��ȡtokenʧ��  
	            System.out.println("��ȡtokenʧ�� errcode:"+jsonObject.getInt("errcode")+"errmsg:"+jsonObject.getString("errmsg"));
//	            log.error("��ȡtokenʧ�� errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
	        }  
	    }  
	    return accessToken;  
	}
	
	/** 
	 * ��ȡaccess_token 
	 *  
	 * @param appid ƾ֤ 
	 * @param appsecret ��Կ 
	 * @return ACCESS_TOKEN&openid=OPENID
	 */  
	public static String getUserInfo(String AccessToken, String OpenId) {  
	    String UserInfo = "";  
	  
	    String requestUrl = access_token_url.replace("ACCESS_TOKEN", AccessToken).replace("OPENID", OpenId);
	    //System.out.println("��ȡaccessToken��"+requestUrl);
	    
	    JSONObject jsonObject = httpRequest(requestUrl, "GET", null);  
	    // �������ɹ�  
	    if (null != jsonObject) {  
	        try { 
	        	UserInfo+="OpenId��"+jsonObject.getString("openid")+"\n";
	        	UserInfo+="�û����ƣ�"+jsonObject.getString("nickname")+"\n";
	        	UserInfo+="�Ա�"+jsonObject.getString("sex")+"\n";
	        	UserInfo+="���ԣ�"+jsonObject.getString("language")+"\n";
	        	UserInfo+="���ڳ��У�"+jsonObject.getString("country")+" "+jsonObject.getString("province")+" "+jsonObject.getString("city")+"\n";
	        	UserInfo+="ͷ���ַ��"+jsonObject.getString("headimgurl")+"\n";
	        	UserInfo+="subscribe_time��"+jsonObject.getString("subscribe_time")+"\n";  
	        } catch (Exception e) {  
	            // ��ȡtokenʧ��  
	            System.out.println("��ȡtokenʧ�� errcode:"+jsonObject.getInt("errcode")+"errmsg:"+jsonObject.getString("errmsg"));
//	            log.error("��ȡtokenʧ�� errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
	        }  
	    }  
	    return UserInfo;  
	}
	
	
	/** 
	 * �����˵� 
	 *  
	 * @param menu �˵�ʵ�� 
	 * @param accessToken ��Ч��access_token 
	 * @return 0��ʾ�ɹ�������ֵ��ʾʧ�� 
	 */  
	public static int createMenu(Menu menu, String accessToken) {  
	    int result = 0;  
	  
	    // ƴװ�����˵���url  
	    String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
	    System.out.println("�����Զ���˵�url:"+url);
	    // ���˵�����ת����json�ַ���  
	    String jsonMenu = JSONObject.fromObject(menu).toString();  
	    // ���ýӿڴ����˵�  
	    JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);
	    System.out.println("�ӿڴ����˵����"+jsonObject);
	  
	    if (null != jsonObject) {  
	        if (0 != jsonObject.getInt("errcode")) {  
	            result = jsonObject.getInt("errcode"); 
	            System.out.println("�����˵�ʧ��errcode:"+jsonObject.getInt("errcode")+"errmsg:"+jsonObject.getString("errmsg"));
//	            log.error("�����˵�ʧ�� errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
	        }  
	    }  
	  
	    return result;  
	} 
}
