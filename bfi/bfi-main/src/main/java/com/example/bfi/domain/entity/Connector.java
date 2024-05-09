package com.example.bfi.domain.entity;

import com.example.bfi.domain.dto.enumeration.ConnectorFormat;
import com.example.bfi.domain.dto.enumeration.ConnectorType;
import com.example.bfi.domain.dto.enumeration.PowerType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.Instant;

/**
 * 컨넥터
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Document(collection = "connector")
public class Connector implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    @Id
    private String id;

    @Field("connectorType")
    private ConnectorType connectorType;

    @Field("format")
    private ConnectorFormat format;

    @Field("power_type")
    private PowerType powerType;

    @Field("max_voltage")
    private Integer maxVoltage;

    @Field("max_amperage")
    private Integer maxAmperage;

    @Field("max_electric_power")
    private Integer maxElectricPower;

    @Size(max = 36)
    @Field("tariff_ids")
    private String tariffIds;

    @Size(max = 255)
    @Field("terms_and_conditions")
    private String termsAndConditions;

    @Field("last_updated")
    private Instant lastUpdated;

}
