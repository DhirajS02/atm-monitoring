package com.atm.atm_monitoring.service.failure;

import com.atm.atm_monitoring.enums.FailureType;
import com.atm.atm_monitoring.model.FailureLog;
import com.atm.atm_monitoring.repository.FailureLogRepo;
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
class FailureLogServiceImplTest {

    @InjectMocks
    private FailureLogServiceImpl FailureLogService;

    @Mock
    private FailureLogRepo failureLogRepository;

    @Test
    void getAllFailures() {
        FailureLog failure1 = new FailureLog(
                "ATM123",
                Instant.now().minusSeconds(60 * 60 * 18),
                FailureType.CARD_READER_FAILURE,
                "TRANS_FAILED_001",
                "txn008"
        );
        when(failureLogRepository.getFailureLogs()).thenReturn(List.of(failure1));
        List<FailureLog> result = FailureLogService.getAllFailures();
        assertEquals(1, result.size());
    }
}