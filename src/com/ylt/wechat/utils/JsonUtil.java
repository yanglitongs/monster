package com.ylt.wechat.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {

	/***
     * json�ַ���תjava List
     * @param rsContent
     * @return
     * @throws Exception
     */
    public static JSONObject jsonStringToList(String rsContent,String key) throws Exception
    {
    	String result="";
    	JSONObject obj = JSONObject.fromObject(rsContent);
    	JSONObject obj2 = obj.getJSONObject(key); 
    	return obj2;
    }
}
