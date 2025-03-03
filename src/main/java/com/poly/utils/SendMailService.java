package com.poly.utils;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.poly.models.OrderItem;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class SendMailService {
	 @Autowired
	    private JavaMailSender mailSender;

	    @Autowired
	    private TemplateEngine templateEngine;
	    public void sendCustomEmail(String to, String fullname, List<OrderItem> orderItems,List<String> values) {
	        try {
	        	  MimeMessage message = mailSender.createMimeMessage();
	              MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

	              Context context = new Context();
	              context.setVariable("orderItems",orderItems);
	              context.setVariable("fullname",fullname);
	              context.setVariable("values", values);
	              String htmlContent = templateEngine.process("email-template", context);
	              
	              helper.setTo(to);
	              helper.setSubject("Hóa đơn mua hàng");
	              helper.setText(htmlContent, true);
	              helper.setFrom("loctranhuu96@gmail.com"); // Thay thế bằng email thực tế của bạn

	              mailSender.send(message);
	              System.out.println("Email đã gửi thành công!");
	          } catch (MessagingException e) {
	              throw new RuntimeException("Lỗi khi gửi email: " + e.getMessage());
	          }
	    }
}
