package com.ocrs.pojo;

import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;


@Component
public class MailMail
{
	Properties properties = new Properties();
	{
		
		properties.put("mail.smtp.starttls.enable", "true");


		//properties.put("mail.smtp.starttls.enable",mailAccount.isStartTls());
	}
	public void sendMail(String firstName,String to,String password) {
		SimpleMailMessage mailMessage=new SimpleMailMessage();
		JavaMailSenderImpl mailSender=new JavaMailSenderImpl();


		mailSender.setPort(587);
		mailSender.setHost("smtp.gmail.com");
		mailSender.setUsername("username");
		mailSender.setPassword("password");
		mailSender.setJavaMailProperties(properties);
		//mailSender.
		String content="Hi "+firstName+"\n"+"Your password: "+password;
		mailMessage.setText(content);
		mailMessage.setTo(to);
		mailMessage.setFrom("noReply@gmail.com");
		mailMessage.setSubject("password recovery");

		mailSender.send(mailMessage);
	}


}
