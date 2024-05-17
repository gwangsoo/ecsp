package com.example.bfi.domain.dto.enumeration;


public enum Role {
    /**
     * Charge Point Operator Role.
     */
    CPO("CPO"),
    /**
     * eMobility Service Provider Role.
     */
    EMSP("EMSP"),
    /**
     * Hub role.
     */
    HUB("HUB"),
    /**
     * National Access Point Role (national Database with all Location information of a country).
     */
    NAP("NAP"),
    /**
     * Navigation Service Provider Role, role like an eMSP (probably only interested in Location information).
     */
    NSP("NSP"),
    /**
     * Other role.
     */
    OTHER("OTHER"),
    /**
     * Smart Charging Service Provider Role.
     */
    SCSP("SCSP");
    private final String value;

    Role(String value) {
        this.value = value;
    }

}
