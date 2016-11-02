package cn.edu.dule.dao.impl;

import java.util.Map;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.junit.Test;

import cn.edu.dule.beans.BookType;

public class JSONTest {
	@Test
	public void jsonTest(){
		BookType bookType = new BookType();
		bookType.setId(1);
		bookType.setName("haha");
		bookType.setNote("This is a not");
		try {
			String jsonStr = JSONUtil.serialize(bookType);
			System.out.println(jsonStr);
			Map map = (Map) JSONUtil.deserialize(jsonStr);
			System.out.println(map);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
}
