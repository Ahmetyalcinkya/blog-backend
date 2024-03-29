package com.blog.BlogBackend.services.concretes;

import com.blog.BlogBackend.exceptions.BlogException;
import com.blog.BlogBackend.services.abstracts.EmailService;
import com.blog.BlogBackend.utils.Constants;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailManager implements EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;
    private JavaMailSender javaMailSender;

    @Autowired
    public EmailManager(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public String sendEmail(String to, String subject, String body) {

        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body);

            javaMailSender.send(mimeMessage);
            return Constants.EMAIL_SENT;
        }catch (Exception ex){
            throw new BlogException(Constants.EMAIL_NOT_SENT, HttpStatus.BAD_REQUEST);
        }
    }
}
