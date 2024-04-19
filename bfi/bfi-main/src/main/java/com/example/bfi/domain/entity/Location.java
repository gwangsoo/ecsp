package com.example.bfi.domain.entity;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

    @Size(max = 255)
    @Field("time_zone")
    private String timeZone;

    @Field("charging_when_closed")
    private Boolean chargingWhenClosed;

    @Field("last_updated")
    private ZonedDateTime lastUpdated;

    @Builder.Default
//    @DBRef
    @Field("evse")
//    @JsonIgnoreProperties(value = { "connectors", "location" }, allowSetters = true)
    private Set<Evse> evses = new HashSet<>();
}
