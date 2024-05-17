package com.example.bfi.domain.dto;

import com.example.bfi.domain.dto.enumeration.VersionNumber;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * VersionDTO
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VersionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    private String id;

    @Size(min = 2)
    private String countryCode;

    @Size(max = 3)
    private String partyId;

    private VersionNumber version;

    @Size(max = 255)
    private String url;

    private Set<EndpointDTO> endpoints = new HashSet<>();
}
