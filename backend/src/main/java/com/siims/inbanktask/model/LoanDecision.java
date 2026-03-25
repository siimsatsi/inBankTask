package com.siims.inbanktask.model;

/**
 * Represents a loan decision with approval status, amount, and period.
 */
public record LoanDecision(boolean approved, int amount, int period, String reason) {

    /**
     * Creates a negative. Meaning the loan was not approved.
     *
     * @return a new LoanDecision instance with approved=false, amount=0, period=0
     */
    public static LoanDecision negative(String reason) {
        return new LoanDecision(false, 0, 0, reason);
    }

    /**
     * Creates a positive loan decision with the specified amount and period.
     *
     * @param amount the approved loan amount
     * @param period the approved loan period in months
     * @return a new LoanDecision instance with approved=true + the given amount and period
     */
    public static LoanDecision positive(int amount, int period) {
        return new LoanDecision(true, amount, period, null);
    }
}
