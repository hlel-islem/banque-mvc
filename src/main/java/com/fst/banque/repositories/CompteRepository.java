package com.fst.banque.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fst.banque.entities.compte;



public interface CompteRepository extends JpaRepository<compte, Integer> {
	 
	}

