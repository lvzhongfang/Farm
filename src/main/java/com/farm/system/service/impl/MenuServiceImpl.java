package com.farm.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.farm.system.bean.Menu;
import com.farm.system.dao.MenuMapper;
import com.farm.system.service.MenuService;

@Service("menuService")
public class MenuServiceImpl implements MenuService {

	private static Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
	
	@Autowired
	private MenuMapper mm;
	
	@Override
	public Menu findByNo(String menuNo) {
		
		if(StringUtils.isEmpty(menuNo)) {
			logger.info("menu no is null");
			return null;
		}
		
		Menu m = mm.findByNo(menuNo);
		
		if(m != null) {
			if(logger.isDebugEnabled())
				logger.info("menu id " + m.getId());
		}else {
			logger.info("menu not exist , menu no is " + menuNo);
		}
		
		return m;
	}

	@Override
	public Long add(Menu m) {
		Long mid = 0l;
		
		if(m == null) {
			logger.info("menu is null");
			return null;
		}
		
		String menuNo = m.getMenuNo();
		
		Menu menu = mm.findByNo(menuNo);
		
		if(menu == null) {
			mid = mm.add(m);
		}else {
			mid = menu.getId();
			logger.info("menu is exist , menu id is " + mid);
			mid = null;
		}
		return mid;
	}

	@Override
	public Menu get(Long id) {
		return mm.get(id);
	}

	@Override
	public int update(Map<String, Object> map) {
		Long id = (Long)map.get("id");
		
		if(id == null) {
			logger.info("id is null");
			return 0;
		}
		return mm.update(map, id);
	}

	@Override
	public int del(Long id) {
		
		if(id == null || id <= 0)
			return 0;
		
		return mm.del(id);
	}

	@Override
	public List<Menu> list(Map<String, Object> map) {
		
		List<Menu> list = mm.list(map);
		
		return list;
	}

	@Override
	public List<Menu> find(Map<String, Object> map) {
		
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
		
		List<Menu> list = mm.find(map);
		
		return list;
	}

	@Override
	public List<Menu> findByPermNos(List<String> permNos) {
		
		List<Menu> menus = new ArrayList<Menu>();
		
		if(permNos != null && !permNos.isEmpty()) {
			menus =  mm.findByPermNos(permNos);
		}else {
			logger.info("permNos is null");
		}
		return menus;
	}
	
	@Override
	public JSONArray jsonMenu(List<String> permNos) {
		
		JSONArray array = new JSONArray();
		
		if(permNos == null || permNos.isEmpty()) {
			return array;
		}
		
		List<Menu> list = mm.findByPermNos(permNos);
		
		List<Menu> children = this.findChildren(list, "root");
		
		if(children != null && children.size() > 0) {
			
			for(int i = 0; i < children.size(); i++) {
				Menu m = children.get(i);
				JSONObject json = new JSONObject();
				json.put("path", m.getUrl());
				json.put("name", m.getMenuNo());
				JSONObject meta = new JSONObject();
				meta.put("icon", m.getIcon());
				meta.put("title", m.getMenuName());
				json.put("meta", meta);
				json.put("component", m.getComponent());
				
				JSONArray c = this.menus(list, m.getMenuNo());
				
				if(c != null && c.size() > 0)
					json.put("children", c);
				
				array.add(json);
			}
		}
		
		return array;
	}

	private List<Menu> findChildren(List<Menu> list,String menuNo){
		
		List<Menu> menus = new ArrayList<Menu>();
		
		if(StringUtils.isEmpty(menuNo)) {
			logger.info("menu no is null");
			return null;
		}
		
		if(list == null || list.size() == 0) {
			logger.info("list is null");
			return null;
		}
		
		for(int i = 0; i < list.size(); i++) {
			Menu m = list.get(i);
			String pno = m.getPno();
			
			if(menuNo.equals(pno))
				menus.add(m);
		}
		
		return menus;
	}
	
	private JSONArray menus4Add(List<Menu> list,String menuNo) {
		JSONArray array = new JSONArray();
		
		List<Menu> children = this.findChildren(list, menuNo);
		
		if(children != null && children.size() > 0) {
			
			for(int i = 0; i < children.size(); i++) {
				Menu m = children.get(i);
				JSONObject json = new JSONObject();
				json.put("path", m.getUrl());
				json.put("name", m.getMenuNo());
				json.put("title", m.getMenuName());
				json.put("expand", true);
				json.put("pno", m.getPno());
				
				JSONArray c = this.menus4Add(list, m.getMenuNo());
				
				if(c != null && c.size() > 0)
					json.put("children", c);
				
				array.add(json);
			}
		}

		return array;
	}
	
	private JSONArray menus(List<Menu> list,String menuNo) {
		JSONArray array = new JSONArray();
		
		List<Menu> children = this.findChildren(list, menuNo);
		
		if(children != null && children.size() > 0) {
			
			for(int i = 0; i < children.size(); i++) {
				Menu m = children.get(i);
				JSONObject json = new JSONObject();
				json.put("path", m.getUrl());
				json.put("name", m.getMenuNo());
				JSONObject meta = new JSONObject();
				meta.put("icon", m.getIcon());
				meta.put("title", m.getMenuName());
				json.put("meta", meta);
				json.put("component", m.getComponent());
				
				JSONArray c = this.menus(list, m.getMenuNo());
				
				if(c != null && c.size() > 0)
					json.put("children", c);
				
				array.add(json);
			}
		}

		return array;
	}
}
