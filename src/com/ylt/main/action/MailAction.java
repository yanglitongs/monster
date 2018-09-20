package com.ylt.main.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylt.wechat.service.YltService;
import com.ylt.common.MailUtils;

@Controller
public class MailAction {

	/**
	 * 
	 * @Title accessWechat
	 * @Description 用于用户在微信端连接服务器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author
	 * @Date 2016年10月11日 下午3:53:40
	 */
	@ResponseBody()
	@RequestMapping(value = "/MailSend", method = RequestMethod.GET)
	public void accessWechat(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String message = request.getParameter("message");
		StringBuffer str = new StringBuffer();
		str.append("DDNS:http://yanglitong.asuscomm.com");
		str.append("\r\n");
		str.append("获取实体IP：http://iplookup.asus.com/nslookup.php");
		str.append("\r\n");
		str.append("asus:http://" + message+":8086");
		str.append("\r\n");
		str.append("ac1900p:http://"+message+":8188");
		str.append("\r\n");
		str.append("ausssh:"+message+":23");
		str.append("\r\n");
		str.append("ac1900pssh:"+message+":22");
		str.append("\r\n");
		str.append("mysql:"+message+":3306");
		str.append("\r\n");
		str.append("vnc:"+message+":5900");
		str.append("\r\n");
		str.append("udp:"+message+":7");
		str.append("\r\n");
		str.append("win10rdp:"+message+":3389");
		str.append("\r\n");
		str.append("windows server 2008 rdp 103:"+message+":3391");
		str.append("\r\n");
		str.append("windows server 2012 rdp 104:"+message+":3395");
		str.append("\r\n");
		str.append("windows 7 rdp 102:"+message+":3392");
		str.append("\r\n");
		str.append("windows 10 rdp 108:"+message+":3393");
		str.append("\r\n");
		str.append("windows server 2016 rdp 109:"+message+":3394");
		str.append("\r\n");
		str.append("centos server 64 ssh 106:"+message+":3396");
		str.append("\r\n");
		str.append("ftp:"+message+":8083");
		str.append("\r\n");
		try {
			MailUtils cn = new MailUtils();
			// 设置发件人地址、收件人地址和邮件标题
			cn.setAddress("weilailingxian@126.com", "ylt_0420@163.com", "地址一览");
			// 设置要发送附件的位置和标题
			// cn.setAffix("e:/123.txt", "123.txt");
			cn.setContent(str.toString());
			// String s="NO";
			/*
			 * 设置smtp服务器以及邮箱的帐号和密码 用QQ 邮箱作为发生者不好使 （原因不明） 163 邮箱可以，但是必须开启 POP3/SMTP服务 和
			 * IMAP/SMTP服务 因为程序属于第三方登录，所以登录密码必须使用163的授权码
			 */
			cn.send("smtp.126.com", "weilailingxian@126.com", "Yanglt1990");
			// s = "OK";
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		PrintWriter out = response.getWriter();
		out.print(str);
		out.close();
	}

	/**
	 * @Title requestRrocessing
	 * @Description 微信消息接入接口
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author
	 * @Date
	 */
	@ResponseBody()
	@RequestMapping(value = "/MailSend", method = RequestMethod.POST)
	public void requestRrocessing(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 消息的接受、处理、响应
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//String respMessage = YltService.processRequest(request);
		//System.out.println("requestRrocessing：" + respMessage);
		// 响应消息
		PrintWriter out = response.getWriter();
		out.print("");
		out.close();
	}

}
