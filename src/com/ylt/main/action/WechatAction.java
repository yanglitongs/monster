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
	 * @Description 用于用户在微信端连接服务器 
	 * @param request
	 * @param response
	 * @throws Exception       
	 * @author 
	 * @Date 2016年10月11日 下午3:53:40
	 */
	@ResponseBody()
	@RequestMapping(value="/accessWechat",method=RequestMethod.GET)
	public void accessWechat(HttpServletRequest request,HttpServletResponse response)throws Exception{
		System.out.println("接口测试开始！！！");
		//微信加密签名
		String signature = request.getParameter("signature");
		//时间戳
		String timestamp = request.getParameter("timestamp");
		//随机数
		String nonce = request.getParameter("nonce");
		//随机字符串
		String echostr = request.getParameter("echostr");
		PrintWriter out = response.getWriter();
		boolean b=SignUtil.checkSignature(signature,timestamp,nonce);
		//通过校验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if(b){
			out.print(echostr);
			System.out.println("接入成功");
		}
		else
		{
			System.out.println("接入失败");
		}
		out.close();
		out = null;
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
	@RequestMapping(value="/accessWechat",method=RequestMethod.POST)
	public void requestRrocessing(HttpServletRequest request,HttpServletResponse response)throws Exception{
		//消息的接受、处理、响应
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		System.out.println("处理消息开始！");
		//调用核心业务类型接受消息、处理消息
		String respMessage = YltService.processRequest(request);
		System.out.println("requestRrocessing："+respMessage);
		//响应消息
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.close();
	}
}
