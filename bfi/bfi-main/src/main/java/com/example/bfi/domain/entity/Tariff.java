package com.example.bfi.domain.entity;

import com.example.bfi.domain.dto.EnergyMixDTO;
import com.example.bfi.domain.dto.enumeration.TariffType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
@Document(collection = "tariff")
public class Tariff implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    @Id
    private String id;

    @Size(max = 2)
    @Field("country_code")
    private String countryCode;

    @Size(max = 36)
    @Field("party_id")
    private String partyId;

    @Size(max = 3)
    @Field("currency")
    private String currency;

    @Field("type")
    private TariffType tariffType;

    @Field("tariff_alt_text")
    private Set<DisplayText> tariffAltTexts = new HashSet<>();

    @Size(max = 255)
    @Field("tariff_alt_url")
    private String tariffAltUrl;

    @Field("min_price")
    private Price minPrice;

    @Field("max_price")
    private Price maxPrice;

    @Field("elements")
    private Set<TariffElement> elements = new HashSet<>();

    @Field("start_date_time")
    private Instant startDateTime;

    @Field("end_date_time")
    private Instant endDateTime;

    private EnergyMixDTO energyMix;

    @Field("last_updated")
    private Instant lastUpdated;
}
