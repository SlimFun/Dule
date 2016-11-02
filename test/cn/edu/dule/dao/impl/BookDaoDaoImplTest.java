package cn.edu.dule.dao.impl;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.edu.dule.beans.Book;
import cn.edu.dule.beans.BookInfo;
import cn.edu.dule.beans.Book.Status;
import cn.edu.dule.beans.BookType;
import cn.edu.dule.dao.BookDao;
import cn.edu.dule.dao.BookInfoDao;
import cn.edu.dule.dao.BookTypeDao;
import cn.edu.dule.service.BookService;

public class BookDaoDaoImplTest {
	
	private static BookDao bookDao;
	private static BookTypeDao typeDao;
	private static BookInfoDao bookInfoDao;
	
	private static ClassPathXmlApplicationContext ctx;
	
	@BeforeClass
	public static void setupBeforeClass(){
		try {
			ctx = new ClassPathXmlApplicationContext("beans.xml");
			bookDao = (BookDao) ctx.getBean("bookDaoImpl");
			typeDao = (BookTypeDao) ctx.getBean("bookTypeDaoImpl");
			bookInfoDao = (BookInfoDao) ctx.getBean("bookInfoDaoImpl");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSave() {
		BookInfo bookInfo = new BookInfo();
		bookInfo.setName("悟空传");
		bookInfo.setAuthor("今何在");
		bookInfo.setPublisher("光明日报出版社");
		bookInfo.setDateOfPub(new Date());
		bookInfoDao.save(bookInfo);
		Book book = new Book();
		book.setBookInfo(bookInfo);
		book.setPosition("D205");
		book.setStatus(Status.ON_BOARD);
		bookDao.save(book);
	}
	
	@Test
	public void testUpdate(){
		Book book = bookDao.find(Book.class, 5);
		book.setPosition("D208");
		bookDao.update(book);
	}

	@Test
	public void testUpdate2(){
		Book book = bookDao.find(Book.class, 5);
		BookInfo info = new BookInfo();
		info.setName("肖肖");
		info.setAuthor("test");
		info.setPublisher("Publisher");
		bookInfoDao.save(info);
		book.setBookInfo(info);
		bookDao.update(book);
	}
	
	@Test
	public void testDelete(){
		Book book = new Book();
		BookInfo info = new BookInfo();
		info.setId(10);
		book.setBookInfo(info);
		bookDao.delete(book);
		System.out.println("Success");
	}

	@Test
	public void testFind(){
		Book book = bookDao.find(Book.class, 2);
		System.out.println(book);
	}
	
	@Test
	public void delete(){
		Book book = new Book();
		book.setId(7);
		bookDao.delete(book);
	}
}
