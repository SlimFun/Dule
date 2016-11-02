package cn.edu.dule.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.edu.dule.beans.Admin;
import cn.edu.dule.beans.QueryResult;
import cn.edu.dule.beans.Student;
import cn.edu.dule.beans.User;
import cn.edu.dule.dao.UserDao;

@Repository
public class UserDaoImpl extends SuperDaoImpl<User> implements UserDao {

	@Override
	public Student findStudent(int id) {
		// TODO Auto-generated method stub
		return (Student) getHibernateTemplate().get(Student.class, id);
	}

	@Override
	public Admin findAdmin(int id) {
		// TODO Auto-generated method stub
		return (Admin) getHibernateTemplate().get(Admin.class, id);
	}

	@Override
	public Student findStudentByUserName(final String userName) {
		// TODO Auto-generated method stub
		return (Student) hibernateTemplate.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				return session.createQuery("select o from Student o where username='" + userName + "'")
						.uniqueResult();
			}
		});
	}

	@Override
	public Student findStudentByEmail(final String email) {
		// TODO Auto-generated method stub
		return (Student) hibernateTemplate.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				return session.createQuery("select o from Student o where email='" + email + "'")
						.uniqueResult();
			}
		});
	}

	@Override
	public Admin findAdminByUserName(final String userName) {
		// TODO Auto-generated method stub
		return (Admin) hibernateTemplate.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				return session.createQuery("select o from Admin o where username='" + userName + "'")
						.uniqueResult();
			}
		});
	}

	@Override
	public Admin findAdminByEmail(final String email) {
		// TODO Auto-generated method stub
		return (Admin) hibernateTemplate.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				return session.createQuery("select o from Admin o where email='" + email + "'")
						.uniqueResult();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<Admin> getAllAdmins() {
		// TODO Auto-generated method stub
		QueryResult<Admin> result = new QueryResult<Admin>();
		List<Admin> data = (List<Admin>) hibernateTemplate.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery("select o from Admin o");
				return query.list();
			}
		});
		result.setResultList(data);
		Long totalRecord = (Long) hibernateTemplate.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				return session.createQuery("select count(o) from Admin o")
						.uniqueResult();
			}
		});
		result.setTotalRecord(totalRecord);
		return result;
	}

//	@Override
//	public void addStudent(Student student) {
//		// TODO Auto-generated method stub
//		getHibernateTemplate().save(student);
//	}
//
//	@Override
//	public void addAdmin(Admin admin) {
//		// TODO Auto-generated method stub
//		getHibernateTemplate().save(admin);
//	}

}
