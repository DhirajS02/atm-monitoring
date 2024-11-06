package com.atm.atm_monitoring.repository;

import com.atm.atm_monitoring.enums.TransactionType;
import com.atm.atm_monitoring.model.Transaction;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing a list of {@link Transaction} objects, simulating a database
 * for storing ATM transaction records.
 */
@Repository
public class TransactionRepo {
    private final List<Transaction> transactions = new ArrayList<>();

    /**
     * Default constructor that initializes the repository with some predefined transaction logs.
     */
    public TransactionRepo() {
        transactions.add(new Transaction("txn001", "cust001", TransactionType.CASH_WITHDRAWAL, 150.00, Instant.now().minusSeconds(60 * 60 * 24), "SUCCESS"));
        transactions.add(new Transaction("txn002", "cust002", TransactionType.DEPOSIT, 500.00, Instant.now().minusSeconds(60 * 60 * 23), "SUCCESS"));
        transactions.add(new Transaction("txn004", "cust003", TransactionType.BALANCE_INFORMATION, 300.00, Instant.now().minusSeconds(60 * 60 * 22), "SUCCESS"));
        transactions.add(new Transaction("txn006", "cust001", TransactionType.CASH_WITHDRAWAL, 250.00, Instant.now().minusSeconds(60 * 60 * 20), "SUCCESS"));
        transactions.add(new Transaction("txn007", "cust005", TransactionType.DEPOSIT, 400.00, Instant.now().minusSeconds(60 * 60 * 19), "SUCCESS"));
        transactions.add(new Transaction("txn008", "cust003", TransactionType.DEPOSIT, 150.00, Instant.now().minusSeconds(60 * 60 * 18), "FAILED"));
        transactions.add(new Transaction("txn009", "cust004", TransactionType.DEPOSIT, 500.00, Instant.now().minusSeconds(60 * 60 * 17), "SUCCESS"));
        transactions.add(new Transaction("txn010", "cust005", TransactionType.BALANCE_INFORMATION, 200.00, Instant.now().minusSeconds(60 * 60 * 16), "SUCCESS"));
    }

    /**
     *Method to retrieve all transactions
     */
    public List<Transaction> findAll() {
        return transactions;
    }
}
