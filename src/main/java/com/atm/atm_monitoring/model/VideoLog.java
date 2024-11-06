package com.atm.atm_monitoring.model;

import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class VideoLog {
    @JsonIgnore
    private String atmId;
    private String transactionId;
    private String customerId;
    private String videoUrl;
    private Instant timestamp;

    public VideoLog(String atmId, String transactionId, String customerId, String videoUrl, Instant timestamp) {
        this.atmId = atmId;
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.videoUrl = videoUrl;
        this.timestamp = timestamp;
    }

    public VideoLog() {
    }

    public String getAtmId() {
        return atmId;
    }

    public void setAtmId(String atmId) {
        this.atmId = atmId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
