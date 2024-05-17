package com.example.bfi.domain.entity;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.Instant;

/**
 * Commands 모듈
 *  기존 예약 취소
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "responseUrl")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReserveNow implements Serializable {
    @Id
    @NotNull
    @Field("response_url")
    private String responseUrl;

    @Field("token")
    private Token token;

    @Field("expiry_date")
    private Instant expiryDate;

    @Field("reservation_id")
    private String reservationId;

    @Field("location_id")
    private String locationId;

    @Field("evseUid")
    private String evseUid;

    @Field("authorization_reference")
    private String authorizationReference;


}
