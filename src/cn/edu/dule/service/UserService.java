package cn.edu.dule.service;

import cn.edu.dule.beans.Admin;
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
}
