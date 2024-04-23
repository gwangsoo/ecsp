package com.example.bfi.domain.dto.enumeration;

/**
 * CommandResultType
 */
public enum CommandResultType {
    ACCEPTED,
    CANCELED_RESERVATION,
    EVSE_OCCUPIED,
    EVSE_INOPERATIVE,
    FAILED,
    NOT_SUPPORTED,
    REJECTED,
    TIMEOUT,
    UNKNOWN_RESERVATION
}
