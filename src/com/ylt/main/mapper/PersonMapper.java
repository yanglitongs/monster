package com.ylt.main.mapper;

import com.ylt.main.pojo.Person;
import com.ylt.main.pojo.PersonExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PersonMapper {
    int countByExample(PersonExample example);

    int deleteByExample(PersonExample example);

    int insert(Person record);

    int insertSelective(Person record);

    List<Person> selectByExample(PersonExample example);

    int updateByExampleSelective(@Param("record") Person record, @Param("example") PersonExample example);

    int updateByExample(@Param("record") Person record, @Param("example") PersonExample example);

	void addPerson(Person person);

	Person getPerson(Person person);

	void updatePerson(Person person);

	void delete(Person person);
}