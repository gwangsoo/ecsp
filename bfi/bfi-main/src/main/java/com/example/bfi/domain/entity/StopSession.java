package com.example.bfi.domain.entity;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Commands 모듈
 *  충전 종료 명령
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "responseUrl")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StopSession implements Serializable {
    @Id
    @NotNull
    @Field("response_url")
    private String responseUrl;

    @Field("location_id")
    private String locationId;

    @Field("evseUid")
    private String evseUid;

    @Field("connector_id")
    private String connectorId;
}
