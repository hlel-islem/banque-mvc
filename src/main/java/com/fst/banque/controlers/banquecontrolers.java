package com.fst.banque.controlers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fst.banque.entities.compte;
import static com.fst.banque.BanqueApplication.comptes; 

import java.util.ArrayList;
import java.util.List;

@Controller
public class banquecontrolers {
	private List<compte> comptes = new ArrayList<>();
	public banquecontrolers (){
		comptes.add(new compte(1,"Islem", 1650));  
        comptes.add(new compte(2,"Eya", 1800));
        comptes.add(new compte(3,"Amine", 1530));
	}
	
	
	
	@GetMapping("/comptes")
    public String listeComptes(Model model) {
        model.addAttribute("comptes", comptes);
        return "listeComptes";
    }

	@GetMapping("/ajouter")
    public String formAjout(Model model) {
        model.addAttribute("compte", new compte());
        return "ajouterCompte";
    }
    

	@PostMapping("/ajouter")
    public String ajouter(@RequestParam String titulaire, @RequestParam double solde) {
        int id = comptes.size() + 1;
        compte c = new compte(id, titulaire, solde);
        c.setId(id);
        comptes.add(c);
        return "redirect:/comptes";
    }

	@GetMapping("/details/{id}")
    public String details(@PathVariable int id, Model model) {
        for (compte c : comptes) {
            if (c.getId() == id) {
                model.addAttribute("compte", c);
                break;
            }
        }
        return "detailsCompte";
    }

	@PostMapping("/depot/{id}")
    public String depot(@PathVariable int id, @RequestParam double montant) {
        for (compte c : comptes) {
        	 if (c.getId() == id && montant > 0) {
                c.setSolde(c.getSolde() + montant);
                break;
            }
        }
        return "redirect:/details/" + id;
    }

	 @PostMapping("/retrait/{id}")
	    public String retrait(@PathVariable int id, @RequestParam double montant) {
	        for (compte c : comptes) {
	        	  if (c.getId() == id && montant > 0 && c.getSolde() >= montant)  {
	                c.setSolde(c.getSolde() - montant);
	                break;
	            }
	        }
	        return "redirect:/details/" + id;
	    }

	 
}