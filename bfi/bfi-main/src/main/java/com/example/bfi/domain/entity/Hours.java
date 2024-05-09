package com.example.bfi.domain.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Hours
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Document(collection = "hours")
public class Hours implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    @Id
    @Field("uid")
    private String id;

    @Field("twentyfourseven")
    private Boolean twentyfourseven;

    @Builder.Default
    @Field("regular_hours")
    private Set<RegularHours> regularHours = new HashSet<>();

    @Size(max = 64)
    @Field("exceptional_openings")
    private String partyId;

    @Size(max = 64)
    @Field("exceptional_closings")
    private String issuer;
}
