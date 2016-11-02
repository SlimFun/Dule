package cn.edu.dule.dao.impl;

import org.junit.Test;

import cn.edu.dule.beans.Admin;
import cn.edu.dule.beans.Priority;

public class PriorityTest {
	@Test
	public void prioryTest(){
		Admin admin = new Admin();
		admin.addPriority(Priority.BORROW_BOOK);
		admin.addPriority(Priority.UPDATE_BOOK);
		System.out.println(admin.containPriority(Priority.ADD_BOOK));
		System.out.println(admin.containPriority(Priority.BORROW_BOOK));
		System.out.println(admin.containPriority(Priority.DELETE_BOOK));
		System.out.println(admin.containPriority(Priority.RETURN_BOOK));
		System.out.println(admin.containPriority(Priority.UPDATE_BOOK));
	}
}
