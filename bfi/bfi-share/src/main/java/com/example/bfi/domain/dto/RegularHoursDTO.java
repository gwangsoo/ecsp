package com.example.bfi.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * RegularHours
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Document(collection = "regularHours")
public class RegularHoursDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    private String id;

    @Size(max = 1)
    private Integer weekday;

    @Size(max = 5)
    private String periodBegin;

    @Size(max = 5)
    private String periodEnd;
}
