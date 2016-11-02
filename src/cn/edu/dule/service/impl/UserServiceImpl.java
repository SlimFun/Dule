package cn.edu.dule.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import cn.edu.dule.beans.Admin;
import cn.edu.dule.beans.QueryResult;
import cn.edu.dule.beans.Student;
import cn.edu.dule.beans.User;
import cn.edu.dule.dao.UserDao;
import cn.edu.dule.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private UserDao userDao;

	@Override
	public User getUserById(int id) {
		return userDao.find(User.class, id);
	}

	@Override
	public void addUser(User user) {
		
	}

	public UserDao getUserDao() {
		return userDao;
	}

	@Resource(name="userDaoImpl")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public Admin getAdminById(int id) {
		// TODO Auto-generated method stub
		return userDao.findAdmin(id);
	}

	@Override
	public Student getStudentById(int id) {
		// TODO Auto-generated method stub
		return userDao.findStudent(id);
	}

	@Override
	public void updateStudent(Student user) {
		// TODO Auto-generated method stub
		userDao.update(user);
	}

	@Override
	public void registerUser(Student user) {
		// TODO Auto-generated method stub
		userDao.save(user);
	}

	@Override
	public Student getStudentByUserName(String userName) {
		// TODO Auto-generated method stub
		return userDao.findStudentByUserName(userName);
	}

	@Override
	public Student getStudentByEmail(String email) {
		// TODO Auto-generated method stub
		return userDao.findStudentByEmail(email);
	}

	@Override
	public void updateAdmin(Admin admin) {
		// TODO Auto-generated method stub
		userDao.update(admin);
	}

	@Override
	public void addAdmin(Admin admin) {
		// TODO Auto-generated method stub
		userDao.save(admin);
	}

	@Override
	public Admin getAdminByUserName(String userName) {
		// TODO Auto-generated method stub
		return userDao.findAdminByUserName(userName);
	}

	@Override
	public Admin getAdminByEmail(String email) {
		// TODO Auto-generated method stub
		return userDao.findAdminByEmail(email);
	}

	@Override
	public QueryResult<Admin> getAllAdmins() {
		// TODO Auto-generated method stub
		return userDao.getAllAdmins();
	}
	
}
