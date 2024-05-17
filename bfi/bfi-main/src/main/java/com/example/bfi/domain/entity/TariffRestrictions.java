package com.example.bfi.domain.entity;

import com.example.bfi.domain.dto.enumeration.DayOfWeek;
import com.example.bfi.domain.dto.enumeration.ReservationRestrictionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

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

    @NotNull
    @Size(max = 36)
    @Id
    @Field("id")
    private String id;

    @Size(max = 5)
    @Field("start_time")
    private String startTime;

    @Size(max = 5)
    @Field("end_time")
    private String endTime;

    @Size(max = 10)
    @Field("start_date")
    private String startDate;

    @Size(max = 10)
    @Field("end_date")
    private String endDate;

    @Field("min_kwh")
    private double minKwh;

    @Field("max_kwh")
    private double maxKwh;

    @Field("min_current")
    private double minCurrent;

    @Field("max_current")
    private double maxCurrent;

    @Field("min_power")
    private double minPower;

    @Field("max_power")
    private double maxPower;

    @Field("min_duration")
    private Integer minDuration;

    @Field("max_duration")
    private Integer maxDuration;

    @Field("day_of_week")
    private DayOfWeek dayOfWeek;

    @Field("reservation")
    private ReservationRestrictionType reservation;
}
