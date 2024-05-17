package com.example.bfi.domain.dto;

import com.example.bfi.domain.dto.enumeration.TariffType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * 요금제
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TariffDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    private String id;

    @Size(max = 2)
    private String countryCode;

    @Size(max = 36)
    private String partyId;

    @Size(max = 3)
    private String currency;

    private TariffType tariffType;

    private Set<DisplayTextDTO> tariffAltTexts = new HashSet<>();

    @Size(max = 255)
    private String tariffAltUrl;

    private PriceDTO minPriceDTO;

    private PriceDTO maxPriceDTO;

    private Set<TariffElementDTO> elements = new HashSet<>();

    private Instant startDateTime;

    private Instant endDateTime;

    private EnergyMixDTO energyMix;

    private Instant lastUpdated;
}
