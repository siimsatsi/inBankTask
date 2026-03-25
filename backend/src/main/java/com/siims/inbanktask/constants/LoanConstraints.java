package com.siims.inbanktask.constants;


/**
 * Hold limits for the loan amounts and periods.
 */
public final class LoanConstraints {

    private LoanConstraints() {}

        public static final int MIN_LOAN_AMOUNT = 2000;
        public static final int MAX_LOAN_AMOUNT = 10000;

        public static final int MAX_LOAN_PERIOD = 60;

}
