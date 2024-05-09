package com.example.bfi.domain.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * AdditionalGeoLocation
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Document(collection = "additionalGeoLocation")
public class AdditionalGeoLocationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 36)
    private String id;

    @Size(max = 10)
    private String latitude;

    @Size(max = 11)
    private String longitude;

    private DisplayTextDTO displayText;
}
