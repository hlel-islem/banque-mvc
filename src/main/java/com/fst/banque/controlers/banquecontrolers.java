package com.fst.banque.controlers;

import com.fst.banque.entities.compte;
import com.fst.banque.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class banquecontrolers {
	
    @Autowired
    private CompteRepository compteRepository;

    @GetMapping("/comptes")
    public String listeComptes(Model model) {
        model.addAttribute("comptes", compteRepository.findAll());
        return "listeComptes";
    }

    @GetMapping("/ajouter")
    public String formAjout(Model model) {
        model.addAttribute("compte", new compte());
        return "ajouterCompte";
    }

    @PostMapping("/ajouter")
    public String ajouter(@RequestParam String titulaire, @RequestParam double solde) {
        compte c = new compte();
        c.setTitulaire(titulaire);
        c.setSolde(solde);
        compteRepository.save(c);
        return "redirect:/comptes";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable int id, Model model) {
        Optional<compte> optionalCompte = compteRepository.findById(id);
        optionalCompte.ifPresent(c -> model.addAttribute("compte", c));
        return "detailsCompte";
    }

    @PostMapping("/depot/{id}")
    public String depot(@PathVariable int id, @RequestParam double montant) {
        Optional<compte> optionalCompte = compteRepository.findById(id);
        if (optionalCompte.isPresent() && montant > 0) {
            compte c = optionalCompte.get();
            c.setSolde(c.getSolde() + montant);
            compteRepository.save(c);
        }
        return "redirect:/details/" + id;
    }

    @PostMapping("/retrait/{id}")
    public String retrait(@PathVariable int id, @RequestParam double montant) {
        Optional<compte> optionalCompte = compteRepository.findById(id);
        if (optionalCompte.isPresent()) {
            compte c = optionalCompte.get();
            if (montant > 0 && c.getSolde() >= montant) {
                c.setSolde(c.getSolde() - montant);
                compteRepository.save(c);
            }
        }
        return "redirect:/details/" + id;
    }
}