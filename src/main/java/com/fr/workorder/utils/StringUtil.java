package com.fr.workorder.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.UUID;

public class StringUtil {

    // 生成随机字符串
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // MD5加密
    public static String md5(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    // 封装成json字符串
    public static String getJSONString(int code, String content, Map<String,Object> map){
        JSONObject json = new JSONObject();
        json.put("code",code);
        json.put("msg",content);
        if(map!=null){
            // 标识是否携带数据的字段
            json.put("data", true);
            for(String key: map.keySet()){
                json.put(key,map.get(key));
            }
        }else
            json.put("data",false);
        return json.toJSONString();
    }
    public static String getJSONString(int code, String content){
        JSONObject json = new JSONObject();
        json.put("code",code);
        json.put("msg",content);
        json.put("data", true);
        return json.toJSONString();
    }
    public static String getJSONString(int code){
        JSONObject json = new JSONObject();
        json.put("code",code);
        json.put("data", false);
        return json.toJSONString();
    }
}
