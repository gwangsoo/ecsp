package com.example.bfi.domain.dto.enumeration;

/**
 * ModuleID
 */
public enum ModuleID {
    CDRS("cdrs"),
    CHARGING_PROFILES("chargingprofiles"),
    COMMANDS("commands"),
    /**
     * Required for all implementations.
     * The role field has no function for this module.
     */
    CREDENTIALS("credentials"),
    HUB_CLIENT_INFO("hubclientinfo"),
    LOCATIONS("locations"),
    SESSIONS("sessions"),
    TARIFFS("tariffs"),
    TOKENS("tokens");

    private final String value;

    ModuleID(String value) {
        this.value = value;
    }

}
