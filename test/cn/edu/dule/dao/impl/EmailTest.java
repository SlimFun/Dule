package cn.edu.dule.dao.impl;

import org.junit.Test;

import cn.edu.dule.beans.Mail;
import cn.edu.dule.utils.EmailUtil;

public class EmailTest {
	@Test
	public void test(){
		Mail mail = new Mail();
		mail.setSender("yunnandalifw@163.com");
		mail.setUsername("yunnandalifw@163.com");
		mail.setPassword("d199544l");
		mail.setHost("smtp.163.com");
		mail.setReceiver("1570233663@qq.com");
		mail.setSubject("Test");
		mail.setMessage("This is a test");
		EmailUtil.send(mail);
	}
}
