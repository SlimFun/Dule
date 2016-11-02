package cn.edu.dule.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserBorrowedBook {
	private int id;
	private Student user;
	private Book book;
	private Date borrowedTime;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	public Student getUser() {
		return user;
	}
	public void setUser(Student user) {
		this.user = user;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="book_id")
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Date getBorrowedTime() {
		return borrowedTime;
	}
	public void setBorrowedTime(Date borrowedTime) {
		this.borrowedTime = borrowedTime;
	}
}
