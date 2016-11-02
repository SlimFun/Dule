package cn.edu.dule.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Admin extends User{
	
	private int priority = 0;

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public boolean containPriority(Priority priority){
		return (this.priority & (1 << priority.ordinal()))!=0;
	}
	
	public void addPriority(Priority priority){
		this.priority = this.priority | (1 << priority.ordinal());
	}
	
	public void resetPriorities(String[] priorities){
		int pri = 0;
		for(String priStr : priorities){
			Priority priority = Priority.valueOf(priStr);
			pri = pri | (1 << priority.ordinal());
		}
//		priority = priority & pri;
		priority = pri;
		addPriority(Priority.RETURN_BOOK);
		addPriority(Priority.BORROW_BOOK);
	}
	
	public List<Priority> generatePriorities(){
		List<Priority> priorities = new ArrayList<Priority>();
		for(Priority pri : Priority.values()){
			if(containPriority(pri)){
				priorities.add(pri);
			}
		}
		return priorities;
	}
	
	public boolean containPriority(String priStr){
		if(priStr == "MANAGE_BOOK"){
			return containPriority(Priority.DELETE_BOOK)||containPriority(Priority.UPDATE_BOOK)||containPriority(Priority.ADD_BOOK);
		}
		return containPriority(Priority.valueOf(priStr));
	}
//	private Set<Priority> priorities;
//
//	@ManyToMany(fetch=FetchType.EAGER)
//	@JoinTable(name="admin_priorities",
//		joinColumns={@JoinColumn(name="admin_id")},
//		inverseJoinColumns={@JoinColumn(name="priority_id")})
//	public Set<Priority> getPriorities() {
//		return priorities;
//	}
//
//	public void setPriorities(Set<Priority> priorities) {
//		this.priorities = priorities;
//	}
	
	
}
