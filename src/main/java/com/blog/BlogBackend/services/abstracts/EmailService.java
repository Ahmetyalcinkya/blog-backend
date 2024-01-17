package com.blog.BlogBackend.services.abstracts;

public interface EmailService {
    String sendEmail(String to, String subject, String body);
}
