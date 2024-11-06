package com.atm.atm_monitoring.controller;

import com.atm.atm_monitoring.exceptions.TransactionTypeNotAvailableException;
import com.atm.atm_monitoring.model.Transaction;
import com.atm.atm_monitoring.service.transaction.TransactionService;
import com.atm.atm_monitoring.service.transaction.TransactionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling transaction-related endpoints in the ATM monitoring system.
 */
@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionServiceImpl transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Retrieves the total count of customers who made transactions in the last 24 hours.
     *
     * @throws AccessDeniedException if the user does not have the 'ROLE_OWNER' role
     */
    @Operation(summary = "Get the total count of customers who made transactions in the last 24 hours")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total count retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access is denied. User does not have the 'ROLE_OWNER' role"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("customers/count/24-hours")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<Integer> getTotalTransactionsCount()
    {
        return new ResponseEntity<>(transactionService.getTotalCustomersInlast24Hours(), HttpStatus.OK);
    }

    /**
     * Retrieves a list of transactions filtered by transaction type.
     *
     * @param transactionType the type of transaction to filter by (e.g., 'DEPOSIT', 'CASH_WITHDRAWAL', 'BALANCE_INFORMATION')
     * @throws TransactionTypeNotAvailableException if the provided transaction type is not valid
     * @throws AccessDeniedException if the user does not have the 'ROLE_OWNER' role
     */
    @Operation(summary = "Get a list of transactions filtered by transaction type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid transaction type provided"),
            @ApiResponse(responseCode = "403", description = "Access is denied. User does not have the 'ROLE_OWNER' role"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<List<Transaction>> getTransactionByType(@RequestParam("transactionType") String transactionType) throws TransactionTypeNotAvailableException {
        return new ResponseEntity<>(transactionService.getTransactionsByType(transactionType), HttpStatus.OK);
    }
}
