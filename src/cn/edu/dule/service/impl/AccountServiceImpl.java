package cn.edu.dule.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.dule.beans.Account;
import cn.edu.dule.beans.Student;
import cn.edu.dule.dao.AccountDao;
import cn.edu.dule.dao.UserDao;
import cn.edu.dule.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{
	
	private UserDao userDao;
	private AccountDao accountDao;

	@Override
	public void addAccount(int userId, Account account) {
		// TODO Auto-generated method stub
		accountDao.save(account);
		Student user = userDao.findStudent(userId);
		user.setAccount(account);
		userDao.update(user);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	@Resource(name="userDaoImpl")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public AccountDao getAccountDao() {
		return accountDao;
	}

	@Resource(name="accountDaoImpl")
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	
}
