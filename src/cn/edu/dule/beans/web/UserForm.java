package cn.edu.dule.beans.web;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import cn.edu.dule.beans.Student;

public class UserForm {
	private int id;
	private String username;
	private String password;
	private String resumePassword;
	private String email;
	private String profile;
	private String phone;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(nullable=false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getResumePassword() {
		return resumePassword;
	}
	public void setResumePassword(String resumePassword) {
		this.resumePassword = resumePassword;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Student generateStu(){
		Student stu = new Student();
		stu.setEmail(email);
		stu.setPassword(password);
		stu.setPhone(phone);
		stu.setProfile(profile);
		stu.setUsername(username);
		return stu;
	}
}
