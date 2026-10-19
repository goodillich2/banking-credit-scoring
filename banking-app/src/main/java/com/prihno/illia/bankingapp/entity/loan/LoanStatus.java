package com.prihno.illia.bankingapp.entity.loan;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum LoanStatus {
    ACCEPTED, DENIED;

    @JsonCreator
    public static LoanStatus fromValue(String value) {
        for (LoanStatus status : LoanStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
