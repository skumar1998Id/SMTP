package com.ascent.smtp;

import com.ascent.smtp.EmailBody.MessageBodyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;

@SpringBootApplication
public class SmtpApplication {

	@Autowired
	private MessageBodyServiceImpl messageBodyServiceImpl;


	public static void main(String[] args) {

		SpringApplication.run(SmtpApplication.class, args);
		System.out.println("Main class running");
	}
	@PostConstruct
	public void init() throws MessagingException {
		messageBodyServiceImpl.sendTestMail();
	}

}
