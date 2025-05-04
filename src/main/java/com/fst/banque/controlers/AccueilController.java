package com.fst.banque.controlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccueilController {
	 @GetMapping("/login")
	    public String accueil() {
	        return "login"; 
	    }
}
