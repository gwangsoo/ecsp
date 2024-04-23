package com.example.ecsp.common.jpa;

import java.util.Optional;

public class TenantContext {
    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    public static Optional<String> getCurrentTenant() {
        return Optional.ofNullable(CURRENT_TENANT.get());
    }

    public static void setCurrentTenant(String tenant) {
        CURRENT_TENANT.set(tenant);
    }
}
