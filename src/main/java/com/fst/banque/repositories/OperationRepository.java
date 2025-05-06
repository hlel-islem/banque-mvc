package com.fst.banque.repositories;

import com.fst.banque.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findByCompteCode(String code);  // Make sure this method exists
}


