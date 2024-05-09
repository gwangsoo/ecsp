package com.example.bfi.domain.entity;

import com.example.bfi.domain.dto.enumeration.EnvironmentalImpactCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * EnergyMix
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Document(collection = "energyMix")
public class EnergyMix implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    @Id
    private String id;

    @Field("is_green_energy")
    private Boolean isGreenEnergy;

    @Field("energy_sources")
    private EnergySource energySource;

    @Field("environ_impact")
    private EnvironmentalImpactCategory environImpact;

    @Size(max = 64)
    @Field("supplier_name")
    private String supplierName;

    @Size(max = 64)
    @Field("energy_product_name")
    private String energyProductName;
}
