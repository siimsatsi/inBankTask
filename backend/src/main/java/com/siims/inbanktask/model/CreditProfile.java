package com.siims.inbanktask.model;

/**
 * Represents credit standing for a person.
 * hasDebt is a check that when true, then it always rejects the loan regardless of anything else.
 * creditModifier is used for the scoring formula (creditModifier / loanAmount) * loanPeriod
 */
public record CreditProfile(boolean hasDebt, int creditModifier) {}
