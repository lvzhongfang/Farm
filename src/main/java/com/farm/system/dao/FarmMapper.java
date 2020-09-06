package com.farm.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.farm.system.bean.Farm;

@Mapper
public interface FarmMapper {

	public Farm get(Long id);
	
	public Farm findByNo(@Param("farmNo") String farmNo);

	public long add(Farm farm);

	public int del(Long id);
	
	public List<Farm> list(Map<String,Object> map);
	
	public List<Farm> find(Map<String,Object> map);
	
	public int count(Map<String,Object> map);
	
	public int update(@Param("params") Map<String,Object> params,@Param("id") Long id);
	
	public List<Farm> findByUserNo(@Param("userNo") String userNo);
}
