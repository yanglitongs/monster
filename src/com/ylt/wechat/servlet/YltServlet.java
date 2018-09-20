package com.ylt.wechat.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ylt.wechat.service.YltService;
import com.ylt.wechat.utils.SignUtil;
//import com.sun.org.apache.regexp.internal.RE;




public class YltServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public YltServlet() {
		super();
	}


	public void destroy() {
		super.destroy(); 
		// Put your code here
	}
	/**
	 * ȷ������������΢�ŷ�����
	 */

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			//΢�ż���ǩ��
			String signature = request.getParameter("signature");
			//ʱ���
			String timestamp = request.getParameter("timestamp");
			//�����
			String nonce = request.getParameter("nonce");
			//����ַ���
			String echostr = request.getParameter("echostr");
			PrintWriter out = response.getWriter();
			boolean b=SignUtil.checkSignature(signature,timestamp,nonce);
			//ͨ��У��signature���������У�飬��У��ɹ���ԭ������echostr����ʾ����ɹ����������ʧ��
			if(b){
				out.print(echostr);
				System.out.println("����ɹ�");
			}
			else
			{
				System.out.println("����ʧ��");
			}
			out.close();
			out = null;
	}
	/**
	 * ����΢�ŷ�������������Ϣ
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//��Ϣ�Ľ��ܡ�������Ӧ
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//���ú���ҵ�����ͽ�����Ϣ��������Ϣ
		String respMessage = YltService.processRequest(request);
		System.out.println("doPost��"+respMessage);
		//��Ӧ��Ϣ
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.close();
		
		
	}

	public void init() throws ServletException {
		// Put your code here
	}
	
}
