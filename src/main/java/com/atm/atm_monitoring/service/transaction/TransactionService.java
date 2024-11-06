package com.atm.atm_monitoring.service.transaction;

import com.atm.atm_monitoring.exceptions.TransactionTypeNotAvailableException;
import com.atm.atm_monitoring.model.Transaction;

import java.util.List;

public interface TransactionService {
    public Integer getTotalCustomersInlast24Hours();

    List<Transaction> getTransactionsByType(String transactionType) throws TransactionTypeNotAvailableException;
}
