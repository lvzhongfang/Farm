package com.farm.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.farm.system.bean.Permission;

@Mapper
public interface PermissionMapper {

	public Permission get(Long id);
	
	public Permission findByNo(@Param("permNo") String permNo);

	public long add(Permission operation);

	public int del(Long id);
	
	public List<Permission> list(Map<String,Object> map);
	
	public List<Permission> find(Map<String,Object> map);
	
	public int count(Map<String,Object> map);
	
	public int update(@Param("params") Map<String,Object> params,@Param("id") Long id);
	
	public List<Permission> findByRoleNos(@Param("list") List<String> roleNos);
}
