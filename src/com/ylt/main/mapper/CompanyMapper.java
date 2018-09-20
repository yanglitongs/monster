package com.ylt.main.mapper;

import com.ylt.main.pojo.Company;
import com.ylt.main.pojo.CompanyExample;
import com.ylt.main.pojo.HangYe;
import com.ylt.main.pojo.Person;
import com.ylt.main.pojo.XingZhi;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CompanyMapper {
    int countByExample(CompanyExample example);

    int deleteByExample(CompanyExample example);

    int deleteByPrimaryKey(Integer ccid);

    int insert(Company record);

    int insertSelective(Company record);

    List<Company> selectByExample(CompanyExample example);

    Company selectByPrimaryKey(Integer ccid);

    int updateByExampleSelective(@Param("record") Company record, @Param("example") CompanyExample example);

    int updateByExample(@Param("record") Company record, @Param("example") CompanyExample example);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);

	List<Company> getUserList(Company company);

	List<Person> getXq(Company company);

	int getCount(Integer ccid);

	Company toUpdate(Company company);

	List<XingZhi> getXingZhi();

	void update(Company company);

	List<HangYe> getHangYe();
}