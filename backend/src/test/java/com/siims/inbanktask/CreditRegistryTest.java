package com.siims.inbanktask;

import com.siims.inbanktask.model.CreditProfile;
import com.siims.inbanktask.registry.HardcodedCreditRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests here verify correct profile mapping and unknown code handling.
 */
class CreditRegistryTest {

    private HardcodedCreditRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new HardcodedCreditRegistry();
    }

    @Test
    void debtorCode_returnsProfileWithDebt() {
        CreditProfile profile = registry.getProfile("49002010965");
        assertTrue(profile.hasDebt());
        assertEquals(0, profile.creditModifier());
    }

    @Test
    void lowModifierCode_returnsCorrectProfile() {
        CreditProfile profile = registry.getProfile("49002010976");
        assertFalse(profile.hasDebt());
        assertEquals(100, profile.creditModifier());
    }

    @Test
    void mediumModifierCode_returnsCorrectProfile() {
        CreditProfile profile = registry.getProfile("49002010987");
        assertFalse(profile.hasDebt());
        assertEquals(300, profile.creditModifier());
    }

    @Test
    void highModifierCode_returnsCorrectProfile() {
        CreditProfile profile = registry.getProfile("49002010998");
        assertFalse(profile.hasDebt());
        assertEquals(1000, profile.creditModifier());
    }

    @Test
    void unknownCode_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                registry.getProfile("00000000000"));
    }

    @Test
    void veryLowModifierCode_returnsCorrectProfile() {
        CreditProfile profile = registry.getProfile("49002010911");
        assertFalse(profile.hasDebt());
        assertEquals(30, profile.creditModifier());
    }

    @Test
    void lowMediumModifierCode_returnsCorrectProfile() {
        CreditProfile profile = registry.getProfile("49002010922");
        assertFalse(profile.hasDebt());
        assertEquals(200, profile.creditModifier());
    }

    @Test
    void mediumHighModifierCode_returnsCorrectProfile() {
        CreditProfile profile = registry.getProfile("49002010933");
        assertFalse(profile.hasDebt());
        assertEquals(500, profile.creditModifier());
    }

    @Test
    void secondDebtorCode_returnsProfileWithDebt() {
        CreditProfile profile = registry.getProfile("49002010944");
        assertTrue(profile.hasDebt());
        assertEquals(0, profile.creditModifier());
    }

    @Test
    void veryHighModifierCode_returnsCorrectProfile() {
        CreditProfile profile = registry.getProfile("49002010955");
        assertFalse(profile.hasDebt());
        assertEquals(1500, profile.creditModifier());
    }
}
