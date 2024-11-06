package com.atm.atm_monitoring.service.failure;

import com.atm.atm_monitoring.model.FailureLog;
import com.atm.atm_monitoring.repository.FailureLogRepo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FailureLogServiceImpl implements FailureLogService {
    private final FailureLogRepo failureLogRepo;

    public FailureLogServiceImpl(FailureLogRepo failureLogRepo) {
        this.failureLogRepo = failureLogRepo;
    }

    /** Retrieves all failure logs from the system.

     *
     * @return List<FailureLog>
     */
    @Override
    public List<FailureLog> getAllFailures() {
        return failureLogRepo.getFailureLogs();
    }
}
