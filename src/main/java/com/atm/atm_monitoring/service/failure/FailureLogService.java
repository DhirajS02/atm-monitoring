package com.atm.atm_monitoring.service.failure;

import com.atm.atm_monitoring.model.FailureLog;

import java.util.List;

public interface FailureLogService {
    List<FailureLog> getAllFailures();
}
