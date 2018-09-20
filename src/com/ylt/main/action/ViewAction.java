package com.ylt.main.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ylt.main.pojo.Company;
import com.ylt.main.pojo.HangYe;
import com.ylt.main.pojo.Person;
import com.ylt.main.pojo.WxData;
import com.ylt.main.pojo.XingZhi;
import com.ylt.main.service.UserServiceI;
import com.ylt.wechat.utils.JsonUtil;
import com.ylt.wechat.utils.OperatorUtil;
import com.ylt.wechat.utils.QRCodeUtil;
import com.ylt.wechat.utils.RedisUtil;

@Controller
public class ViewAction {
	@Autowired
	private UserServiceI userService;
	
	@RequestMapping("/selectByFromUserName.action")
	public String selectByFromUserName(HttpServletRequest request,HttpServletResponse response,WxData wxdata) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		if(wxdata.getFromusername()==null){
			wxdata.setFromusername("");
		}
		WxData list=userService.selectByFromUserName(wxdata.getFromusername());
		request.setAttribute("selectByFromUserName", list);		
		return "wxdata";
	}
	@RequestMapping("/insertData.action")
	public String insertData(HttpServletRequest request,HttpServletResponse response,WxData wxdata) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		int count = userService.insertData(wxdata);
		request.setAttribute("count", count);		
		return "wxdata";
	}

	@RequestMapping("/updateData.action")
	public String updateData(HttpServletRequest request,HttpServletResponse response,WxData wxdata) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		int count = userService.updateData(wxdata);
		request.setAttribute("count", count);		
		return "wxdata";
	}
	
	@RequestMapping("/GetAllData.action")
	public String GetAllData(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		List<WxData> list = userService.GetAllData();
		request.setAttribute("GetAllData", list);
		
		return "wxdata";
	}
	
	@RequestMapping("/list.action")
	public String list(HttpServletRequest request,HttpServletResponse response,Company company) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		if(company.getCname()==null){
			company.setCname("");
		}
		List<Company> list=userService.getUserList(company);
		for (Company company2 : list) {
			Integer ccid = company2.getCcid();
			int count=userService.getCount(ccid);
			company2.setCount(count);
		}
		List<HangYe> list2=userService.getHangYe();
		List<XingZhi> xingzhiList=userService.getXingZhi();
		request.setAttribute("hangye", list2);
		request.setAttribute("userList", list);
		request.setAttribute("xingzhi", xingzhiList);
		return "list";
	}
	@RequestMapping("/toUpdate.action")
	public void toUpdate(HttpServletRequest request,HttpServletResponse response,Company company) throws IOException{
		response.setCharacterEncoding("UTF-8");	
		Company company2=userService.toUpdate(company);
			JSONObject jsonObject = JSONObject.fromObject(company2);
			response.getWriter().write(jsonObject.toString());
	}
	@RequestMapping("/add.action")
	public String add(HttpServletRequest request,HttpServletResponse response,Company company) throws UnsupportedEncodingException{
			userService.add(company);
			company.setCname("");
			List<Company> list=userService.getUserList(company);
			request.setAttribute("userList", list);
			return "list";
	}
	
	@RequestMapping("/delete.action")
	public void delete(HttpServletRequest request,HttpServletResponse response,Company company){
		userService.delete(company);
	}
	@RequestMapping("/xq.action")
	public String xq(HttpServletRequest request,HttpServletResponse response,Company company){
		if(company.getPname()==null){
			company.setPname("");
		}
		List<Person> list=userService.xq(company);
		request.setAttribute("personList", list);
		return "xq";
	}
	@RequestMapping("/update.action")
	public String update(HttpServletRequest request,HttpServletResponse response,Company company) throws UnsupportedEncodingException{
		userService.update(company);
		company.setCname("");
		List<Company> list=userService.getUserList(company);
		for (Company company2 : list) {
			Integer ccid = company2.getCcid();
			int count=userService.getCount(ccid);
			company2.setCount(count);
		}
		List<HangYe> list2=userService.getHangYe();
		List<XingZhi> xingzhiList=userService.getXingZhi();
		request.setAttribute("hangye", list2);
		request.setAttribute("userList", list);
		request.setAttribute("xingzhi", xingzhiList);

		return "list";
	}
	@RequestMapping("/addPerson.action")
	public String addPerson(HttpServletRequest request,HttpServletResponse response,Person person){
		userService.addPerson(person);
		Company company = new Company();
		company.setCcid(person.getCcid());
		if(company.getPname()==null){
			company.setPname("");
		}
		List<Person> list=userService.xq(company);
		request.setAttribute("personList", list);
		return "xq";
	}
	@RequestMapping("/toUpdatePerson.action")
	public void toUpdatePerson(HttpServletRequest request,HttpServletResponse response,Person person) throws IOException{
		response.setCharacterEncoding("UTF-8");
		Person person2=userService.getPerson(person);
		JSONObject fromObject = JSONObject.fromObject(person2);
		response.getWriter().write(fromObject.toString());
	}
	@RequestMapping("/updatePerson.action")
	public String updatePerson(HttpServletRequest request,HttpServletResponse response,Person person){
		userService.updatePerson(person);
		Company company = new Company();
		company.setCcid(person.getCcid());
		if(company.getPname()==null){
			company.setPname("");
		}
		List<Person> list=userService.xq(company);
		request.setAttribute("personList", list);
		return "xq";
	}
	
	@RequestMapping("/del.action")
	public void del(HttpServletRequest request,HttpServletResponse response,Person person){
		userService.del(person);
		
	}
	@RequestMapping("/MailSend.action")
	public String MailSend(HttpServletRequest request,HttpServletResponse response, String massage)
	{
		return userService.MailSend(massage);
	}
	
	
}
