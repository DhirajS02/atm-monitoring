package com.atm.atm_monitoring.service.videologs;

import com.atm.atm_monitoring.model.VideoLog;

import java.time.Instant;
import java.util.List;

public interface VideoLogService {
    List<VideoLog> findByAtmIdAndTransactionId(String atmId, String transactionId);

    List<VideoLog> findByAtmIdAndCustomerIdAndTimeRange(String atmId, String customerId, Instant startTime, Instant endTime);

    List<VideoLog> findByTimeRange(String atmId,Instant startTime, Instant endTime);
}
