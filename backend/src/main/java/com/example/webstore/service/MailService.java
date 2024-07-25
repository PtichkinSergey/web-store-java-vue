package com.example.webstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Сервис для отправки сообщений на почту пользователя
 * Внедряемые зависимости: 
 * mailSender - объект для отправки почты
 */
@Service
public class MailService {
    private final MailSender mailSender;
    @Value("${spring.mail.username}")
    private String addresser;

    @Autowired
    public MailService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Метод отправки сообщения на почту пользователя, указанной при регистрации
     * Принимает токен аутентификации и текст сообщения
     */
    public void sendMail(Authentication authentication, String message) throws MailException{
        final SimpleMailMessage simpleMail = new SimpleMailMessage();
        simpleMail.setFrom(addresser);
        simpleMail.setTo(authentication.getName());
        simpleMail.setSubject("Заказ в интернет магазине");
        simpleMail.setText(message);
        this.mailSender.send(simpleMail);
    }
}
