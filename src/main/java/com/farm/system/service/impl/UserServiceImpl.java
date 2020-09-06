package com.farm.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.farm.common.Constant;
import com.farm.system.bean.Farm;
import com.farm.system.bean.Permission;
import com.farm.system.bean.Role;
import com.farm.system.bean.User;
import com.farm.system.dao.FarmMapper;
import com.farm.system.dao.PermissionMapper;
import com.farm.system.dao.RoleMapper;
import com.farm.system.dao.UserMapper;
import com.farm.system.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserMapper um;
	
	@Autowired
	private FarmMapper fm;
	
	@Autowired
	private RoleMapper rm;
	
	@Autowired
	private PermissionMapper pm;
	
	@Override
	public User findByNo(String userNo) {
		
		if(StringUtils.isEmpty(userNo)) {
			logger.info("user no is null");
			return null;
		}
		
		User u = um.findByNo(userNo);
		
		if(u == null) {
			logger.info("can not find user , user no is " + userNo);
			return null;
		}
		//MD5 salt 不返回到前端
		//u.setSalt("");
		
		//加载farm信息
		List<Farm> farms = fm.findByUserNo(userNo);
		if(farms != null && farms.size() > 0) {
			u.setFarms(farms);
		}
		
		//加载role
		
		return u;
	}

	@Override
	public Long add(User u) {
		
		if(u == null) {
			logger.info("user is null.");
			return -1l;
		}
		
		String userNo = u.getUserNo();
		String phoneNo = u.getPhoneNo();
		
		User u01 = um.findByNo(userNo);
		
		if(u01 != null) {
			String un = u01.getUserNo();
			if(StringUtils.isEmpty(un)) {
				logger.info("user not exist , user no is " + userNo);
			}else {
				logger.info("user exist , user no is " + un);
				return -2l;
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("phoneNo",phoneNo);
		
		List<User> list = um.list(map);
		
		if(list != null && list.size() > 0) {
			logger.info("phone no is in used , phone no is " + phoneNo);
			return -3l;
		}
		
		Long id = um.add(u);
		
		return id;
	}

	@Override
	public User get(Long id) {
		
		if(id != null && id > 0)
			return um.get(id);
		return null;
	}

	@Override
	public int update(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int del(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<User> list(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> find(Map<String, Object> map) {
		
		if(map != null && map.size() > 0) {
			if(!map.containsKey("begin"))
				map.put("begin", 0);
			else {
				try {
					Integer begin = (Integer)map.get("begin");
					if(begin < 0)
						map.put("begin", 0);
				}catch(Exception e) {
					map.put("begin", 0);
				}
			}
			
			if(!map.containsKey("size"))
				map.put("size", 10);
			else {
				try {
					Integer size = (Integer)map.get("size");
					if(size < 0)
						map.put("size", 10);
				}catch(Exception e) {
					map.put("size", 10);
				}
			}
		}else {
			map = new HashMap<String,Object>();
			map.put("begin", 0);
			map.put("size", 10);
		}
		
		List<User> list = um.find(map);
		
		return list;
	}

	public String getSalt(String userNo) {
		if(StringUtils.isEmpty(userNo)) {
			logger.info("userNo is null");
			return null;
		}
		
		User u = um.findByNo(userNo);
		
		if(u == null) {
			logger.info("can not find user , user no is " + userNo);
			return null;
		}
		
		String salt = u.getSalt();
		
		return salt;
	}

	@Override
	public Map<String,List<String>> findRolesAndPermisByNo(String userNo) {
		
		Map<String,List<String>> map = new HashMap<>();
		
		if(StringUtils.isEmpty(userNo)) {
			logger.info("user no is null");
			return map;
		}else {
			//获取role
			List<Role> roles = rm.findByUserNo(userNo);
			
			if(roles != null && !roles.isEmpty()) {
				
				List<String> roleNos = roles.stream().map(role -> role.getRoleNo()).collect(Collectors.toList());
				map.put("userNo", roleNos);
				//获取permission
				List<Permission> perms = pm.findByRoleNos(roleNos);
				if(perms != null && !perms.isEmpty()) {
					
					List<String> permsNos = new ArrayList<>();
					List<String> menuPerm = new ArrayList<>();
					List<String> operPerm = new ArrayList<>();
					
					for(int i = 0; i < perms.size(); i++) {
						Permission perm = perms.get(i);
						permsNos.add(perm.getPermNo());
						
						if(Constant.MENU_TYPE.equals(perm.getPermType())) {
							menuPerm.add(perm.getPermNo());
						}else if(Constant.OPERATION_TYPE.equals(perm.getPermType())){
							operPerm.add(perm.getPermNo());
						}
					}
					map.put("perms", permsNos);
					map.put("menuPerms", menuPerm);
					map.put("operPerms", operPerm);
				}
			}
		}
		
		return map;
	}

	@Override
	public Integer count(Map<String, Object> map) {
		return um.count(map);
	}
}
