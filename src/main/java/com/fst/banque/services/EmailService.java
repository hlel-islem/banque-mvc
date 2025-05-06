package com.fst.banque.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fst.banque.entities.EmailNotification;
import com.fst.banque.repositories.EmailNotificationRepository;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

        @Autowired
        private EmailNotificationRepository notificationRepository;

        public void envoyerEmail(String to, String subject, String text) {
            // Envoi réel
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);

            // Sauvegarde dans la base
            EmailNotification notification = new EmailNotification(to, subject, text, new Date());
            notificationRepository.save(notification);
        }
    }


