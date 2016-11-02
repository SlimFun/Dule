package cn.edu.dule.service;

import java.util.List;

import cn.edu.dule.beans.Book;
import cn.edu.dule.beans.Book.Status;
import cn.edu.dule.beans.BookInfo;
import cn.edu.dule.beans.BookType;
import cn.edu.dule.beans.QueryResult;
import cn.edu.dule.beans.Student;
import cn.edu.dule.beans.User;
import cn.edu.dule.exception.DataNotExistException;

public interface BookService{
	void addBook(Book book);
	
	List<BookType> getBookTypes();
	
	QueryResult<BookInfo> getBookInfos();
	
	QueryResult<BookInfo> getBookInfos(int firstIndex, int maxResult);
	
	QueryResult<Book> getBooksOfOneBookInfo(int bookInfoId);
	
	QueryResult<Book> getBooksOfOneBookInfo(BookInfo bookInfo);
	
	int getNumberOfBookOfOneBookInfo(int bookInfoId);
	
	int getNumberOfBookOfOneBookInfo(int bookInfoId,Status status);
	
	BookInfo getBookInfo(int bookInfoId);
	
	BookInfo getBookInfo(BookInfo info);
	
	void addBookInfo(BookInfo info);
	
	Book getBook(int id);
	
	void update(Book book);
	
	void update(BookInfo info);
	
	void delete(Book book);
	
	void delete(BookInfo info);
	
	void deleteBookInfo(int infoId);
	
	void deleteBooksOfOneInfo(int infoId);
	
	QueryResult<BookInfo> searchBookInfo(String bookName);
	
	void borrowBook(int userId, int bookId);
	
	void borrowBook(Student user, Book book);
	
	void returnBook(int bookId) throws DataNotExistException;
	
	void returnBook(Book book);
	
	QueryResult<Book> getBorrowedBooks(int userId, int firstIndex, int maxResult);
}
