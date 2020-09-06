package com.farm.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.farm.system.bean.User;

/**
 * 
 * @author admin
 *
 */
@Mapper
public interface UserMapper {

	public User get(Long id);
	
	public User findByNo(@Param("userNo") String userNo);
	
	public User findByNoAndPwd(@Param("userNo") String userNo, @Param("pwd") String pwd);

	public long add(User user);

	public int del(Long id);
	
	public List<User> list(Map<String,Object> map);
	
	public List<User> find(Map<String,Object> map);
	
	public int count(Map<String,Object> map);
	
	public int updateUserEmail(@Param("email") String email, @Param("id") Long id);

	public int updateStatus(@Param("status") String status, @Param("id") Long id);
	
	public int update(@Param("params") Map<String,Object> params,@Param("id") Long id);
	
	public List<User> findByFarmNo(@Param("farmNo") String farmNo);

}
