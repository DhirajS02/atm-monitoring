package com.atm.atm_monitoring.service.videologs;

import com.atm.atm_monitoring.model.VideoLog;
import com.atm.atm_monitoring.repository.VideoLogRepo;
import org.junit.jupiter.api.BeforeAll;
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
class VideoLogServiceImplTest {
    @InjectMocks
    private VideoLogServiceImpl videoLogService;

    @Mock
    private VideoLogRepo videoLogRepository;

    private static List<VideoLog> videoLogs;

    @BeforeAll
    static void setUp() {
        final var videoLog1 = new VideoLog("ATM123", "txn001", "cust001", "https://s3.amazonaws.com/videos/ATM123_TRANS001.mp4", Instant.now().minusSeconds(60 * 60 * 48));
        final var videoLog2 = new VideoLog("ATM456", "txn002", "cust002", "https://s3.amazonaws.com/videos/ATM456_TRANS002.mp4", Instant.now().minusSeconds(60 * 60 * 48));
        videoLogs=List.of(videoLog1,videoLog2);
    }

    @Test
    void findByAtmIdAndTransactionIdAndTimeRange() {
        when(videoLogRepository.findByAtmIdAndTransactionId("ATM123", "txn001"))
                .thenReturn(videoLogs);
        List<VideoLog> result = videoLogService.findByAtmIdAndTransactionId("ATM123", "txn001");
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("ATM123", result.get(0).getAtmId());

    }

    @Test
    void findByAtmIdAndCustomerIdAndTimeRange() {
        Instant startTime = Instant.now().minusSeconds(60 * 60 * 72);
        Instant endTime = Instant.now();
        when(videoLogRepository.findByAtmIdAndCustomerIdAndTimeRange("ATM123", "cust001", startTime, endTime))
                .thenReturn(videoLogs);
        final var result = videoLogService.findByAtmIdAndCustomerIdAndTimeRange("ATM123", "cust001", startTime, endTime);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("cust001", result.get(0).getCustomerId());
    }

    @Test
    void findByTimeRange() {
        Instant startTime = Instant.now().minusSeconds(60 * 60 * 72);
        Instant endTime = Instant.now();
        when(videoLogRepository.findByTimeRange("ATM123",startTime, endTime))
                .thenReturn(videoLogs);
        final var result = videoLogService.findByTimeRange("ATM123",startTime, endTime);
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}