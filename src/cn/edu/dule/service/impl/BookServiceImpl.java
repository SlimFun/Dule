package cn.edu.dule.service.impl;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.dule.beans.Book;
import cn.edu.dule.beans.Book.Status;
import cn.edu.dule.beans.BookInfo;
import cn.edu.dule.beans.BookType;
import cn.edu.dule.beans.Message;
import cn.edu.dule.beans.QueryResult;
import cn.edu.dule.beans.Student;
import cn.edu.dule.beans.User;
import cn.edu.dule.beans.UserBorrowedBook;
import cn.edu.dule.beans.WhereJPQL;
import cn.edu.dule.dao.BookDao;
import cn.edu.dule.dao.BookInfoDao;
import cn.edu.dule.dao.BookTypeDao;
import cn.edu.dule.dao.MessageDao;
import cn.edu.dule.dao.UserBorBookDao;
import cn.edu.dule.dao.UserDao;
import cn.edu.dule.exception.DataNotExistException;
import cn.edu.dule.service.BookService;

@Service
public class BookServiceImpl implements BookService{
	
	private BookDao bookDao;
	private BookInfoDao bookInfoDao;
	private BookTypeDao bookTypeDao;
	private UserBorBookDao ubbDao;
	private UserDao userDao;
	private MessageDao messageDao;

	@Override
	public void addBook(Book book) {
		bookDao.save(book);
	}

	public BookDao getBookDao() {
		return bookDao;
	}

	@Resource(name="bookDaoImpl")
	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public BookInfoDao getBookInfoDao() {
		return bookInfoDao;
	}

	@Resource(name="bookInfoDaoImpl")
	public void setBookInfoDao(BookInfoDao bookInfoDao) {
		this.bookInfoDao = bookInfoDao;
	}
	
	public MessageDao getMessageDao() {
		return messageDao;
	}

	@Resource(name="messageDaoImpl")
	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	@Resource(name="userDaoImpl")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public QueryResult<BookInfo> getBookInfos() {
		return bookInfoDao.getScrollData(BookInfo.class);
	}

	@Override
	public QueryResult<BookInfo> getBookInfos(int firstIndex, int maxResult) {
		return bookInfoDao.getScrollData(BookInfo.class, firstIndex, maxResult);
	}

	@Override
	public QueryResult<Book> getBooksOfOneBookInfo(int bookInfoId) {
		return bookDao.getScrollData(Book.class, new WhereJPQL().addEqual("book_info_id", bookInfoId));
	}

	@Override
	public QueryResult<Book> getBooksOfOneBookInfo(BookInfo bookInfo) {
		return bookDao.getScrollData(Book.class, new WhereJPQL().addEqual("book_info_id", bookInfo.getId()));
	}

	@Override
	public int getNumberOfBookOfOneBookInfo(int bookInfoId) {
		return (int) bookDao.getNumberOfData(Book.class, new WhereJPQL().addEqual("book_info_id", bookInfoId));
	}

	@Override
	public int getNumberOfBookOfOneBookInfo(int bookInfoId, Status status) {
		return (int) bookDao.getNumberOfData(Book.class, new WhereJPQL().addEqual("status", status.ordinal()).addEqual("book_info_id", bookInfoId));
	}

	@Override
	public BookInfo getBookInfo(int bookInfoId) {
		return bookInfoDao.find(BookInfo.class, bookInfoId);
	}
	
	@Override
	public List<BookType> getBookTypes() {
		return bookTypeDao.getScrollData(BookType.class).getResultList();
	}

	public BookTypeDao getBookTypeDao() {
		return bookTypeDao;
	}

	@Resource(name="bookTypeDaoImpl")
	public void setBookTypeDao(BookTypeDao bookTypeDao) {
		this.bookTypeDao = bookTypeDao;
	}

	@Override
	public BookInfo getBookInfo(BookInfo info) {
		List<BookInfo> results = bookInfoDao.getScrollData(BookInfo.class,
											new WhereJPQL().addEqual("name", info.getName()).addEqual("author", info.getAuthor()).addEqual("publisher", info.getPublisher())).getResultList();
		return (results==null||results.size()==0)?null:results.get(0);
	}

	@Override
	public void addBookInfo(BookInfo info) {
		bookInfoDao.save(info);
	}

	@Override
	public Book getBook(int id) {
		return bookDao.find(Book.class, id);
	}

	@Override
	public void update(Book book) {
		// TODO Auto-generated method stub
		bookDao.update(book);
	}

	@Override
	public void delete(Book book) {
		bookDao.delete(book);
	}

	@Override
	public void update(BookInfo info) {
		bookInfoDao.update(info);
	}

	@Override
	public void delete(BookInfo info) {
		// TODO Auto-generated method stub
		bookInfoDao.delete(info);
	}

	@Override
	public void deleteBookInfo(int infoId) {
		// TODO Auto-generated method stub
		bookInfoDao.delete(BookInfo.class, infoId);
	}

	@Override
	public void deleteBooksOfOneInfo(int infoId) {
		// TODO Auto-generated method stub
		List<Book> books = bookDao.getScrollData(Book.class, new WhereJPQL().addEqual("book_info_id", infoId)).getResultList();
		bookDao.deleteEntities(books);
	}

	@Override
	public QueryResult<BookInfo> searchBookInfo(String infoName) {
		return bookInfoDao.getScrollData(BookInfo.class, new WhereJPQL().addEqual("name", infoName));
	}

	@Override
	public void borrowBook(int userId, int bookId) {
		// TODO Auto-generated method stub
		Student user = new Student();
		user.setId(userId);
		Book book = new Book();
		book.setId(bookId);
		borrowBook(user, book);
	}

	@Override
	public void borrowBook(Student user, Book book) {
		// TODO Auto-generated method stub
		book = bookDao.find(Book.class, book.getId());
		sendMessages(user,book);
		if(book != null){
			book.setBorrowedStu(user);
			book.setStatus(Status.BORROWED);
			book.setBorrowedDate(new Date());
			bookDao.update(book);
		}else{
			throw new DataNotExistException();
		}
	}

	private void sendMessages(Student user, Book book) {
		// TODO Auto-generated method stub
		List<User> users = bookInfoDao.getFollowers(book.getBookInfo().getId());
		Message msg = new Message();
		Date date = new Date();
		DateFormat format=DateFormat.getDateInstance(DateFormat.FULL,Locale.US);
		String str=format.format(date);
		msg.setHeader("A book you focused has bean borrowed.");
		msg.setContent("The following book has bean borrowed:<br/>" +
						"book name:" + book.getBookInfo().getName() + "<br/>" +
						"book id:" + book.getBookInfo().getId() + "<br/>" +
						"Borrower name:" + user.getUsername() + "<br/>" +
						"Borrower id" + user.getId() + "<br/>" +
						"Borrowed Date:" + str);
		msg.setDate(date);
		for(User u : users){
			u.getMessages().add(msg);
			msg.setUser(u);
			messageDao.save(msg);
			//userDao.update(u);
		}
		sendEmails(users, msg);
	}

	private void sendEmails(List<User> users, Message msg) {
		// TODO Auto-generated method stub
		
	}

	public UserBorBookDao getUbbDao() {
		return ubbDao;
	}

	public void setUbbDao(UserBorBookDao ubbDao) {
		this.ubbDao = ubbDao;
	}

	@Override
	public void returnBook(int bookId) throws DataNotExistException {
		// TODO Auto-generated method stub
		Book book = new Book();
		book.setId(bookId);
		returnBook(book);
	}

	@Override
	public void returnBook(Book book) {
		// TODO Auto-generated method stub
		book = bookDao.find(Book.class, book.getId());
		if(book != null){
			book.setBorrowedStu(null);
			book.setStatus(Status.ON_BOARD);
			bookDao.update(book);
		}else{
			throw new DataNotExistException();
		}
		
	}

	@Override
	public QueryResult<Book> getBorrowedBooks(int userId, int firstIndex, int maxResult) {
		// TODO Auto-generated method stub
		return bookDao.getScrollData(Book.class, firstIndex, maxResult, new WhereJPQL().addEqual("borrowedStu_id", userId));
	}

}
