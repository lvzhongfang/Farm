package com.farm.system.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.farm.common.ResponseData;
import com.farm.jwt.JwtUtil;
import com.farm.system.service.MenuService;
import com.farm.system.service.UserService;

@RestController
public class MenuController {

	private static Logger logger = LoggerFactory.getLogger(MenuController.class);

	@Autowired
	private MenuService menuService;

	@Autowired
	private UserService userService;

	@RequestMapping(value="/getMenu",method = RequestMethod.GET)
	public @ResponseBody ResponseData<JSONObject> getMenu(@RequestParam("token") String token,HttpServletRequest request, HttpServletResponse response) {

		ResponseData<JSONObject> rd = new ResponseData<>();

		JSONObject ret = new JSONObject();

		logger.info("token = " + token);
		String userNo = JwtUtil.getUsername(token);
		logger.info("user name " + userNo);

		if(!StringUtils.isEmpty(userNo)) {
			//result = menuService.jsonMenu(username);
			//role and permission

			Map<String, List<String>> map = userService.findRolesAndPermisByNo(userNo);

			List<String> roles = map.get("userNo");

			List<String> menuPerm = map.get("menuPerms"); List<String> operPerm =
					map.get("operPerms");

			JSONArray menus = menuService.jsonMenu(menuPerm);

			ret.put("roles", roles); 
			ret.put("menuPerms", menuPerm); 
			ret.put("operPerms",operPerm); 
			ret.put("menus", menus);

			rd.setData(ret);
			rd.setCode("0000");
			rd.setMsg("success");

		}else {
			rd.setCode("0001");
			rd.setMsg("token为空");
		}
		/**
    	JSONObject component = new JSONObject();
    	component.put("path", "/components");
    	component.put("name", "components");
    	JSONObject compMeta = new JSONObject();
    	compMeta.put("icon", "logo-buffer");
    	compMeta.put("title", "组件");
    	component.put("meta", compMeta);

    	JSONArray compChildren = new JSONArray();

    	JSONObject tsp = new JSONObject();

    	tsp.put("path", "tree_select_page");
    	tsp.put("name", "tree_select_page");
    	JSONObject tspMeta = new JSONObject();
    	tspMeta.put("icon", "md-arrow-dropdown-circle");
    	tspMeta.put("title", "树状下拉选择器");
    	tsp.put("meta", tspMeta);
    	tsp.put("component", "() => import('@/view/components/tree-select/index.vue')");

    	compChildren.add(tsp);

    	JSONObject ctp = new JSONObject();

    	ctp.put("path", "count_to_page");
    	ctp.put("name", "count_to_page");
    	JSONObject ctpMeta = new JSONObject();
    	ctpMeta.put("icon", "md-trending-up");
    	ctpMeta.put("title", "数字渐变");
    	ctp.put("meta", tspMeta);
    	ctp.put("component", "() => import('@/view/components/count-to/count-to.vue')");

    	compChildren.add(ctp);

    	component.put("children", compChildren);
    	result.add(component);
		 */

		return rd;
	}

	@RequestMapping(value="/system/menu/getAll",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody JSONArray getAllMenu(HttpServletRequest request, HttpServletResponse response) {

		JSONArray result = new JSONArray();

		//result = menuService.jsonMenu();

		return result;
	}
}
