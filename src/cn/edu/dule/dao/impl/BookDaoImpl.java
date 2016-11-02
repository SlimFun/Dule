package cn.edu.dule.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.edu.dule.beans.Book;
import cn.edu.dule.dao.BookDao;

@Repository
public class BookDaoImpl extends SuperDaoImpl<Book> implements BookDao {
	
}
