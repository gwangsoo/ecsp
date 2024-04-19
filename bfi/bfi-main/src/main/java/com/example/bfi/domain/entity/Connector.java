package com.example.bfi.domain.entity;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

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

    @Field("max_voltage")
    private Integer maxVoltage;

    @Field("max_amperage")
    private Integer maxAmperage;

    @Field("max_electric_power")
    private Integer maxElectricPower;

    @Size(max = 36)
    @Field("tariff_ids")
    private String tariffIds;

    @Field("last_updated")
    private ZonedDateTime lastUpdated;

//    @DBRef
//    @Field("evse")
//    @JsonIgnoreProperties(value = { "connectors", "location" }, allowSetters = true)
//    private Evse evse;
}
