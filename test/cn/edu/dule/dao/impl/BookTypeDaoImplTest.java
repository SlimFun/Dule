package cn.edu.dule.dao.impl;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.edu.dule.beans.BookType;
import cn.edu.dule.dao.BookTypeDao;

public class BookTypeDaoImplTest {

	private static BookTypeDao typeDao;
	
	private static ClassPathXmlApplicationContext ctx;
	
	@BeforeClass
	public static void setupBeforeClass(){
		try {
			ctx = new ClassPathXmlApplicationContext("beans.xml");
			typeDao = (BookTypeDao) ctx.getBean("bookTypeDaoImpl");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSave() {
		BookType type = new BookType();
		type.setName("�й���ѧ");
		type.setNote("�����й���ѧ���鼮");
		typeDao.save(type);
	}
	
	@Test
	public void testFind(){
		BookType type = typeDao.find(BookType.class, 1);
		System.out.println(type);
	}

}
