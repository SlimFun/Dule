package cn.edu.dule.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Ignore;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.edu.dule.beans.BookInfo;
import cn.edu.dule.beans.User;
import cn.edu.dule.dao.BookInfoDao;

@Repository
public class BookInfoDaoImpl extends SuperDaoImpl<BookInfo> implements BookInfoDao{

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getFollowers(final int bookInfoId) {
		return (List<User>) getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				// TODO Auto-generated method stub
				return session.createQuery("select u from Student u join u.focusOnBooks books where books.id=" + bookInfoId).list();
			}
		});
	}

	
}
