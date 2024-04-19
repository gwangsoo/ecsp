package com.example.bfi.domain.dto;

import com.example.bfi.domain.dto.enumeration.EvseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.example.bfi.domain.entity.Evse} entity.
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

    @Size(max = 256)
    private String directions;

    private ZonedDateTime lastUpdated;

    private Set<ConnectorDTO> connectors = new HashSet<>();
}
