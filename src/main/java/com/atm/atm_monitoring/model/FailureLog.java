package com.atm.atm_monitoring.model;

import com.atm.atm_monitoring.enums.FailureType;

import java.time.Instant;

public record FailureLog(
        String atmId,
        Instant timestamp,        // When the failure occurred
        FailureType failureType,  // Type of failure
        String errorCode,         // Error code associated with the failure
        String transactionId       // Reference to the related transaction ID
) {}

