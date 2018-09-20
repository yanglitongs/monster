package com.ylt.main.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ylt.main.mapper.CompanyMapper;
import com.ylt.main.mapper.PersonMapper;
import com.ylt.main.mapper.WxDataMapper;
import com.ylt.main.pojo.Company;
import com.ylt.main.pojo.HangYe;
import com.ylt.main.pojo.Person;
import com.ylt.main.pojo.WxData;
import com.ylt.main.pojo.XingZhi;
@Repository
public class UserDao extends SqlSessionDaoSupport implements UserDaoI{
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private PersonMapper personMapper;
	@Autowired
	private WxDataMapper wxdataMapper;
	public List<Company> getUserList(Company company) {
		List<Company> list=companyMapper.getUserList(company);
		return list;
	}
	public void add(Company company) {
		// TODO Auto-generated method stub
		companyMapper.insert(company);
	}
	public void del(Company company) {
		// TODO Auto-generated method stub
		companyMapper.deleteByPrimaryKey(company.getCcid());
	}
	public List<Person> xq(Company company) {
		// TODO Auto-generated method stub
		List<Person> personList=companyMapper.getXq(company);
		return personList;
	}
	public int getCount(Integer ccid) {
		// TODO Auto-generated method stub
		return companyMapper.getCount(ccid);
	}
	public Company toUpdate(Company company) {
		// TODO Auto-generated method stub
		return companyMapper.toUpdate(company);
	}
	public List<XingZhi> getXingZhi() {
		// TODO Auto-generated method stub
		return companyMapper.getXingZhi();
	}
	public void update(Company company) {
		// TODO Auto-generated method stub
		companyMapper.update(company);
	}
	public List<HangYe> getHangYe() {
		// TODO Auto-generated method stub
		return companyMapper.getHangYe();
	}
	public void addPerson(Person person) {
		// TODO Auto-generated method stub
		personMapper.addPerson(person);
	}
	public Person getPerson(Person person) {
		// TODO Auto-generated method stub
		return personMapper.getPerson(person);
	}
	public void updatePerson(Person person) {
		// TODO Auto-generated method stub
		personMapper.updatePerson(person);
	}
	public void del(Person person) {
		// TODO Auto-generated method stub
		personMapper.delete(person);
	}
	public void delete(Company company) {
		// TODO Auto-generated method stub
		companyMapper.deleteByPrimaryKey(company.getCcid());
	}

	public WxData selectByFromUserName(String FromUserName)
	{
		return wxdataMapper.selectByFromUserName(FromUserName);
	}
    
    public int insertData(WxData record)
    {
    	return wxdataMapper.insertData(record);
    }
    
    public int updateData(WxData record)
    {
    	return wxdataMapper.updateData(record);
    }
    
    public List<WxData> GetAllData()
    {
    	return wxdataMapper.GetAllData();
    }
    
    public String MailSend(String message)
    {
    	return "ok";
    	}

}
