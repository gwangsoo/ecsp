package com.example.bfi.domain.dto;

import com.example.bfi.domain.dto.enumeration.TariffDimensionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * TariffElement
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceComponentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    private String id;
    private TariffDimensionType tariffDimensionType;
    private Double price;
    private Double inclVat;
    private Integer stepSize;
}
