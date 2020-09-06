package com.farm.system.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.farm.common.ResponseData;
import com.farm.jwt.JwtUtil;
import com.farm.system.bean.User;
import com.farm.system.service.UserService;
import com.farm.utils.SystemUtils;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@RestController
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value="/system/user/list",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseData<JSONObject> list(@RequestParam("token") String token, @RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
    	
		ResponseData<JSONObject> rd = new ResponseData<>();
		
		JSONObject ret = new JSONObject();
		
		try {
			
			JSONObject json = JSONObject.parseObject(data);
			
			Integer page = 0;
			Integer pageSize = 10;
			
			if(json.containsKey("page"))
				page = json.getInteger("page");
			
			if(json.containsKey("pageSize"))
				pageSize = json.getInteger("pageSize");
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("begin", page - 1);
			map.put("size", pageSize);
			
			if(json.containsKey("userNo")) {
				String userNo = json.getString("userNo");
				map.put("userNo", userNo);
				
			}
			
			List<User> list = userService.find(map);
			
			Integer total = userService.count(map);
			
			rd.setCode("0000");
	    	rd.setMsg("success");
			
			ret.put("data", JSON.toJSON(list));
			ret.put("total", total);
			ret.put("current", page);
			ret.put("size", pageSize);
			
			rd.setData(ret);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

        return rd;
    }

	@RequestMapping(value="/system/user/getById",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody JSONObject getById(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
    	
		JSONObject ret = new JSONObject();

		if(logger.isDebugEnabled())
			logger.debug("request data : " + data);
		
		JSONObject checkRet = SystemUtils.checkQueryData(data);
		
		String code = checkRet.getString("code");
		
		if("0000".equals(code)) {
			JSONObject json = JSONObject.parseObject(data);
			if(json.containsKey("id")) {
				Integer id = json.getInteger("id");
				
				User user = userService.get(Long.parseLong(id.toString()));
				
				if(user == null) {
					ret.put("msg", "no data find");
					ret.put("code", "0011");
					ret.put("data", user);
				}else {
					ret.put("msg", "query success");
					ret.put("code", "0000");
					ret.put("data", user);
				}
			}else {
				ret.put("msg", "json must contain key id");
				ret.put("code", "0010");
			}
		}else {
			return checkRet;
		}
		logger.info("json = " + ret.toString());
		return ret;
	}
	
	@RequestMapping(value="/system/user/add",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody JSONObject add(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
    	
		JSONObject ret = new JSONObject();
		
		if(logger.isDebugEnabled())
			logger.debug("request data : " + data);
		
		JSONObject checkRet = SystemUtils.checkAddData(data);
		
		String code = checkRet.getString("code");
		
		if("0000".equals(code)) {
			JSONObject json = JSONObject.parseObject(data);
			
			String token = json.getString("token");

			String userNo = JwtUtil.getUsername(token);

			String d = json.getString("data");
			
			User u = JSONObject.parseObject(d, User.class);
			
			String salt = JwtUtil.generateSalt();
	        String base64Salt = Base64.encode(salt.getBytes());
	        
			String pwd = new Sha256Hash("123456",base64Salt).toHex();
			
			u.setPwd(pwd);
			u.setSalt(salt);
			u.setCreateTime(LocalDateTime.now());
			u.setCreateUser(userNo);
			u.setModifyTime(LocalDateTime.now());
			u.setModifyUser(userNo);
			u.setStatus("0");
			u.setPwdErrorTime(0);
			
			Long uid = userService.add(u);
			
			ret.put("msg", "user add success");
			ret.put("code", "0000");
			ret.put("uid", uid);
		}else {
			return checkRet;
		}
		
		return ret;
	}
}
