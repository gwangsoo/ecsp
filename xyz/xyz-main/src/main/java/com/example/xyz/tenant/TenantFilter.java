package com.example.xyz.tenant;

import com.example.ecsp.common.jpa.TenantContext;
import com.example.xyz.security.SecurityUtils;
import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@Order(1)
class TenantFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

//        HttpServletRequest req = (HttpServletRequest) request;
//        String tenantName = req.getHeader("X-TenantID");

        String tenant = SecurityUtils.getCurrentTenant().orElse(null);
        log.info("tenant={}", tenant);

        TenantContext.setCurrentTenant(tenant);

        try {
            chain.doFilter(request, response);
        } finally {
            TenantContext.setCurrentTenant("");
        }
    }
}