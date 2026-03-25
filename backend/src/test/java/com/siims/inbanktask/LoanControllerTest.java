package com.siims.inbanktask;

import com.siims.inbanktask.controller.LoanController;
import com.siims.inbanktask.model.LoanDecision;
import com.siims.inbanktask.model.LoanRequest;
import com.siims.inbanktask.service.DecisionEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests here verify HTTP response behavior with mocked engine.
 */
class LoanControllerTest {

    private LoanController controller;
    private DecisionEngine decisionEngine;

    @BeforeEach
    void setUp() {
        decisionEngine = mock(DecisionEngine.class);
        controller = new LoanController(decisionEngine);
    }

    @Test
    void validRequest_returnsApprovedDecision() {
        when(decisionEngine.evaluate("49002010998", 5000, 24))
                .thenReturn(LoanDecision.positive(5000, 24));

        ResponseEntity<LoanDecision> response = controller.getDecision(
                new LoanRequest("49002010998", 5000, 24));

        LoanDecision body = response.getBody();
        assertNotNull(body);
        assertEquals(200, response.getStatusCode().value());
        assertTrue(body.approved());
    }

    @Test
    void negativeDecision_returnsUnapprovedDecision() {
        when(decisionEngine.evaluate("49002010965", 5000, 24))
                .thenReturn(LoanDecision.negative());

        ResponseEntity<LoanDecision> response = controller.getDecision(
                new LoanRequest("49002010965", 5000, 24));

        LoanDecision body = response.getBody();
        assertNotNull(body);
        assertEquals(200, response.getStatusCode().value());
        assertFalse(body.approved());
    }

    @Test
    void unknownPersonalCode_throwsIllegalArgumentException() {
        when(decisionEngine.evaluate("00000000000", 5000, 24))
                .thenThrow(new IllegalArgumentException("Unknown personal code: 00000000000"));

        LoanRequest request = new LoanRequest("00000000000", 5000, 24);
        assertThrows(IllegalArgumentException.class, () ->
                controller.getDecision(request));
    }
}
