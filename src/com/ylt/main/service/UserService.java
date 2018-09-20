package com.ylt.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ylt.main.dao.UserDaoI;
import com.ylt.main.pojo.Company;
import com.ylt.main.pojo.HangYe;
import com.ylt.main.pojo.Person;
import com.ylt.main.pojo.WxData;
import com.ylt.main.pojo.XingZhi;
@Service
public class UserService implements UserServiceI{
	@Autowired
	private UserDaoI userDao;

	public List<Company> getUserList(Company company) {
		// TODO Auto-generated method stub
		
		return userDao.getUserList(company);
	}

	public List<HangYe> getHangYe() {
		// TODO Auto-generated method stub
		return userDao.getHangYe();
	}

	public void add(Company company) {
		// TODO Auto-generated method stub
		userDao.add(company);
	}

	public void delete(Company company) {
		// TODO Auto-generated method stub
		userDao.delete(company);
	}

	public List<Person> xq(Company company) {
		// TODO Auto-generated method stub
		return userDao.xq(company);
	}

	public int getCount(Integer ccid) {
		// TODO Auto-generated method stub
		return userDao.getCount(ccid);
	}

	public Company toUpdate(Company company) {
		// TODO Auto-generated method stub
		return userDao.toUpdate(company);
	}

	public List<XingZhi> getXingZhi() {
		// TODO Auto-generated method stub
		return userDao.getXingZhi();
	}

	public void update(Company company) {
		// TODO Auto-generated method stub
		userDao.update(company);
	}

	public void addPerson(Person person) {
		// TODO Auto-generated method stub
		userDao.addPerson(person);
	}

	public Person getPerson(Person person) {
		// TODO Auto-generated method stub
		return userDao.getPerson(person);
	}

	public void updatePerson(Person person) {
		// TODO Auto-generated method stub
		userDao.updatePerson(person);
	}

	public void del(Person person) {
		// TODO Auto-generated method stub
		userDao.del(person);
	}
	
	public WxData selectByFromUserName(String FromUserName)
	{
		return userDao.selectByFromUserName(FromUserName);
	}
    
    public int insertData(WxData record)
    {
    	return userDao.insertData(record);
    }
    
    public int updateData(WxData record)
    {
    	return userDao.updateData(record);
    }
    
    public List<WxData> GetAllData()
    {
    	return userDao.GetAllData();
    }
    
    public String MailSend(String message)
    {
    	return userDao.MailSend(message);
    }

}
