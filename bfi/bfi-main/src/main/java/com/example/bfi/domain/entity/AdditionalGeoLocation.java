package com.example.bfi.domain.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * AdditionalGeoLocation
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Document(collection = "additionalGeoLocation")
public class AdditionalGeoLocation implements Serializable {

//    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    @Id
    private String id;

    @Size(max = 10)
    @Field("latitude")
    private String latitude;

    @Size(max = 11)
    @Field("longitude")
    private String longitude;

    @Field("name")
    private DisplayText displayText;
}
