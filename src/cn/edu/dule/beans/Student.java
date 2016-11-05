package cn.edu.dule.beans;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;

@Entity
public class Student extends User{
	private Set<BookInfo> focusOnBooks;
	private Set<Book> borrowedBooks;
	private Account account;
//	private Set<UserBorrowedBook> borrowedBooks;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="focusBooks",
				joinColumns={@JoinColumn(name="student_id")},
				inverseJoinColumns={@JoinColumn(name="book_info_id")})
	public Set<BookInfo> getFocusOnBooks() {
		return focusOnBooks;
	}

	public void setFocusOnBooks(Set<BookInfo> focusOnBooks) {
		this.focusOnBooks = focusOnBooks;
	}

	@OneToMany(fetch=FetchType.EAGER,mappedBy="borrowedStu")
	public Set<Book> getBorrowedBooks() {
		return borrowedBooks;
	}

	public void setBorrowedBooks(Set<Book> borrowedBooks) {
		this.borrowedBooks = borrowedBooks;
	}

	@Override
	public String toString() {
		return String.format("Student:id=%d, password=%s, email=%s, focusOnBooks=%s, borrowedBooks=%s, messages=%s", id, password, email, focusOnBooks, borrowedBooks, messages );
	}

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="account_id")
	public Account getAccount() {
		return account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}

//	@OneToMany(fetch=FetchType.EAGER,mappedBy="user")
//	public Set<UserBorrowedBook> getBorrowedBooks() {
//		return borrowedBooks;
//	}
//
//	public void setBorrowedBooks(Set<UserBorrowedBook> borrowedBooks) {
//		this.borrowedBooks = borrowedBooks;
//	}
}
