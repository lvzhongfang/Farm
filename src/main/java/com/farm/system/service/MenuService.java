package com.farm.system.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONArray;
import com.farm.system.bean.Menu;

public interface MenuService {

	public Menu findByNo(String menuNo);
	
	public Long add(Menu m);
	
	public Menu get(Long id);
	
	public int update(Map<String,Object> map);
	
	public int del(Long id);
	
	public List<Menu> list(Map<String,Object> map);
	
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	public List<Menu> find(Map<String,Object> map);
	
	public List<Menu> findByPermNos(@Param("permNos") List<String> permNos);
	
	public JSONArray jsonMenu(List<String> permNos);
	
}
