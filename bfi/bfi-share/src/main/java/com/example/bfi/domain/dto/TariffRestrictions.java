package com.example.bfi.domain.dto;

import com.example.bfi.domain.dto.enumeration.DayOfWeek;
import com.example.bfi.domain.dto.enumeration.ReservationRestrictionType;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * TariffRestrictions
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TariffRestrictions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 36)
    private String id;

    @Size(max = 5)
    private String startTime;

    @Size(max = 5)
    private String endTime;

    @Size(max = 10)
    private String startDate;

    @Size(max = 10)
    private String endDate;
    private double minKwh;
    private double maxKwh;
    private double minCurrent;
    private double maxCurrent;
    private double minPower;
    private double maxPower;
    private Integer minDuration;
    private Integer maxDuration;
    private DayOfWeek dayOfWeek;
    private ReservationRestrictionType reservation;
}
