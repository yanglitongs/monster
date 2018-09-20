package com.ylt.main.mapper;

import java.util.List;

import com.ylt.main.pojo.WxData;
import com.ylt.main.pojo.WxDataWithBLOBs;

public interface WxDataMapper {
    int deleteByPrimaryKey(Long dataid);

    int insert(WxDataWithBLOBs record);

    int insertSelective(WxDataWithBLOBs record);

    WxDataWithBLOBs selectByPrimaryKey(Long dataid);

    int updateByPrimaryKeySelective(WxDataWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(WxDataWithBLOBs record);

    int updateByPrimaryKey(WxData record);
    
    WxData selectByFromUserName(String FromUserName);
    
    int insertData(WxData record);
    
    int updateData(WxData record);
    
    List<WxData> GetAllData();
}