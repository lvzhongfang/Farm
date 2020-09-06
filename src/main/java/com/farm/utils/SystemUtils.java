package com.farm.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.farm.jwt.JwtUtil;

public class SystemUtils {

	private static Logger logger = LoggerFactory.getLogger(SystemUtils.class);
	
	public static JSONObject checkQueryData(String data) {
		JSONObject ret = new JSONObject();

		if(StringUtils.isEmpty(data)) {
			ret.put("msg", "request data is null");
			ret.put("code", "0001");
			logger.info("request data is null");
			
			return ret;
		}
		
		JSONObject json = JSONObject.parseObject(data);
		
		if(!json.containsKey("token")) {
			ret.put("msg", "json can not contain key token");
			ret.put("code", "0002");
			logger.info("json can not contain key token , token is null");
			return ret;
		}
		
		String token = json.getString("token");
		
		if(StringUtils.isEmpty(token)) {
			ret.put("msg", "token is null");
			ret.put("code", "0003");
			logger.info("token is null");
			return ret;
		}
		
		String userNo = JwtUtil.getUsername(token);
		
		if(StringUtils.isEmpty(userNo)) {
			ret.put("msg", "can not get userNo from token");
			ret.put("code", "0004");
			logger.info("can not get userNo from token");
			
			return ret;
		}

		ret.put("code", "0000");
		ret.put("msg", "success");
		
		return ret;
	}
	
	public static JSONObject checkAddData(String data) {
		
		JSONObject ret = new JSONObject();

		if(StringUtils.isEmpty(data)) {
			ret.put("msg", "request data is null");
			ret.put("code", "0001");
			logger.info("request data is null");
			
			return ret;
		}
		
		JSONObject json = JSONObject.parseObject(data);
		
		if(!json.containsKey("token")) {
			ret.put("msg", "json can not contain key token");
			ret.put("code", "0002");
			logger.info("json can not contain key token , token is null");
			return ret;
		}
		
		String token = json.getString("token");
		
		if(StringUtils.isEmpty(token)) {
			ret.put("msg", "token is null");
			ret.put("code", "0003");
			logger.info("token is null");
			return ret;
		}
		
		String userNo = JwtUtil.getUsername(token);
		
		if(StringUtils.isEmpty(userNo)) {
			ret.put("msg", "can not get userNo from token");
			ret.put("code", "0004");
			logger.info("can not get userNo from token");
			
			return ret;
		}
		
		if(!json.containsKey("data")) {
			ret.put("msg", "json can not contain key data");
			ret.put("code", "0005");
			logger.info("json can not contain key data");
			return ret;
		}
		
		String d = json.getString("data");
		
		if(StringUtils.isEmpty(d)) {
			ret.put("msg", "data is null");
			ret.put("code", "0006");
			logger.info("data is null");
			return ret;
		}
		
		ret.put("code", "0000");
		ret.put("msg", "success");
		
		return ret;
	}
	
	public static List<String> getSelectedMenus(JSONArray array){
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < array.size(); i++) {
			JSONObject json = array.getJSONObject(i);
			boolean checked = json.getBooleanValue("checked");
			String menuNo = json.getString("name");
			JSONArray c = json.getJSONArray("children");
			if(c != null && c.size() > 0) {
				List<String> l = getSelectedMenus(c);
				if(l != null && l.size() > 0) {
					list.add(menuNo);
					list.addAll(l);
				}
			}else {
				if(checked)
					list.add(menuNo);
			}
		}
		return list;
	}
	
	public static void main(String [] args) {
		String str = "{\"token\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NjE3MTA1MDUsInVzZXJuYW1lIjoiYWRtaW4ifQ.KJ40vC8chCBz3xhitBxIEwD0XSr-2wSbeH8VBaRibtA\",\"data\":{\"roleNo\":\"R001\",\"roleName\":\"R001\",\"status\":\"0\"},\"menus\":[{\"pno\":\"root\",\"path\":\"/system\",\"expand\":true,\"children\":[{\"pno\":\"system\",\"path\":\"user\",\"expand\":true,\"name\":\"user\",\"title\":\"用户管理\",\"nodeKey\":1,\"checked\":true,\"indeterminate\":false},{\"pno\":\"system\",\"path\":\"role\",\"expand\":true,\"name\":\"role\",\"title\":\"角色管理\",\"nodeKey\":2}],\"name\":\"system\",\"title\":\"系统管理\",\"nodeKey\":0,\"checked\":false,\"indeterminate\":true}]}";
	
		JSONObject json = JSONObject.parseObject(str);
		JSONArray array = json.getJSONArray("menus");
		List<String> menus = SystemUtils.getSelectedMenus(array);
		for(int i = 0; i < menus.size(); i++) {
			System.out.println(menus.get(i));
		}
	}
}
