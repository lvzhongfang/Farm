package com.farm.system.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.farm.common.ResponseData;
import com.farm.jwt.JwtUtil;
import com.farm.system.bean.Menu;
import com.farm.system.bean.User;
import com.farm.system.service.MenuService;
import com.farm.system.service.UserService;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@RestController
public class LoginController {

	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private MenuService menuService;
	/*
	@RequestMapping(value="/login",method = RequestMethod.POST)
    public @ResponseBody User login(HttpServletRequest request) {
    	
		JSONObject jsonObj = this.getJson(request);
		
		logger.info("data = " + jsonObj.toString());
		
		String userName = jsonObj.getString("userName");
		String password = jsonObj.getString("password");
    	logger.info("userName = " + userName);
    	logger.info("password = " + password);
    	
    	User result = null;
        Subject subject = SecurityUtils.getSubject();

        // 此处的密码应该是按照后台的加密规则加密过的，不应该传输明文密码
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

        try {
            subject.login(token);
            result = userService.findByName(userName);
        } catch (UnknownAccountException e) {
        	logger.error("用户名或密码错误");
            e.printStackTrace();
        } catch (IncorrectCredentialsException e) {
        	logger.error("用户名或密码错误");
            e.printStackTrace();
        } catch (AuthenticationException e) {
            //其他错误，比如锁定，如果想单独处理请单独catch处理
        	logger.error("其他错误");
            e.printStackTrace();
        }
        return result;
    }
	*/
	/*
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public @ResponseBody User login(@RequestParam(value = "userName", required = true) String userName,  
            @RequestParam(value = "password", required = true) String password) {
    	
    	logger.info("userName = " + userName);
    	logger.info("password = " + password);
    	
    	User result = null;
        Subject subject = SecurityUtils.getSubject();

        // 此处的密码应该是按照后台的加密规则加密过的，不应该传输明文密码
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

        try {
            subject.login(token);
            result = userService.findByName(userName);
        } catch (UnknownAccountException e) {
        	logger.error("用户名或密码错误");
            e.printStackTrace();
        } catch (IncorrectCredentialsException e) {
        	logger.error("用户名或密码错误");
            e.printStackTrace();
        } catch (AuthenticationException e) {
            //其他错误，比如锁定，如果想单独处理请单独catch处理
        	logger.error("其他错误");
            e.printStackTrace();
        }
        return result;
    }
    */
	
	
	@RequestMapping(value="/login",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseData<JSONObject> login(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
    	
		ResponseData<JSONObject> rd = new ResponseData<>();
		
		JSONObject ret = new JSONObject();
		
		User user = null;
		String userNo = "";
		String salt = "";
		String base64Salt = "";
		String pwd = "";
		
        try {
        	
        	JSONObject json = JSONObject.parseObject(data);
			userNo = json.getString("userName");
			String password = json.getString("password");
			
			logger.info("userName = " + userNo);
	    	logger.info("password = " + password);

            user = userService.findByNo(userNo);
            
            salt = user.getSalt();
            
	        base64Salt = Base64.encode(salt.getBytes());
	        
	        pwd = new Sha256Hash(password,base64Salt).toHex();
            
	        if(pwd.equals(user.getPwd())) {
	        	String newToken = JwtUtil.sign(userNo, pwd);
	            //role and permission
				/*
				 * Map<String, List<String>> map = userService.findRolesAndPermisByNo(userNo);
				 * 
				 * List<String> roles = map.get("userNo");
				 * 
				 * List<String> menuPerm = map.get("menuPerms"); List<String> operPerm =
				 * map.get("operPerms");
				 * 
				 * JSONArray menus = menuService.jsonMenu(menuPerm);
				 * 
				 * ret.put("roles", roles); ret.put("menuPerms", menuPerm); ret.put("operPerms",
				 * operPerm); ret.put("menus", menus);
				 */
	        	
	            response.setHeader("x-auth-token", newToken);
	            ret.put("token", newToken);
	            
	            rd.setData(ret);
	            rd.setCode("0000");
	            rd.setMsg("success");
	        }else {
	        	logger.error("用户名或密码错误");
	            rd.setCode("0001");
	            rd.setMsg("用户名或密码错误");
	        }
        } catch (UnknownAccountException e) {
        	logger.error("用户名或密码错误");
            logger.error("error",e);
            rd.setCode("0001");
            rd.setMsg("用户名或密码错误");
        } catch (IncorrectCredentialsException e) {
        	logger.error("用户名或密码错误");
        	logger.error("error",e);
        	rd.setCode("0001");
            rd.setMsg("用户名或密码错误");
        } catch (AuthenticationException e) {
            //其他错误，比如锁定，如果想单独处理请单独catch处理
        	logger.error("其他错误");
        	logger.error("error",e);
        	rd.setCode("0002");
            rd.setMsg("服务器错误，请联系管理员");
        }
        return rd;
    }
	
	@RequiresAuthentication
	@RequestMapping(value="/getInfo",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseData<JSONObject> get_info(@RequestParam("token") String token) {
    	
		ResponseData<JSONObject> rd = new ResponseData<>();
		
		logger.info("token = " + token);
		String username = JwtUtil.getUsername(token);
    	User user = userService.findByNo(username);
    	//拉取用户信息，通过用户权限和跳转的页面的name来判断是否有权限访问;access必须是一个数组，如：['super_admin'] ['super_admin', 'admin']
    	
    	rd.setCode("0000");
    	rd.setMsg("success");

    	JSONObject ret = new JSONObject();
    	
    	ret.put("avatar",user.getAvatar());
        ret.put("name",user.getUserName());
        ret.put("userId",user.getId());
        
        Map<String, List<String>> map = userService.findRolesAndPermisByNo(username);
    	
    	List<String> roles = map.get("userNo");
    	
    	ret.put("access", roles);
    	
        rd.setData(ret);
        
        return rd;
    }
	
    @CrossOrigin
    @RequestMapping(value="/getInfo/{userNo}",method = RequestMethod.GET)
    public @ResponseBody User getInfo(@PathVariable String userNo) {
    	logger.info("userName = " + userNo);
    	
    	User result = userService.findByNo(userNo);
    	
        return result;
    }
    
    /**
    @RequestMapping(value="/getMenu",method = RequestMethod.GET)
    public @ResponseBody JSONArray getMenu(@RequestParam("token") String token,HttpServletRequest request, HttpServletResponse response) {
    	
    	logger.info("token = " + token);
		String username = JwtUtil.getUsername(token);
		logger.info("user name " + username);
		
    	JSONArray result = new JSONArray();
    	
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
    	
        return result;
    }
    
    */
    @RequestMapping(value="/logout",method = RequestMethod.GET)
    public @ResponseBody JSONObject logout(@RequestParam("token") String token,HttpServletRequest request, HttpServletResponse response) {
    	logger.info("token = " + token);
		String username = JwtUtil.getUsername(token);
		logger.info("user name " + username);
		
		return new JSONObject();
    }
    
    public JSONObject getJson(HttpServletRequest request){
        JSONObject jsonParam = null;
        try {
            // 获取输入流
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));

            // 写入数据到Stringbuilder
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = streamReader.readLine()) != null) {
                sb.append(line);
            }
            jsonParam = JSONObject.parseObject(sb.toString());
            // 直接将json信息打印出来
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonParam;
    }

}
