package com.farm.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.farm.system.bean.Role;

/**
 * 
 * @author admin
 *
 */
@Mapper
public interface RoleMapper {

	public Role get(Long id);
	
	public Role findByNo(@Param("roleNo") String roleNo);

	public long add(Role role);

	public int del(Long id);
	
	public List<Role> list(Map<String,Object> map);
	
	public List<Role> find(Map<String,Object> map);
	
	public int count(Map<String,Object> map);
	
	public int update(@Param("params") Map<String,Object> params,@Param("id") Long id);
	
	public List<Role> findByUserNo(@Param("userNo") String userNo);
}
