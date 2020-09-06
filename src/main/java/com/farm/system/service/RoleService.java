package com.farm.system.service;

import java.util.List;
import java.util.Map;

import com.farm.system.bean.Role;

public interface RoleService {

	public Role findByNo(String roleNo);
	
	public Long add(Role r);
	
	public Role get(Long id);
	
	public int update(Map<String,Object> map);
	
	public int del(Long id);
	
	public List<Role> list(Map<String,Object> map);
	
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	public List<Role> find(Map<String,Object> map);
	
	public Integer count(Map<String,Object> map);
}
