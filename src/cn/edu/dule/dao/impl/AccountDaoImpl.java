package cn.edu.dule.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.dule.beans.Account;
import cn.edu.dule.beans.Book;
import cn.edu.dule.dao.AccountDao;

@Repository
public class AccountDaoImpl extends  SuperDaoImpl<Account> implements AccountDao{

}
