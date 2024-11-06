package com.atm.atm_monitoring.repository;

import com.atm.atm_monitoring.model.VideoLog;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository class for managing a list of {@link VideoLog} objects, simulating a database for storing ATM video logs.
 */
@Repository
public class VideoLogRepo {
    private final List<VideoLog> videoLogs = new ArrayList<>();

    /**
     * Default constructor that initializes the repository with some predefined video logs.
     */
    public VideoLogRepo() {
        videoLogs.add(new VideoLog("ATM123", "txn001", "cust001", "https://s3.amazonaws.com/videos/ATM123_TRANS001.mp4", Instant.now().minusSeconds(60 * 60 * 48)));
        videoLogs.add(new VideoLog("ATM456", "txn002", "cust002", "https://s3.amazonaws.com/videos/ATM456_TRANS002.mp4", Instant.now().minusSeconds(60 * 60 * 48)));
        videoLogs.add(new VideoLog("ATM789", "txn004", "cust003", "https://s3.amazonaws.com/videos/ATM789_TRANS003.mp4", Instant.now()));
        videoLogs.add(new VideoLog("ATM123", "txn006", "cust001", "https://s3.amazonaws.com/videos/ATM123_TRANS004.mp4", Instant.now()));
        videoLogs.add(new VideoLog("ATM123", "txn008", "cust003", "https://s3.amazonaws.com/videos/ATM123_TRANS008.mp4", Instant.now()));

    }

    /**
     * Retrieves all stored video logs from the repository.
     *
     * @return a list of {@link VideoLog} representing all logged video logs.
     */
    public List<VideoLog> findAll() {
        return new ArrayList<>(videoLogs);
    }

    /**
     * Retrieves video logs by a given ATM ID and transaction ID.
     *
     * @param atmId          the ID of the ATM.
     * @param transactionId  the ID of the transaction.
     * @return a list of {@link VideoLog} that match the given ATM ID and transaction ID.
     */
    public List<VideoLog> findByAtmIdAndTransactionId(String atmId, String transactionId) {
        return videoLogs.stream()
                .filter(log -> log.getAtmId().equals(atmId) &&
                        log.getTransactionId().equals(transactionId))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves video logs for a specific ATM, customer, and time range.
     *
     * @param atmId       the ID of the ATM.
     * @param customerId  the ID of the customer.
     * @param startTime   the start of the time range.
     * @param endTime     the end of the time range.
     * @return a list of {@link VideoLog} that match the given ATM ID, customer ID, and time range.
     */
    public List<VideoLog> findByAtmIdAndCustomerIdAndTimeRange(String atmId, String customerId, Instant startTime, Instant endTime) {
        return videoLogs.stream()
                .filter(log -> log.getAtmId().equals(atmId) &&
                        log.getCustomerId().equals(customerId) &&
                        (log.getTimestamp().isAfter(startTime) || log.getTimestamp().equals(startTime)) &&
                        (log.getTimestamp().isBefore(endTime) || log.getTimestamp().equals(endTime)))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves video logs within a specific time range.
     *
     * @param atmId       the ID of the ATM.
     * @param startTime   the start of the time range.
     * @param endTime     the end of the time range.
     * @return a list of {@link VideoLog} that match the given time range.
     */
    public List<VideoLog> findByTimeRange(String atmId, Instant startTime, Instant endTime) {
        return videoLogs.stream()
                .filter(log -> log.getAtmId().equals(atmId) && log.getTimestamp().isAfter(startTime) || log.getTimestamp().equals(startTime) &&
                        (log.getTimestamp().isBefore(endTime) || log.getTimestamp().equals(endTime)))
                .collect(Collectors.toList());
    }
}
