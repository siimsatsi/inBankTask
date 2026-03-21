package com.siims.inbanktask.registry;

import com.siims.inbanktask.model.CreditProfile;

/**
 * Provides credit profiles by personal code.
 */
public interface CreditRegistry {

    /**
     * Returns the credit profile for the personal code
     *
     * @param personalCode unique identifier for a person
     * @return credit profile
     * @throws IllegalArgumentException if no personal code is found
     */
    CreditProfile getProfile(String personalCode);
}
