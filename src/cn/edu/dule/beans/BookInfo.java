package cn.edu.dule.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class BookInfo {
	private int id;
	private String name;
	private String author;
	private String publisher;
	private Date dateOfPub;
	private BookType type;
	private String img;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(length=256,nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable=false)
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(nullable=false)
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getDateOfPub() {
		return dateOfPub;
	}

	public void setDateOfPub(Date dateOfPub) {
		this.dateOfPub = dateOfPub;
	}
	
	@OneToOne
	public BookType getType() {
		return type;
	}

	public void setType(BookType type) {
		this.type = type;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("BookInfo:id=%d,name=%s,author=%s,publisher=%s,dateOfPub=%s,type=%s,img=%s",id,name,author,publisher,dateOfPub,type,img);
	}
}
