package com.farm.system.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.farm.jwt.JwtUtil;
import com.farm.system.bean.Role;
import com.farm.system.service.RoleService;
import com.farm.utils.SystemUtils;

@RestController
public class RoleController {

	private static Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;

	@RequestMapping(value="/system/role/list",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody JSONObject list(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {

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

			if(json.containsKey("roleNo")) {
				String roleNo = json.getString("roleNo");
				if(!StringUtils.isEmpty(roleNo))
					map.put("roleNo", roleNo);

			}

			if(json.containsKey("roleName")) {
				String roleName = json.getString("roleName");
				if(!StringUtils.isEmpty(roleName))
					map.put("roleName", "%" + roleName + "%");

			}

			List<Role> list = roleService.find(map);

			Integer total = roleService.count(map);

			ret.put("data", list.toArray());
			ret.put("total", total);
			ret.put("current", page);
			ret.put("size", pageSize);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return ret;
	}

	@RequestMapping(value="/system/role/getAll",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody JSONObject getAll(HttpServletRequest request, HttpServletResponse response) {

		JSONObject ret = new JSONObject();

		try {
			List<Role> list = roleService.list(null);

			ret.put("data", list.toArray());

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return ret;
	}

	@RequestMapping(value="/system/role/add",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
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

			Role r = JSONObject.parseObject(json.getString("data"), Role.class);

			r.setCreateTime(LocalDateTime.now());
			r.setCreateUser(userNo);
			r.setModifyTime(LocalDateTime.now());
			r.setModifyUser(userNo);
			r.setStatus("0");

			Long rid = roleService.add(r);
			
			JSONArray menus = json.getJSONArray("menus");
			
			//long c = roleService.addRM(userNo,r.getRoleNo(), menus);
			
			ret.put("msg", "role add success");
			ret.put("code", "0000");
			ret.put("role count", rid);
			//ret.put("role menu count", c);
		}else {
			return checkRet;
		}

		return ret;
	}
}
