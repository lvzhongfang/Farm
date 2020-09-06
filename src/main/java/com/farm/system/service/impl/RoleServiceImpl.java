package com.farm.system.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.farm.system.bean.Permission;
import com.farm.system.bean.Role;
import com.farm.system.dao.PermissionMapper;
import com.farm.system.dao.RoleMapper;
import com.farm.system.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	private static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
	private RoleMapper rm;
	
	@Autowired
	private PermissionMapper pm;
	
	@Override
	public Role findByNo(String roleNo) {
		
		Role role = null;
		
		if(StringUtils.isEmpty(roleNo)) {
			logger.info("role no is null");
		}else {
			role = rm.findByNo(roleNo);
			if(role != null) {
				if(logger.isDebugEnabled())
					logger.info("role id " + role.getId());
				List<String> list = Collections.emptyList();
				list.add(roleNo);
				//加载permission
				List<Permission> perms = pm.findByRoleNos(list);
				
				role.setPermissions(perms);
			}else {
				logger.info("role not exist , role no is " + roleNo);
			}
		}
		return role;
	}

	@Override
	public Long add(Role r) {
		
		Long rid = 0l;
		
		if(r == null) {
			logger.info("role is null");
			return null;
		}
		
		String roleNo = r.getRoleNo();
		
		Role role = rm.findByNo(roleNo);
		
		if(role == null) {
			rid = rm.add(r);
		}else {
			rid = role.getId();
			logger.info("role is exist , role id is " + rid);
			rid = null;
		}
		return rid;
	}

	@Override
	public Role get(Long id) {
		Role r = rm.get(id);
		if(r != null) {
			List<String> list = Collections.emptyList();
			list.add(r.getRoleNo());
			//加载permission
			List<Permission> perms = pm.findByRoleNos(list);
			
			r.setPermissions(perms);
		}
		return r;
	}

	@Override
	public int update(Map<String, Object> map) {
		
		Long id = (Long)map.get("id");
		
		if(id == null) {
			logger.info("id is null");
			return 0;
		}
		
		return rm.update(map, id);
	}

	@Override
	public int del(Long id) {
		
		if(id == null || id <= 0)
			return 0;
		
		return rm.del(id);
	}

	@Override
	public List<Role> list(Map<String, Object> map) {
		
		List<Role> list = rm.list(map);
		
		return list;
	}

	@Override
	public List<Role> find(Map<String, Object> map) {
		
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
		
		List<Role> list = rm.find(map);
		
		return list;
	}

	@Override
	public Integer count(Map<String, Object> map) {
		return rm.count(map);
	}
}
