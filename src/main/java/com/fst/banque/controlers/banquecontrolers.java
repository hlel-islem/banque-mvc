package com.fst.banque.controlers;

import com.fst.banque.entities.Operation;
import com.fst.banque.entities.compte;
import com.fst.banque.repositories.CompteRepository;
import com.fst.banque.repositories.OperationRepository;
import com.fst.banque.services.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.Optional;

@Controller
public class banquecontrolers {

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private EmailService emailService;
    // Liste des comptes
    @GetMapping("/comptes")
    public String listeComptes(Model model) {
        model.addAttribute("comptes", compteRepository.findAll());
        return "listeComptes";
    }

    // Ajouter un compte
    @GetMapping("/ajouter")
    public String formAjout(Model model) {
        model.addAttribute("compte", new compte());
        return "ajouterCompte"; // Affichage du formulaire pour ajouter un compte
    }

   

    @PostMapping("/ajouter")
    public String ajouter(@ModelAttribute compte c, RedirectAttributes redirectAttributes) {
        compteRepository.save(c);

        // Envoi d’un email de confirmation
        if (c.getEmail() != null && !c.getEmail().isEmpty()) {
            String sujet = "Création de compte bancaire";
            String msg = "Bonjour " + c.getTitulaire() +
                         ",\n\nVotre compte bancaire a été créé avec succès.\nSolde initial : " + c.getSolde() + " TND\n\nMerci.";
            emailService.envoyerEmail(c.getEmail(), sujet, msg);
        }

        // Message de confirmation
        redirectAttributes.addFlashAttribute("successMessage", "Compte ajouté avec succès !");
        return "redirect:/comptes";
    }




    // Détails d'un compte
    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login"; // Rediriger vers la page de login si non authentifié
        }
        Optional<compte> compteOpt = compteRepository.findById(id);
        if (compteOpt.isPresent()) {
            compte c = compteOpt.get();
            model.addAttribute("compte", c);
            model.addAttribute("operations", c.getOperations()); // Affichage des opérations du compte
            return "detailsCompte"; // Affichage des détails du compte
        } else {
            return "redirect:/comptes"; // Si le compte n'existe pas, rediriger vers la liste des comptes
        }
    }

    // Dépôt d'argent
    

    @PostMapping("/depot/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String effectuerDepot(@PathVariable Long id, @RequestParam double montant) {
        Optional<compte> compteOpt = compteRepository.findById(id);
        if (compteOpt.isPresent() && montant > 0) {
            compte c = compteOpt.get();
            c.setSolde(c.getSolde() + montant);
            compteRepository.save(c);

            Operation op = new Operation(new Date(), montant, "DEPOT", c);
            operationRepository.save(op);

            // 🔔 Envoi d'email
            if (c.getEmail() != null && !c.getEmail().isEmpty()) {
                String subject = "Dépôt effectué";
                String message = "Bonjour " + c.getTitulaire() +
                                 ",\n\nUn dépôt de " + montant + " TND a été effectué sur votre compte.\nSolde actuel : " + c.getSolde() + " TND.\n\nMerci.";
                emailService.envoyerEmail(c.getEmail(), subject, message);
            }
        }
        return "redirect:/details/" + id;
    }


    // Retrait d'argent
    @PostMapping("/retrait/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String effectuerRetrait(@PathVariable Long id, @RequestParam double montant, Model model) {
        Optional<compte> compteOpt = compteRepository.findById(id);
        if (compteOpt.isPresent() && montant > 0) {
            compte c = compteOpt.get();
            if (c.getSolde() >= montant) {
                c.setSolde(c.getSolde() - montant);
                compteRepository.save(c);

                Operation op = new Operation(new Date(), montant, "RETRAIT", c);
                operationRepository.save(op);

                // 🔔 Envoi d'email
                if (c.getEmail() != null && !c.getEmail().isEmpty()) {
                    String subject = "Retrait effectué";
                    String message = "Bonjour " + c.getTitulaire() +
                                     ",\n\nUn retrait de " + montant + " TND a été effectué depuis votre compte.\nSolde actuel : " + c.getSolde() + " TND.\n\nMerci.";
                    emailService.envoyerEmail(c.getEmail(), subject, message);
                }

            } else {
                model.addAttribute("erreur", "Solde insuffisant !");
                model.addAttribute("compte", c);
                model.addAttribute("operations", c.getOperations());
                return "detailsCompte";
            }
        }
        return "redirect:/details/" + id;
    

        
   
    }
}


