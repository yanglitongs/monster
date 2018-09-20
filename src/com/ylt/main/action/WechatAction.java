package com.ylt.main.action;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylt.main.service.UserServiceI;
import com.ylt.wechat.service.YltService;
import com.ylt.wechat.utils.SignUtil;

@Controller
public class WechatAction {
	
	/**
	 * 
	 * @Title accessWechat 
	 * @Description �����û���΢�Ŷ����ӷ����� 
	 * @param request
	 * @param response
	 * @throws Exception       
	 * @author 
	 * @Date 2016��10��11�� ����3:53:40
	 */
	@ResponseBody()
	@RequestMapping(value="/accessWechat",method=RequestMethod.GET)
	public void accessWechat(HttpServletRequest request,HttpServletResponse response)throws Exception{
		System.out.println("�ӿڲ��Կ�ʼ������");
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
	 * @Title requestRrocessing 
	 * @Description ΢����Ϣ����ӿ�
	 * @param request
	 * @param response
	 * @throws Exception       
	 * @author 
	 * @Date 
	 */
	@ResponseBody()
	@RequestMapping(value="/accessWechat",method=RequestMethod.POST)
	public void requestRrocessing(HttpServletRequest request,HttpServletResponse response)throws Exception{
		//��Ϣ�Ľ��ܡ�������Ӧ
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		System.out.println("������Ϣ��ʼ��");
		//���ú���ҵ�����ͽ�����Ϣ��������Ϣ
		String respMessage = YltService.processRequest(request);
		System.out.println("requestRrocessing��"+respMessage);
		//��Ӧ��Ϣ
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.close();
	}
}
