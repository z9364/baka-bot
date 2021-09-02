package com.baka.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.InputStream;
import java.util.Map;

public class JSONUtil {

    public static Map<String, Object> str2json(String json){
        return JSONObject.parseObject(json);
    }

    public static Map<String, Object> io2json(InputStream inputStream) throws Exception{
        return str2json(IOUtil.readInputStream(inputStream));
    }

}
