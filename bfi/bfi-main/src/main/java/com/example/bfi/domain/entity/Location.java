package com.example.bfi.domain.entity;

import com.example.bfi.domain.dto.enumeration.Facility;
import com.example.bfi.domain.dto.enumeration.ParkingType;
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
 * 충전소
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "location")
public class Location implements Serializable {

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

    @Field("publish")
    private Boolean publish;

    @Field("publish_allowed_to")
    private PublishTokenType publishTokenType;

    @Size(max = 255)
    @Field("name")
    private String name;

    @Size(max = 45)
    @Field("address")
    private String address;

    @Size(max = 45)
    @Field("city")
    private String city;

    @Size(max = 10)
    @Field("postal_code")
    private String postalCode;

    @Size(max = 20)
    @Field("state")
    private String state;

    @Size(max = 3)
    @Field("country")
    private String country;

    @Field("coordinates")
    private GeoLocation coordinates;

    @Field("related_locations")
    private AdditionalGeoLocation relatedLocations;

    @Field("parking_type")
    private ParkingType parkingType;

    @Builder.Default
//    @DBRef
    @Field("evse")
//    @JsonIgnoreProperties(value = { "connectors", "location" }, allowSetters = true)
    private Set<Evse> evses = new HashSet<>();

    @Builder.Default
    @Field("directions")
    private Set<DisplayText> directions = new HashSet<>();

    @Field("operator")
    private BusinessDetails operator;

    @Field("suboperator")
    private BusinessDetails suboperator;

    @Field("owner")
    private BusinessDetails owner;

    @Field("facilities")
    private Set<Facility> facilities;

    @Size(max = 255)
    @Field("time_zone")
    private String timeZone;

    @Builder.Default
    @Field("opening_times")
    private Set<Hours> openingTimes = new HashSet<>();

    @Field("charging_when_closed")
    private Boolean chargingWhenClosed;

    @Field("energy_mix")
    private EnergyMix energyMix;

    @Field("last_updated")
    private Instant lastUpdated;

}
