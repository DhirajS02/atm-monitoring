package com.atm.atm_monitoring.repository;

import com.atm.atm_monitoring.enums.FailureType;
import com.atm.atm_monitoring.model.FailureLog;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing a list of {@link FailureLog} objects, simulating a database
 * for storing ATM failure logs.
 */
@Repository
public class FailureLogRepo {
    private final List<FailureLog> failureLogs = new ArrayList<>();

    /**
     * Default constructor that initializes the repository with some predefined failure logs.
     */
    public FailureLogRepo() {
        // A failure for txn008 which is a failed transaction
        failureLogs.add(new FailureLog(
                "ATM123",
                Instant.now().minusSeconds(60 * 60 * 18), // When the failure occurred
                FailureType.CARD_READER_FAILURE,       // Type of failure
                "TRANS_FAILED_001",                   // Error code
                "txn008"                               // Reference to the related transaction ID
        ));
    }

    /**
     * Retrieves all stored failure logs from the repository.
     *
     * @return a list of {@link FailureLog} representing all logged failures.
     */
    public List<FailureLog> getFailureLogs() {
        return failureLogs;
    }
}
