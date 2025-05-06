package com.fst.banque.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fst.banque.services.EmailService;

import jakarta.mail.MessagingException;

@RestController
public class UserController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public String registerUser(@RequestParam String email) throws MessagingException {
        try {
            emailService.sendEmail(
                email, 
                "Confirmation d'inscription", 
                "Bienvenue, votre inscription a été réussie !"
            );
            return "Utilisateur inscrit et email envoyé.";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Erreur lors de l'envoi de l'email.";
        }
    }
}
