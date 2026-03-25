package com.siims.inbanktask.registry;

import com.siims.inbanktask.model.CreditProfile;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Hardcoded credit profiles for the DecisionEngine
 */
@Component
public class HardcodedCreditRegistry implements CreditRegistry {

    private static final Map<String, CreditProfile> PROFILES = Map.of(
            "49002010965", new CreditProfile(true, 0),
            "49002010976", new CreditProfile(false, 100),
            "49002010987", new CreditProfile(false, 300),
            "49002010998", new CreditProfile(false, 1000),

            "49002019043", new CreditProfile(false, 10),
            "49002010911", new CreditProfile(false, 30),
            "49002010922", new CreditProfile(false, 200),
            "49002010933", new CreditProfile(false, 500),
            "49002010944", new CreditProfile(true, 0),
            "49002010955", new CreditProfile(false, 1500)
    );

    @Override
    public CreditProfile getProfile(String personalCode) {
        CreditProfile profile = PROFILES.get(personalCode);
        if (profile == null) {
            throw new IllegalArgumentException("Unknown personal code: " + personalCode);
        }
        return profile;
    }
}
