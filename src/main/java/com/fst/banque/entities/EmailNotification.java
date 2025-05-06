package com.fst.banque.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class EmailNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destinataire;
    private String sujet;

    @Column(length = 1000)
    private String message;

    private Date dateEnvoi;

    // Constructeurs, getters et setters
    public EmailNotification() {}

    public EmailNotification(String destinataire, String sujet, String message, Date dateEnvoi) {
        this.destinataire = destinataire;
        this.sujet = sujet;
        this.message = message;
        this.dateEnvoi = dateEnvoi;
    }

    // Getters et setters ...
}

