package cn.edu.dule.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import cn.edu.dule.utils.DateUtil;

import com.sun.istack.internal.Nullable;

@Entity
public class Message {

	private int id;
	private String header;
	private String content;
	private boolean hasRead = false;
	private Date date;
	private User user;
	private String timeAgo;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	
	@Type(type="text") 
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isHasRead() {
		return hasRead;
	}
	public void setHasRead(boolean hasRead) {
		this.hasRead = hasRead;
	}
	@ManyToOne
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Transient
	public String getTimeAgo() {
		return DateUtil.format(date);
	}
	
	public void setTimeAgo(String timeAgo) {
		this.timeAgo = timeAgo;
	}
	
}
