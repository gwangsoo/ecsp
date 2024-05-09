package com.example.bfi.domain.dto;

import com.example.bfi.domain.dto.enumeration.EnvironmentalImpactCategory;
import jakarta.validation.constraints.Size;
import lombok.*;

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
public class EnergyMixDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 36)
    private String id;
    private Boolean isGreenEnergy;
    private EnergySourceDTO energySource;
    private EnvironmentalImpactCategory environImpact;
    @Size(max = 64)
    private String supplierName;
    @Size(max = 64)
    private String energyProductName;
}
