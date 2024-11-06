package com.atm.atm_monitoring.service.transaction;

import com.atm.atm_monitoring.enums.TransactionType;
import com.atm.atm_monitoring.exceptions.TransactionTypeNotAvailableException;
import com.atm.atm_monitoring.model.Transaction;
import com.atm.atm_monitoring.repository.TransactionRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepo transactionRepo;

    @Test
    public void testGetTotalCustomersInLast24Hours() {
        final var transactionList=mockTransactions();
        when(transactionRepo.findAll()).thenReturn(transactionList);
        Integer result = transactionService.getTotalCustomersInlast24Hours();

        assertEquals(2, result);
    }

    @Test
    public void testGetTransactionsByType_ReturnsTransactions_WhenTypeIsValid() throws TransactionTypeNotAvailableException {
        final var transactionList=mockTransactions();
        when(transactionRepo.findAll()).thenReturn(transactionList);
        List<Transaction> result = transactionService.getTransactionsByType("Cash Withdrawal");
        assertEquals(1, result.size());
    }

    @Test
    public void testGetTransactionsByType_ThrowsException_WhenTypeIsInvalid() {
        final var exceptionThrown = assertThrows(TransactionTypeNotAvailableException.class, () -> {
            transactionService.getTransactionsByType("INVALID_TYPE");
        });
        assertEquals("This transaction is not allowed", exceptionThrown.getMessage());
    }

    public List<Transaction> mockTransactions()
    {
        Transaction transaction1 = new Transaction("txn001", "cust001", TransactionType.CASH_WITHDRAWAL, 150.00, Instant.now().minusSeconds(60 * 60 * 22), "SUCCESS");
        Transaction transaction2 = new Transaction("txn002", "cust002", TransactionType.DEPOSIT, 500.00, Instant.now().minusSeconds(60 * 60 * 23), "SUCCESS");
        return Arrays.asList(transaction1,transaction2);
    }
}