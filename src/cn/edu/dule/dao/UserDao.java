package cn.edu.dule.dao;

import java.util.List;

import cn.edu.dule.beans.Admin;
import cn.edu.dule.beans.QueryResult;
import cn.edu.dule.beans.Student;
import cn.edu.dule.beans.User;

public interface UserDao extends SuperDao<User>{
	Student findStudent(int id);
	Admin findAdmin(int id);
	Student findStudentByUserName(String userName);
	Student findStudentByEmail(String email);
	Admin findAdminByUserName(String userName);
	Admin findAdminByEmail(String email);
	QueryResult<Admin> getAllAdmins();
	List<Admin> getAllSuperAdmins();
//	void addStudent(Student student);
//	void addAdmin(Admin admin);
}
