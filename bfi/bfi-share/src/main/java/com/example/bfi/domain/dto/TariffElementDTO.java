package com.example.bfi.domain.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * TariffElement
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TariffElementDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 36)
    private String id;

    private Set<PriceComponentDTO> priceComponentDTOS = new HashSet<>();

    private TariffRestrictions restrictions;
}
