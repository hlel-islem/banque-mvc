package com.fst.banque.services;

import com.fst.banque.entities.Operation;
import com.fst.banque.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OperationService {

    @Autowired
    private OperationRepository operationRepository;

    // Define the method correctly
    public List<Operation> getOperationsByCompteCode(String code) {
        return operationRepository.findByCompteCode(code);  // Call repository method
    }



	


}
