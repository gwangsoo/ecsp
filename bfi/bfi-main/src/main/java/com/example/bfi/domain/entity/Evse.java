package com.example.bfi.domain.entity;

import com.example.bfi.domain.dto.enumeration.EvseCapability;
import com.example.bfi.domain.dto.enumeration.EvseStatus;
import com.example.bfi.domain.dto.enumeration.ParkingRestriction;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * 충전기
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Document(collection = "evse")
public class Evse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 36)
    @Field("uid")
    private String uid;

    @Size(max = 48)
    @Field("evse_id")
    private String evseId;

    @Field("status")
    private EvseStatus status;

    @Field("status_schedule")
    private StatusSchedule statusSchedule;

    @Builder.Default
    @Field("capabilities")
    private Set<EvseCapability>  evseCapability = new HashSet<>();

    @Builder.Default
    @Field("connector")
    private Set<Connector> connectors = new HashSet<>();

    @Size(max = 4)
    @Field("floor_level")
    private String floorLevel;

    @Field("coordinates")
    private GeoLocation coordinates;

    @Size(max = 16)
    @Field("physical_reference")
    private String physicalReference;

    @Builder.Default
    @Field("directions")
    private Set<DisplayText> directions = new HashSet<>();

    @Field("parking_restrictions")
    private ParkingRestriction parkingRestriction;

    @Builder.Default
    @Field("images")
    private Set<Image> images = new HashSet<>();

    @Field("last_updated")
    private Instant lastUpdated;
}
