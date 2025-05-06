package com.fst.banque.repositories;

import com.fst.banque.entities.compte;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CompteRepository extends JpaRepository<compte, Integer> {

	Optional<compte> findById(long long1);
	 
	}

