package com.siims.inbanktask.service;

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

    /**
     * Constructs a DecisionEngine with the required credit registry.
     *
     * @param creditRegistry the credit registry service used to fetch applicant credit profiles
     */
    public DecisionEngine(CreditRegistry creditRegistry) {
        this.creditRegistry = creditRegistry;
    }

    /**
     * Evaluates a loan application based on the applicant's credit profile.
     *
     * @param personalCode    the unique identifier of the applicant
     * @param requestedAmount the amount of credit requested
     * @param requestedPeriod the requested loan period in months
     * @return a LoanDecision indicating whether it was approved or not. If approved, return amount and period
     */
    public LoanDecision evaluate(String personalCode, int requestedAmount, int requestedPeriod) {
        CreditProfile profile = creditRegistry.getProfile(personalCode);

        // TODO: add loan search logic
        if (profile.hasDebt()) {
            return LoanDecision.negative();
        }

        return LoanDecision.negative();
    }
}
