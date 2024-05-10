package com.example.bfi.domain.entity;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.Instant;

/**
 * Commands 모듈
 *  새 세션 시작
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "responseUrl")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StartSession implements Serializable {
    @Id
    @NotNull
    @Field("response_url")
    private String responseUrl;

    @Field("token")
    private Token token;

    @Field("location_id")
    private String locationId;

    @Field("evseUid")
    private String evseUid;

    @Field("connector_id")
    private String connectorId;

    @Field("authorization_reference")
    private String authorizationReference;

}
