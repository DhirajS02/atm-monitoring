package com.atm.atm_monitoring.enums;

/**
 * Enum representing the types of transactions in the ATM monitoring system.
 */
public enum TransactionType {
    CASH_WITHDRAWAL("Cash Withdrawal"),
    DEPOSIT("Deposit"),
    BALANCE_INFORMATION("Balance Information");
    private final String value;  // String value associated with the enum

    TransactionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
