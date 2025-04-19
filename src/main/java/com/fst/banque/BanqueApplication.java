package com.fst.banque;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fst.banque.entities.compte;

@SpringBootApplication
public class BanqueApplication {
	public static List<compte>comptes=new ArrayList<>();
	public static void main(String[] args) {
		SpringApplication.run(BanqueApplication.class, args);
		compte c1=new compte (1,"Islem",1650);
		compte c2=new compte (2,"Amine",1050);
		compte c3=new compte (3,"ahmed",1650);
		
		for (compte compte : comptes) {
            System.out.println(compte);
        }
		  
		
		
	}

}
