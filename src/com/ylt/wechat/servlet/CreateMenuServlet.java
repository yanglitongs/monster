package com.ylt.wechat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ylt.wechat.pojo.AccessToken;
import com.ylt.wechat.pojo.Button;
import com.ylt.wechat.pojo.CommonButton;
import com.ylt.wechat.pojo.ComplexButton;
import com.ylt.wechat.pojo.Menu;
import com.ylt.wechat.utils.WeixinUtil;

public class CreateMenuServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor of the object.
	 */
	public CreateMenuServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 绗笁鏂圭敤鎴峰敮涓�鍑瘉  
        String appId = "wxc29c8a276474475b";  
        // 绗笁鏂圭敤鎴峰敮涓�鍑瘉瀵嗛挜  
        String appSecret = "488a440203d5e52fbfef969ac573849a";  
  
        // 璋冪敤鎺ュ彛鑾峰彇access_token  
        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);
        if (null != at) {  
            // 璋冪敤鎺ュ彛鍒涘缓鑿滃崟  
            int result = WeixinUtil.createMenu(getMenu(), at.getToken());  
  
            // 鍒ゆ柇鑿滃崟鍒涘缓缁撴灉  
            if (0 == result){
            	response.setContentType("text/html;charset=UTF-8");  
            	PrintWriter pw = response.getWriter();  
	            pw.println("鑿滃崟鍒涘缓鎴愬姛锛�");  
	            pw.flush();     
            }else{
            	response.setContentType("text/html;charset=UTF-8");  
            	PrintWriter pw = response.getWriter();  
	            pw.println("鑿滃崟鍒涘缓澶辫触锛岄敊璇爜锛�" + result);  
	            pw.flush();     
            }   
        }
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	/** 
     * 缁勮鑿滃崟鏁版嵁 
     *  
     * @return 
     */  
    private static Menu getMenu() {  
        CommonButton btn11 = new CommonButton();  
        btn11.setName("涓汉淇℃伅鏌ョ湅");  
        btn11.setType("click");  
        btn11.setKey("stuInfoView");  
  
        CommonButton btn12 = new CommonButton();  
        btn12.setName("涓汉淇℃伅淇敼");  
        btn12.setType("click");  
        btn12.setKey("stuInfoEdit"); 
  
        CommonButton btn21 = new CommonButton();  
        btn21.setName("琛岀▼鏌ョ湅");  
        btn21.setType("click");  
        btn21.setKey("stuTravelView");  
  
        CommonButton btn22 = new CommonButton();  
        btn22.setName("琛岀▼娣诲姞");  
        btn22.setType("click");  
        btn22.setKey("stuTravelAdd");  
  
        CommonButton btn23 = new CommonButton();  
        btn23.setName("琛岀▼淇敼");  
        btn23.setType("click");  
        btn23.setKey("stuTravelEdit");  
  
        CommonButton btn31 = new CommonButton();  
        btn31.setName("鎿嶄綔璇存槑");  
        btn31.setType("click");  
        btn31.setKey("help");  
  
        CommonButton btn32 = new CommonButton();  
        btn32.setName("鍛煎彨绠＄悊鍛�");  
        btn32.setType("click");  
        btn32.setKey("callAdmin");  
  
        CommonButton btn33 = new CommonButton();  
        btn33.setName("鎰忚鍙嶉");  
        btn33.setType("click");  
        btn33.setKey("suggestions");  
  
        ComplexButton mainBtn1 = new ComplexButton();  
        mainBtn1.setName("涓汉淇℃伅");  
        mainBtn1.setSub_button(new Button[] { btn11, btn12});  
  
        ComplexButton mainBtn2 = new ComplexButton();  
        mainBtn2.setName("琛岀▼");  
        mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23});  
  
        ComplexButton mainBtn3 = new ComplexButton();  
        mainBtn3.setName("甯姪");  
        mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33 });  
  
        /** 
         * 杩欐槸鍏紬鍙穢iaoqrobot鐩墠鐨勮彍鍗曠粨鏋勶紝姣忎釜涓�绾ц彍鍗曢兘鏈変簩绾ц彍鍗曢」<br> 
         *  
         * 鍦ㄦ煇涓竴绾ц彍鍗曚笅娌℃湁浜岀骇鑿滃崟鐨勬儏鍐碉紝menu璇ュ浣曞畾涔夊憿锛�<br> 
         * 姣斿锛岀涓変釜涓�绾ц彍鍗曢」涓嶆槸鈥滄洿澶氫綋楠屸�濓紝鑰岀洿鎺ユ槸鈥滃菇榛樼瑧璇濃�濓紝閭ｄ箞menu搴旇杩欐牱瀹氫箟锛�<br> 
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
         */  
        Menu menu = new Menu();  
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });  
  
        return menu;  
    }  
}
