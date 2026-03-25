package com.siims.inbanktask.service;

import com.siims.inbanktask.constants.LoanConstraints;
import com.siims.inbanktask.model.CreditProfile;
import com.siims.inbanktask.model.LoanDecision;
import com.siims.inbanktask.registry.CreditRegistry;
import org.springframework.stereotype.Service;

/**
 * DecisionEngine is responsible for evaluating loan applications and making approval decisions.
 * Uses credit registry to evaluate whether an applicant qualifies for a loan or not.
 */
@Service
public class DecisionEngine {

    private final CreditRegistry creditRegistry;

    public DecisionEngine(CreditRegistry creditRegistry) {
        this.creditRegistry = creditRegistry;
    }

    public LoanDecision evaluate(String personalCode, int requestedAmount, int requestedPeriod) {
        CreditProfile profile = creditRegistry.getProfile(personalCode);

        if (profile.hasDebt()) {
            return LoanDecision.negative("Loan rejected due to existing debt");
        }

        for (int period = requestedPeriod; period <= LoanConstraints.MAX_LOAN_PERIOD; period++) {
            int amount = findBestAmount(profile.creditModifier(), requestedAmount, period);
            if (amount >= LoanConstraints.MIN_LOAN_AMOUNT) {
                return LoanDecision.positive(amount, period);
            }
        }

        return LoanDecision.negative("No valid loan combination found for your credit profile.");
    }

    /**
     * Finds the highest approvable amount for the given period.
     */
    private int findBestAmount(int creditModifier, int requestedAmount, int period) {
        // Valid request amount, search up for maximum amount
        if (creditScore(creditModifier, requestedAmount, period) >= 1.0) {
            int best = requestedAmount;
            for (int amount = requestedAmount; amount <= LoanConstraints.MAX_LOAN_AMOUNT; amount += 100) {
                if (creditScore(creditModifier, amount, period) >= 1.0) {
                    best = amount;
                } else {
                    break;
                }
            }
            return best;
        } else {
            // Invalid request amount, search downwards
            for (int amount = requestedAmount; amount >= LoanConstraints.MIN_LOAN_AMOUNT; amount -= 100) {
                if (creditScore(creditModifier, amount, period) >= 1.0) {
                    return amount;
                }
            }
            return 0;
        }
    }

    /**
     * credit score = (creditModifier / loanAmount) * loanPeriod
     * Score >= 1.0 means the loan can be approved.
     */
    private double creditScore(int creditModifier, int amount, int period) {
        return ((double) creditModifier / amount) * period;
    }
}
