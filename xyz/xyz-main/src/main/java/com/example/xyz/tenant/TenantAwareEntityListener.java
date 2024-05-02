package com.example.xyz.tenant;

import com.example.ecsp.common.jpa.TenantAware;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TenantAwareEntityListener {

    private final TenantIdentifierResolver tenantIdentifierResolver;

//    @PrePersist @PreUpdate @PreRemove
    @PostLoad
    @PostPersist @PostRemove @PostUpdate
    public void checkTenant(Object entity) {
        if (!(entity instanceof TenantAware)) return;
        String currentTenantIdentifier = tenantIdentifierResolver.resolveCurrentTenantIdentifier();
        if (!currentTenantIdentifier.equals(((TenantAware) entity).getTenantId())) {
            log.warn("Entity's tenantId does not match current tenant: currentTenantIdentifier={}, entity={}", currentTenantIdentifier, entity);
            throw new IllegalArgumentException("Entity's tenantId does not match current tenant");
        }
    }
}