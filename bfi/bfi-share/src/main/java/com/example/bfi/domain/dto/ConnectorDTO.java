package com.example.bfi.domain.dto;

import com.example.bfi.domain.dto.enumeration.ConnectorFormat;
import com.example.bfi.domain.dto.enumeration.ConnectorType;
import com.example.bfi.domain.dto.enumeration.PowerType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

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

    private Instant lastUpdated;

    private ConnectorType connectorType;

    private ConnectorFormat format;

    private PowerType powerType;

    @Size(max = 255)
    private String termsAndConditions;
}
