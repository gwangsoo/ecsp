package com.example.bfi.domain.entity;

import com.example.bfi.domain.dto.enumeration.EvseStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

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

    @Size(max = 256)
    @Field("directions")
    private String directions;

    @Field("last_updated")
    private ZonedDateTime lastUpdated;

    @Builder.Default
//    @DBRef
    @Field("connector")
//    @JsonIgnoreProperties(value = { "evse" }, allowSetters = true)
    private Set<Connector> connectors = new HashSet<>();

//    @DBRef
//    @Field("location")
//    @JsonIgnoreProperties(value = { "evses" }, allowSetters = true)
//    private Location location;
}
