package com.farm.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.farm.system.bean.Operation;

@Mapper
public interface OperationMapper {

	public Operation get(Long id);
	
	public Operation findByNo(@Param("operNo") String operNo);

	public long add(Operation operation);

	public int del(Long id);
	
	public List<Operation> list(Map<String,Object> map);
	
	public List<Operation> find(Map<String,Object> map);
	
	public int count(Map<String,Object> map);
	
	public int update(@Param("params") Map<String,Object> params,@Param("id") Long id);
	
	public List<Operation> findByPermNos(@Param("list") List<String> permNos);
}
