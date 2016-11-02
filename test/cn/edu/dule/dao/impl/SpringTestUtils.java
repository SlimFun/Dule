package cn.edu.dule.dao.impl;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.edu.dule.dao.BookTypeDao;

public class SpringTestUtils {
	
	public static ClassPathXmlApplicationContext getCtx(){
		try {
			return new ClassPathXmlApplicationContext("beans.xml");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
