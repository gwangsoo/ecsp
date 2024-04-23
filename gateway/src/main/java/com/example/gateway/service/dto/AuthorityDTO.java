package com.example.gateway.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * An authority (a security role) used by Spring Security.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorityDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
}
