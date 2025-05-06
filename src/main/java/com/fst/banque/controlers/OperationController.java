package com.fst.banque.controlers;

import com.fst.banque.entities.Operation;
import com.fst.banque.services.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operations")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @GetMapping("/comptes/{code}")
    public List<Operation> getOperationsByCompteCode(@PathVariable String code) {
        return operationService.getOperationsByCompteCode(code);  // Correct method call
    }
}

