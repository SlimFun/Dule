package cn.edu.dule.beans;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Book {
	private int id;
	private BookInfo bookInfo;
	private Status status;
	private String position;
	private Student borrowedStu;
	private Date borrowedDate;
	private int leftDays;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(nullable=false)
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Column(nullable=false)
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public enum Status{
		BORROWED,ON_BOARD
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="book_info_id")
	public BookInfo getBookInfo() {
		return bookInfo;
	}

	public void setBookInfo(BookInfo bookInfo) {
		this.bookInfo = bookInfo;
	}
	
	@Override
	public String toString() {
		return String.format("Book:id=%d,bookInfo=%s,status=%s,position=%s", id, bookInfo, status, position);
	}

	@ManyToOne
	public Student getBorrowedStu() {
		return borrowedStu;
	}

	public void setBorrowedStu(Student borrowedStu) {
		this.borrowedStu = borrowedStu;
	}

	public Date getBorrowedDate() {
		return borrowedDate;
	}

	public void setBorrowedDate(Date borrowedDate) {
		this.borrowedDate = borrowedDate;
	}

	@Transient
	public int getLeftDays() {
		return leftDays;
	}

	public void setLeftDays(int leftDays) {
		this.leftDays = leftDays;
	}
	
}
