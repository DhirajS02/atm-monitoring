package com.atm.atm_monitoring.service.transaction;

import com.atm.atm_monitoring.enums.TransactionType;
import com.atm.atm_monitoring.exceptions.TransactionTypeNotAvailableException;
import com.atm.atm_monitoring.model.Transaction;
import com.atm.atm_monitoring.repository.TransactionRepo;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepo transactionRepo;

    public TransactionServiceImpl(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    /**
     * Retrieves the total number of unique customers who made transactions in the last 24 hours.
     *
     * @return the count of unique customers within the last 24 hours
     */
    @Override
    public Integer getTotalCustomersInlast24Hours() {
        final var now = Instant.now();
        final var last24HoursInSeconds = now.minusSeconds(24 * 60 * 60);
        return transactionRepo
                .findAll()
                .stream()
                .filter(transaction -> transaction.timestamp().isAfter(last24HoursInSeconds))
                .map(Transaction::customerId).collect(Collectors.toSet()).size();
    }

    /**
     * Retrieves transactions filtered by the specified transaction type.
     *
     * @param transactionType the type of transaction to filter by (e.g., 'DEPOSIT', 'CASH_WITHDRAWAL', 'BALANCE_INFORMATION')
     * @return a list of transactions that match the specified type
     * @throws TransactionTypeNotAvailableException if the specified transaction type is not valid
     */
    @Override
    public List<Transaction> getTransactionsByType(String transactionType) throws TransactionTypeNotAvailableException {
        if(isValidTransactionType(transactionType)) {
            return transactionRepo
                    .findAll()
                    .stream()
                    .filter(transaction -> transaction.transactionType().getValue().equalsIgnoreCase(transactionType)).collect(Collectors.toList());
        }
        throw new TransactionTypeNotAvailableException("This transaction is not allowed");
    }

    /**
     * Checks if the given transaction type is valid.
     *
     * @param transactionType the transaction type to check
     * @return true if the transaction type is valid, false otherwise
     */
    private boolean isValidTransactionType(String transactionType) {
        return Arrays.stream(TransactionType.values())
                .anyMatch(type -> type.getValue().equalsIgnoreCase(transactionType));
    }
}
