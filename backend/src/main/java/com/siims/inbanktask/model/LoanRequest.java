package com.siims.inbanktask.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * Incoming loan request from the client.
 */
public record LoanRequest(
        @NotBlank
        String personalCode,

        @Min(2000) @Max(10000)
        int amount,

        @Min(12) @Max(60)
        int period
) {
}
