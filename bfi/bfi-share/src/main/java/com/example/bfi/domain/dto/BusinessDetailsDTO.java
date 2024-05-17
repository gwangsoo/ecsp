package com.example.bfi.domain.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * BusinessDetails
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Document(collection = "businessDetails")
public class BusinessDetailsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 36)
    private String id;

    @Size(max = 100)
    private String name;

    @Size(max = 255)
    private String website;

    private ImageDTO logo;
}
