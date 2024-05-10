package com.example.bfi.domain.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Token 모듈
 *  위치 상세 참조
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "locationId")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationReferences implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @Size(max = 36)
    @Field("location_id")
    private String locationId;

    @Size(max = 36)
    @Field("evse_uids")
    private String evseUids;
}
