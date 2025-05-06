package com.fst.banque.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    private double montant;

    private String type; // "DEPOT" ou "RETRAIT"

    @ManyToOne
    @JoinColumn(name = "compte_id") // FK vers compte
    private compte compte;

    public Operation() {}

    public Operation(Date date, double montant, String type, compte compte) {
        this.date = date;
        this.montant = montant;
        this.type = type;
        this.compte = compte;
    }

    // Getters & setters
    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public double getMontant() {
        return montant;
    }

    public String getType() {
        return type;
    }

    public compte getCompte() {
        return compte;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCompte(compte compte) {
        this.compte = compte;
    }
}

