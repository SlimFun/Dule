package cn.edu.dule.service;

import java.util.List;

import cn.edu.dule.beans.Admin;
import cn.edu.dule.beans.Message;
import cn.edu.dule.beans.QueryResult;
import cn.edu.dule.beans.Student;
import cn.edu.dule.beans.User;

public interface UserService {
	User getUserById(int id);
	void addUser(User user);
	Admin getAdminById(int id);
	Admin getAdminByUserName(String userName);
	Admin getAdminByEmail(String email);
	Student getStudentById(int id);
	Student getStudentByUserName(String userName);
	Student getStudentByEmail(String email);
	void updateStudent(Student user);
	void registerUser(Student user);
	void updateAdmin(Admin admin);
	void addAdmin(Admin admin);
	QueryResult<Admin> getAllAdmins();
	void focusBook(Student user, int bookInfoId);
	void cancelFocus(Student user, int bookInfoId);
	List<Admin> getAllSuperAdmins();
	void sendMessages(Message message);
	void updateMessage(Message message);
	Message getMessage(int id);
}
