package com.example.bfi.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * 위치토큰
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Document(collection = "publishTokenType")
public class PublishTokenTypeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    private String id;

    private String tokenType;

    @Size(max = 64)
    private String partyId;

    @Size(max = 64)
    private String issuer;

    @Size(max = 36)
    private String name;
}
