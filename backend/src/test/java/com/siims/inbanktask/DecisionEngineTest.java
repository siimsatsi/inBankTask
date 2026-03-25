package com.siims.inbanktask;

import com.siims.inbanktask.model.LoanDecision;
import com.siims.inbanktask.registry.HardcodedCreditRegistry;
import com.siims.inbanktask.service.DecisionEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests here cover debt cases, loan search logic and boundary values.
 */
class DecisionEngineTest {

    private DecisionEngine engine;

    @BeforeEach
    void setUp() {
        engine = new DecisionEngine(new HardcodedCreditRegistry());
    }

    @Test
    void applicantWithDebt_alwaysNegative() {
        LoanDecision decision = engine.evaluate("49002010965", 5000, 24);
        assertFalse(decision.approved());
    }

    @Test
    void validAmount_returnsMaximumNotJustRequested() {
        // modifier is 1000 for this profile, should be valid and then search upward
        LoanDecision decision = engine.evaluate("49002010998", 2000, 12);
        assertTrue(decision.approved());
        assertEquals(10000, decision.amount());
    }

    @Test
    void requestedAmountTooHigh_returnsLowerAmount() {
        // modifier is 100, score at 10000 is too low so it should return a lower amount
        LoanDecision decision = engine.evaluate("49002010976", 10000, 12);
        assertTrue(decision.approved());
        assertTrue(decision.amount() < 10000);
    }

    @Test
    void noValidAmountInPeriod_triesLongerPeriod() {
        // modifier is 100, no valid amount at period 12 months so it should try to extend period
        LoanDecision decision = engine.evaluate("49002010976", 2000, 12);
        assertTrue(decision.approved());
        assertTrue(decision.period() > 12);
    }

    @Test
    void noValidCombinationExists_negative() {
        // unknown personal code so registry should throw
        assertThrows(IllegalArgumentException.class, () ->
                engine.evaluate("00000000000", 5000, 24));
    }

    @Test
    void noValidCombinationExists_negative_2() {
        // modifier is 10, no valid combination at any period
        LoanDecision decision = engine.evaluate("49002019043", 5000, 24);
        assertFalse(decision.approved());
    }

    @Test
    void boundaryValues_minAmountMinPeriod() {
        // modifier is 300, minimum amount and period so it should be valid
        LoanDecision decision = engine.evaluate("49002010987", 2000, 12);
        assertTrue(decision.approved());
    }

    @Test
    void boundaryValues_maxAmountMaxPeriod() {
        // modifier is 1000, maximum amount and period so it should be valid
        LoanDecision decision = engine.evaluate("49002010998", 10000, 60);
        assertTrue(decision.approved());
        assertEquals(10000, decision.amount());
    }

    @Test
    void boundaryValues_scoreExactlyOne() {
        // modifier is 100, score at amount 2000 period 20 is exactly 1.0 so it should be valid
        LoanDecision decision = engine.evaluate("49002010976", 2000, 20);
        assertTrue(decision.approved());
    }

    @Test
    void veryLowModifier_noValidLoan() {
        // modifier is 50 so it should fail
        LoanDecision decision = engine.evaluate("49002010911", 5000, 24);
        assertFalse(decision.approved());
    }

    @Test
    void lowMediumModifier_returnsReducedAmount() {
        // modifier is 200 so it should not return max amount
        LoanDecision decision = engine.evaluate("49002010922", 10000, 12);
        assertTrue(decision.approved());
        assertTrue(decision.amount() < 10000);
    }

    @Test
    void secondDebtor_alwaysRejected() {
        // has debt so application should always be rejected
        LoanDecision decision = engine.evaluate("49002010944", 3000, 24);
        assertFalse(decision.approved());
    }
}
