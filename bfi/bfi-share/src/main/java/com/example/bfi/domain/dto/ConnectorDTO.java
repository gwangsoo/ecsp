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
 * A DTO for the {@link com.example.bfi.domain.entity.Connector} entity.
 */
@Schema(description = "컨넥터")
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConnectorDTO extends AbstractAuditingDTO<String> implements Serializable {

    @NotNull
    @Size(max = 36)
    private String id;

    private Integer maxVoltage;

    private Integer maxAmperage;

    private Integer maxElectricPower;

    @Size(max = 36)
    private String tariffIds;

    private ZonedDateTime lastUpdated;
}
