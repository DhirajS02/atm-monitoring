package com.atm.atm_monitoring.model;

import com.atm.atm_monitoring.enums.TransactionStatus;
import com.atm.atm_monitoring.enums.TransactionType;

import java.time.Instant;

public record Transaction(
        String transactionId,         // Unique transaction ID
        String customerId,            // Customer ID
        TransactionType transactionType,  // Type of transaction (e.g., "WITHDRAWAL", "DEPOSIT")
        double amount,                // Amount involved in the transaction
        Instant timestamp,            // Timestamp when the transaction occurred
        TransactionStatus status          // Status of the transaction (e.g., "SUCCESS", "FAILED")
)
{

}


