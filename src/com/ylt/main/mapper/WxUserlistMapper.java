package com.ylt.main.mapper;

import com.ylt.main.pojo.WxUserlist;
import com.ylt.main.pojo.WxUserlistKey;

public interface WxUserlistMapper {
    int deleteByPrimaryKey(WxUserlistKey key);

    int insert(WxUserlist record);

    int insertSelective(WxUserlist record);

    WxUserlist selectByPrimaryKey(WxUserlistKey key);

    int updateByPrimaryKeySelective(WxUserlist record);

    int updateByPrimaryKey(WxUserlist record);
}