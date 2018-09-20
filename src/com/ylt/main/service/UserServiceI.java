package com.ylt.main.service;

import java.util.List;

import com.ylt.main.pojo.Company;
import com.ylt.main.pojo.HangYe;
import com.ylt.main.pojo.Person;
import com.ylt.main.pojo.WxData;
import com.ylt.main.pojo.XingZhi;

public interface UserServiceI {

	List<Company> getUserList(Company company);

	List<HangYe> getHangYe();

	void add(Company company);

	void delete(Company company);

	List<Person> xq(Company company);

	int getCount(Integer ccid);

	Company toUpdate(Company company);

	List<XingZhi> getXingZhi();

	void update(Company company);

	void addPerson(Person person);

	Person getPerson(Person person);

	void updatePerson(Person person);

	void del(Person person);

	WxData selectByFromUserName(String FromUserName);
    
    int insertData(WxData record);
    
    int updateData(WxData record);
    
    List<WxData> GetAllData();
    
    String MailSend(String massage);

}
