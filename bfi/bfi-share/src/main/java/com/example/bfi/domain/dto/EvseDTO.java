package com.example.bfi.domain.dto;

import com.example.bfi.domain.dto.enumeration.EvseCapability;
import com.example.bfi.domain.dto.enumeration.EvseStatus;
import com.example.bfi.domain.dto.enumeration.ParkingRestriction;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.example.bfi.domain.entity} entity.
 */
@Schema(description = "충전기")
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvseDTO extends AbstractAuditingDTO<String> implements Serializable {

    private String id;

    @NotNull
    @Size(max = 36)
    private String uid;

    @Size(max = 48)
    private String evseId;

    private EvseStatus status;

    private Instant lastUpdated;

    @Builder.Default
    private Set<ConnectorDTO> connectors = new HashSet<>();

    private StatusScheduleDTO statusSchedule;

    @Builder.Default
    private Set<EvseCapability> evseCapability = new HashSet<>();

    @Size(max = 4)
    private String floorLevel;

    private GeoLocationDTO coordinates;

    @Size(max = 16)
    private String physicalReference;

    @Builder.Default
    private Set<DisplayTextDTO> directions = new HashSet<>();

    private ParkingRestriction parkingRestriction;

    @Builder.Default
    private Set<ImageDTO> images = new HashSet<>();
}
