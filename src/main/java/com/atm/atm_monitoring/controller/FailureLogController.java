package com.atm.atm_monitoring.controller;

import com.atm.atm_monitoring.model.FailureLog;
import com.atm.atm_monitoring.service.failure.FailureLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class that handles the API requests for failure logs.
 * It provides endpoints to retrieve failure from the system.
 */
@RestController
@RequestMapping("/v1/failures")
public class FailureLogController {
    private final FailureLogService failureLogService;

    public FailureLogController(FailureLogService failureLogService) {
        this.failureLogService = failureLogService;
    }

    /**
     *  Retrieves all failure logs in the system with cause of failure.
     *  The response will contain a list of {@link FailureLog} objects representing the failure logs
     *
     * @return List<FailureLog>
     * @throws AccessDeniedException if user does not have 'ROLE_OWNER' role.
     */
    @Operation(summary = "Get all failure logs with their causes of failure")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Failure logs retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access is denied. User does not have the 'ROLE_OWNER' role"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public List<FailureLog> getFailures() {
        return failureLogService.getAllFailures();
    }
}

