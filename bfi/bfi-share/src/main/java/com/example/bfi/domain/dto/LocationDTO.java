package com.example.bfi.domain.dto;

import com.example.bfi.domain.dto.enumeration.Facility;
import com.example.bfi.domain.dto.enumeration.ParkingType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 *
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

    private Instant lastUpdated;

    @Builder.Default
    private Set<EvseDTO> evses = new HashSet<>();

    private PublishTokenTypeDTO publishTokenType;

    private GeoLocationDTO coordinates;

    private AdditionalGeoLocationDTO relatedLocations;

    private ParkingType parkingType;

    @Builder.Default
    private Set<DisplayTextDTO> directions = new HashSet<>();

    private BusinessDetailsDTO operator;
    private BusinessDetailsDTO suboperator;
    private BusinessDetailsDTO owner;

    @Builder.Default
    private Set<Facility> facilities = new HashSet<>();

    @Builder.Default
    private Set<HoursDTO> openingTimes = new HashSet<>();
    private EnergyMixDTO energyMix;
}
