package cn.edu.dule.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Account {
	private int id;
	private float money;
	private boolean frozen;
	private Student student;
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	
	public boolean isFrozen() {
		return frozen;
	}
	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}
	@OneToOne(mappedBy="account")
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
