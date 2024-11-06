package com.atm.atm_monitoring.controller;

import com.atm.atm_monitoring.model.VideoLog;
import com.atm.atm_monitoring.service.videologs.VideoLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.List;

/**
 * REST controller for handling video download url -related endpoints in the ATM monitoring system.
 */
@RestController
@RequestMapping("/v1/video-logs/atm")
public class VideoLogController {

    private final VideoLogService videoLogService;

    public VideoLogController(VideoLogService videoLogService) {
        this.videoLogService = videoLogService;
    }

    /**
     * Retrieves video logs based on the provided ATM ID and transaction ID.
     * This endpoint is restricted to users with the 'ROLE_OWNER' role.
     *
     * @param atmId the ID of the ATM machine
     * @param transactionId the ID of the transaction
     * @return a list of {@link VideoLog} objects associated with the specified ATM and transaction
     * @throws AccessDeniedException if the user does not have the 'ROLE_OWNER' role
     */
    @Operation(summary = "Get video logs by ATM ID and transaction ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Video logs retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access is denied. User does not have the 'ROLE_OWNER' role"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("{atmId}/transaction/{transactionId}")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public List<VideoLog> getVideoLogsByAtmIdAndTransactionId(
            @PathVariable String atmId,
            @PathVariable String transactionId) {
        return videoLogService.findByAtmIdAndTransactionId(atmId, transactionId);
    }

    /**
     * Retrieves video logs within a specific time range.
     *
     * @param startTime the start time of the range
     * @param endTime the end time of the range
     * @return a list of {@link VideoLog} objects that were recorded between the specified times
     * @throws AccessDeniedException if the user does not have the 'ROLE_OWNER' role
     */
    @Operation(summary = "Get video logs by time range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Video logs retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access is denied. User does not have the 'ROLE_OWNER' role"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{atmId}")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public List<VideoLog> findByTimeRange(
            @PathVariable String atmId,
            @RequestParam Instant startTime,
            @RequestParam Instant endTime) {
        return videoLogService.findByTimeRange(atmId,startTime, endTime);
    }

    /**
     * Retrieves video logs based on the provided ATM ID, customer ID, and time range.
     *
     * @param atmId the ID of the ATM machine
     * @param customerId the ID of the customer
     * @param startTime the start time of the range
     * @param endTime the end time of the range
     * @return a list of {@link VideoLog} objects associated with the specified ATM, customer, and time range
     * @throws AccessDeniedException if the user does not have the 'ROLE_OWNER' role
     */
    @Operation(summary = "Get video logs by ATM ID, customer ID, and time range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Video logs retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access is denied. User does not have the 'ROLE_OWNER' role"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("{atmId}/customer/{customerId}")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public List<VideoLog> getVideoLogsByAtmIdAndCustomerIdAndTimeRange(
            @PathVariable String atmId,
            @PathVariable String customerId,
            @RequestParam Instant startTime,
            @RequestParam Instant endTime) {
        return videoLogService.findByAtmIdAndCustomerIdAndTimeRange(atmId, customerId, startTime, endTime);
    }
}

