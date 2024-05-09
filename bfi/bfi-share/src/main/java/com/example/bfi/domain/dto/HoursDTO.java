package com.example.bfi.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Hours
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Document(collection = "hours")
public class HoursDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    private String id;
    private Boolean twentyfourseven;

    @Builder.Default
    private Set<RegularHoursDTO> regularHours = new HashSet<>();

    @Size(max = 64)
    private String partyId;

    @Size(max = 64)
    private String issuer;
}
