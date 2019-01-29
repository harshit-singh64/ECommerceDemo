package com.example.demo.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.User;
import com.example.demo.repo.IUserRepo;

@Component
public class EmailService {
	@Autowired
	private IUserRepo userRepo;
	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String emailFrom;

	public void sendMail(String userName, String password, String name, Integer id)
			throws UnsupportedEncodingException {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setTo(userName);
			helper.setFrom(new InternetAddress(emailFrom, "E-Commerce"));
			helper.setSubject("E-Commerce Registration");

			message.setContent("<h1>Registeration Successfull !</h1>" + "YOUR ACCOUNT IS READY<br><br><br>Hello " + name
					+ "   ,<br><br>" + "Thank You for registering in E-Commerce where you can spread your"
					+ " buissness in every corner of the country. Below are your" + " credentials for login."
					+ "<br><br>        Username is :   " + userName + "<br><br>        Password is :   " + password
					+ "<br><br><body><a href=http://localhost:8083/api/activateAccount/" + id
					+ ">Click here to Activate Your Account</a></body>", "text/html");

		} catch (MessagingException e) {
			System.out.println("Error while sending mail ..");
		}

		mailSender.send(message);
		System.out.println("Mail Sent Successfully!");
	}

	/* account activation */

	public void activation(Integer id) {
		User user = userRepo.findById(id).get();
		// user.setStatus((byte) 1);
		userRepo.save(user);
	}
}
