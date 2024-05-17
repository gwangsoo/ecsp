package com.example.bfi.domain.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Commands 모듈
 *  기존 예약 취소
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "responseUrl")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CancelReservation implements Serializable {
    @Id
    @NotNull
    @Field("response_url")
    private String responseUrl;

    @Size(max = 36)
    @Field("reservation_id")
    private String reservationId;

}
