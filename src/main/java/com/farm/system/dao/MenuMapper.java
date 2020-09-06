package com.farm.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.farm.system.bean.Menu;

/**
 * 
 * @author admin
 *
 */
@Mapper
public interface MenuMapper {

	public Menu get(Long id);
	
	public Menu findByNo(@Param("menuNo") String menuNo);

	public long add(Menu menu);

	public int del(Long id);
	
	public List<Menu> list(Map<String,Object> map);
	
	public List<Menu> find(Map<String,Object> map);
	
	public int count(Map<String,Object> map);
	
	public int update(@Param("params") Map<String,Object> params,@Param("id") Long id);
	
	public List<Menu> findByPermNos(@Param("list") List<String> permNos);
	
}
