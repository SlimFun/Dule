package cn.edu.dule.dao.impl;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sun.xml.internal.ws.client.Stub;

import cn.edu.dule.beans.Account;
import cn.edu.dule.beans.Admin;
import cn.edu.dule.beans.Book;
import cn.edu.dule.beans.BookInfo;
import cn.edu.dule.beans.Message;
import cn.edu.dule.beans.Priority;
import cn.edu.dule.beans.QueryResult;
import cn.edu.dule.beans.Student;
import cn.edu.dule.beans.User;
import cn.edu.dule.beans.UserBorrowedBook;
import cn.edu.dule.beans.WhereJPQL;
import cn.edu.dule.dao.AccountDao;
import cn.edu.dule.dao.BookDao;
import cn.edu.dule.dao.BookInfoDao;
import cn.edu.dule.dao.BookTypeDao;
import cn.edu.dule.dao.MessageDao;
import cn.edu.dule.dao.UserBorBookDao;
import cn.edu.dule.dao.UserDao;
import cn.edu.dule.exception.DataNotExistException;

public class UserDaoImplTest {

	private static UserDao userDao;
	private static BookDao bookDao;
	private static BookInfoDao bookInfoDao;
	private static UserBorBookDao ubbDao;
	private static AccountDao accountDao;
	private static MessageDao messageDao;
	
	private static ClassPathXmlApplicationContext ctx;
	
	@BeforeClass
	public static void setupBeforeClass(){
		ctx = SpringTestUtils.getCtx();
		userDao = (UserDao) ctx.getBean("userDaoImpl");
		bookDao = (BookDao) ctx.getBean("bookDaoImpl");
		bookInfoDao = (BookInfoDao) ctx.getBean("bookInfoDaoImpl");
		ubbDao = (UserBorBookDao) ctx.getBean("userBorBookDaoImpl");
		accountDao = (AccountDao) ctx.getBean("accountDaoImpl");
		messageDao = (MessageDao) ctx.getBean("messageDaoImpl");
	}
	
//	@Test
//	public void testSave() {
//		Student stu = new Student();
//		stu.setPassword("5465");
//		stu.setEmail("slimfun@qq.com");
//		Set<Book> borrowedBooks = new HashSet<Book>();
//		borrowedBooks.add(bookDao.find(Book.class, 1));
//		stu.setBorrowedBooks(borrowedBooks);
//		userDao.save(stu);
//	}
	
	@Test
	public void testBorrowBook(){
//		Student stu = userDao.findStudent(1);
//		System.out.println(stu);
//		Book book = bookDao.find(Book.class, 7);
//		System.out.println(book);
//		UserBorrowedBook ubb = new UserBorrowedBook();
//		ubb.setUser(stu);
//		ubb.setBook(book);
		Student stu = new Student();
		stu.setId(1);
		Book book = bookDao.find(Book.class, 7);
		book.setBorrowedStu(stu);
		bookDao.update(book);
	}
	
	@Test
	public void testGetBorrowedBooks(){
		Student stu = userDao.findStudent(1);
		System.out.println(stu.getBorrowedBooks());
		
	}
	
	@Test
	public void testGetUserByName(){
		Student stu = userDao.findStudentByUserName("haha");
		System.out.println(stu);
	}
	
	@Test
	public void testGetUserByEmail(){
		Student stu = userDao.findStudentByEmail("haha@gmail.com");
		System.out.println(stu);
	}
	
	@Test
	public void testReturnBook(){
		Book book = bookDao.find(Book.class, 7);
		book.setBorrowedStu(null);
		bookDao.update(book);
	}
	
	@Test
	public void testSaveAdmin(){
		Admin admin = new Admin();
		admin.setUsername("slimfun");
		admin.setPassword("123456");
		admin.setEmail("haha@google.com");
		userDao.save(admin);
	}
	
	@Test
	public void testGetPriorities(){
		Admin admin = userDao.findAdmin(3);
		for(Priority pri : admin.generatePriorities()){
//			System.out.println(pri.getValue());
		}
	}
	
	@Test
	public void testAddPriorities(){
		Admin admin = userDao.findAdmin(3);
		admin.addPriority(Priority.SUPER_PRIORITY);
		userDao.update(admin);
	}
	
	@Test
	public void focuseBookTest(){
		Student stu = (Student) userDao.find(User.class, 2);
		Set<BookInfo> focusBooks = new HashSet<BookInfo>();
		focusBooks.add(bookInfoDao.find(BookInfo.class, 1));
		stu.setFocusOnBooks(focusBooks);
		userDao.update(stu);
	}
	
	@Test
	public void testFind(){
		Student stu = (Student) userDao.find(User.class, 1);
		System.out.println(stu);
	}
	
	@Test
	public void testAccount(){
		Account account = accountDao.find(Account.class, 1);
		System.out.println(account.isFrozen());
	}
	
	@Test
	public void testMessageAdd(){
		Message msg = new Message();
		msg.setHeader("test");
		msg.setContent("This is a test");
		messageDao.save(msg);
	}
	
	@Test
	public void testMessage(){
		Message msg = messageDao.find(Message.class, 1);
		User user = userDao.find(User.class, 1);
		List<Message> messages = new LinkedList<Message>();
		messages.add(msg);
		msg.setUser(user);
		messageDao.update(msg);
//		user.setMessages(messages);
		userDao.update(user);
	}
}
