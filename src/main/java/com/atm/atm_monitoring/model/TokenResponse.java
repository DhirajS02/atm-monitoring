package com.atm.atm_monitoring.model;

import java.time.Instant;

public record TokenResponse(String token, Instant generatedAt) {}

