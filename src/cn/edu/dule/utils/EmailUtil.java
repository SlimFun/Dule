package cn.edu.dule.utils;

import org.apache.commons.mail.HtmlEmail;

import cn.edu.dule.beans.Mail;

public class EmailUtil {
	public static void send(Mail mail){
        HtmlEmail email = new HtmlEmail();  
        try {  
            email.setHostName(mail.getHost());  
            email.setCharset(Mail.ENCODEING);  
            email.addTo(mail.getReceiver());  
            email.setFrom(mail.getSender(), mail.getName());  
            email.setAuthentication(mail.getUsername(), mail.getPassword());  
            email.setSubject(mail.getSubject());  
            email.setMsg(mail.getMessage());  
            email.send();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}  
}
