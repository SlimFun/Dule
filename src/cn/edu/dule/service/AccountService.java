package cn.edu.dule.service;

import cn.edu.dule.beans.Account;

public interface AccountService {
	void addAccount(int userId, Account account);
	void frozenAccount(int id);
	void activeAccount(int id);
}
