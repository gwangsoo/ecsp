package com.example.bfi.domain.entity;

import com.example.bfi.domain.dto.enumeration.EvseStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.Instant;

/**
 * 위치토큰
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Document(collection = "statusSchedule")
public class StatusSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    @Id
    @Field("id")
    private String id;

    @Field("period_begin")
    private Instant periodBegin;

    @Field("period_end")
    private Instant periodEnd;

    @Field("status")
    private EvseStatus status;
}
