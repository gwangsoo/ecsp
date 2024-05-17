package com.example.bfi.domain.dto;

import com.example.bfi.domain.dto.enumeration.EnergySourceCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * EnergySource
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Document(collection = "energySource")
public class EnergySourceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    private String id;
    private EnergySourceCategory source;
    private Integer percentage;
}
