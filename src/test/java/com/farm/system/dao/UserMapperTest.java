package com.farm.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.farm.FarmApplication;
import com.farm.system.bean.User;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FarmApplication.class )//这里是启动类
public class UserMapperTest {

	@Autowired
	private UserMapper um;

	//@Test
	public void get() {
		User u = um.get(1l);
		if(u == null) {
			System.out.println("can not found user that who's id is 1");
		}else {
			System.out.println("user is : " + u.toString());
		}
	}

	@Test
	public void findByNoAndPwd() {
		
		String base64Salt = Base64.encode("b3e89c8b050f518e2cabf883770e8310".getBytes());
        
        String pwd = new Sha256Hash("123456",base64Salt).toHex();
        
        User u = um.findByNoAndPwd("admin", pwd);
        
        System.out.println("userNo is " + u.getUserNo());
	}
	
	//@Test
	public void list() {
		Map<String,Object> map = new HashMap<String,Object>();

		List<User> list = um.list(map);

		if(list != null && list.size() > 0) {
			System.out.println(list.size());
		}
	}
}
