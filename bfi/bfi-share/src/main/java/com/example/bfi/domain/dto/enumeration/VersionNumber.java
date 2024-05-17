package com.example.bfi.domain.dto.enumeration;

/**
 * VersionNumber
 */
public enum VersionNumber {
    /**
     * OCPI version 2.0
     */
    V_2_0("2.0.0"),
    /**
     * OCPI version 2.1 (DEPRECATED, do not use, use 2.1.1 instead)
     */
    V_2_1("2.0.1"),
    /**
     * OCPI version 2.1.1
     */
    V_2_1_1("2.1.1"),
    /**
     * OCPI version 2.2 (DEPRECATED, do not use, use 2.2.1 instead)
     */
    V_2_2("2.2.0"),
    /**
     * OCPI version 2.2.1 (this version)
     */
    V_2_2_1("2.2.1");

    private final String value;

    VersionNumber(String value){
        this.value=this.name();
    }
}
