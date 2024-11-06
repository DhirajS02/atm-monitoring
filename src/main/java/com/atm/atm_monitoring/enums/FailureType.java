package com.atm.atm_monitoring.enums;

/**
 * Enum representing the types of failures that can occur in an ATM system.
 */
public enum FailureType {

    /**
     * Failure due to a cash dispenser hardware error. A Hardware error
     */
    CASH_DISPENSER_ERROR,

    /**
     * Failure due to a network timeout.
     */
    NETWORK_TIMEOUT,

    /**
     * Failure due to a card reader hardware error. A Hardware error
     */
    CARD_READER_FAILURE,

    /**
     * Failure due to a printer hardware error. A Hardware error
     */
    PRINTER_ERROR,

    /**
     * Failure due to a system crash.
     */
    SYSTEM_CRASH,

    /**
     * Failure due to a system crash.
     */
    UNKNOWN_ERROR
}

