package com.fst.banque.repositories;

import com.fst.banque.entities.compte;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompteRepository extends JpaRepository<compte, Integer> {
	 
	}

