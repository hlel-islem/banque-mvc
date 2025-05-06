package com.fst.banque.controlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccueilController {

    // Cette méthode permet de rediriger l'utilisateur vers la page de connexion
    @GetMapping("/login")
    public String accueil() {
        return "login"; // La vue login.html doit être présente dans src/main/resources/templates
    }
}