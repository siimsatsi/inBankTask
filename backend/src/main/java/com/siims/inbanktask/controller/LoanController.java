package com.siims.inbanktask.controller;

import com.siims.inbanktask.model.LoanDecision;
import com.siims.inbanktask.model.LoanRequest;
import com.siims.inbanktask.service.DecisionEngine;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Handles loan decision HTTP requests. Delegates all logic to DecisionEngine.
 */
@RestController
@RequestMapping("/api/loan")
public class LoanController {

    private final DecisionEngine decisionEngine;

    public LoanController(DecisionEngine decisionEngine) {
        this.decisionEngine = decisionEngine;
    }

    @PostMapping("/decision")
    public ResponseEntity<LoanDecision> getDecision(@Valid @RequestBody LoanRequest request) {
        LoanDecision decision = decisionEngine.evaluate(
                request.personalCode(),
                request.amount(),
                request.period()
        );
        return ResponseEntity.ok(decision);
    }
}
