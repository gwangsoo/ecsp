package com.example.bfi.domain.dto;

import com.example.bfi.domain.dto.enumeration.EvseStatus;
import jakarta.validation.constraints.Size;
import lombok.*;

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
public class StatusScheduleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 36)
    private String id;

    private Instant periodBegin;

    private Instant periodEnd;

    private EvseStatus status;
}
