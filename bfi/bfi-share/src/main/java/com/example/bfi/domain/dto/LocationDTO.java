package com.example.bfi.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.example.bfi.domain.entity.Location} entity.
 */
@Schema(description = "충전소")
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDTO extends AbstractAuditingDTO<String> implements Serializable {

    @NotNull
    @Size(max = 36)
    private String id;

    @Size(max = 2)
    private String countryCode;

    @Size(max = 36)
    private String partyId;

    private Boolean publish;

    @Size(max = 255)
    private String name;

    @Size(max = 45)
    private String address;

    @Size(max = 45)
    private String city;

    @Size(max = 10)
    private String postalCode;

    @Size(max = 20)
    private String state;

    @Size(max = 3)
    private String country;

    @Size(max = 255)
    private String timeZone;

    private Boolean chargingWhenClosed;

    private ZonedDateTime lastUpdated;

    private Set<EvseDTO> evses = new HashSet<>();
}
