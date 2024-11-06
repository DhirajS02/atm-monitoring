package com.atm.atm_monitoring.service.videologs;

import com.atm.atm_monitoring.model.VideoLog;
import com.atm.atm_monitoring.repository.VideoLogRepo;
import com.atm.atm_monitoring.service.videologs.VideoLogService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
@Service
public class VideoLogServiceImpl implements VideoLogService {

    private final VideoLogRepo videoLogRepo;

    public VideoLogServiceImpl(VideoLogRepo videoLogRepo) {
        this.videoLogRepo = videoLogRepo;
    }

    /**
     * Retrieves video logs by the specified ATM ID and transaction ID.
     *
     * @param atmId the ID of the ATM
     * @param transactionId the ID of the transaction
     * @return a list of {@link VideoLog} objects that match the given ATM ID and transaction ID
     */
    @Override
    public List<VideoLog> findByAtmIdAndTransactionId(String atmId, String transactionId) {
        return videoLogRepo.findByAtmIdAndTransactionId(atmId, transactionId);
    }

    /**
     * Retrieves video logs by ATM ID, customer ID, and time range.
     *
     * @param atmId the ID of the ATM
     * @param customerId the ID of the customer
     * @param startTime the start of the time range for filtering logs
     * @param endTime the end of the time range for filtering logs
     * @return a list of {@link VideoLog} objects that match the given ATM ID, customer ID, and time range
     */
    @Override
    public List<VideoLog> findByAtmIdAndCustomerIdAndTimeRange(String atmId, String customerId, Instant startTime, Instant endTime) {
        return videoLogRepo.findByAtmIdAndCustomerIdAndTimeRange(atmId, customerId, startTime, endTime);
    }

    /**
     * Retrieves video logs within a specified time range.
     *
     * @param startTime the start of the time range
     * @param endTime the end of the time range
     * @return a list of {@link VideoLog} objects within the given time range
     */
    @Override
    public List<VideoLog> findByTimeRange(String atmId, Instant startTime, Instant endTime) {
        return videoLogRepo.findByTimeRange(atmId, startTime, endTime);
    }
}
