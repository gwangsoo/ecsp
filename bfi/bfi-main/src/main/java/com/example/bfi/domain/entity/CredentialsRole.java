package com.example.bfi.domain.entity;

import com.example.bfi.domain.dto.enumeration.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * CredentialsRole
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CredentialsRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    @Id
    private String id;

    /**
     * Type of role.
     */
    @Field("role")
    private Role role;

    /**
     * Details of this party.
     */
    @Field("business_details")
    private BusinessDetails businessDetails;

    /**
     * CPO, eMSP (or other role) ID of this party (following the ISO-15118 standard).
     */
    @Size(max = 36)
    @Field("party_id")
    private String partyId;

    /**
     * ISO-3166 alpha-2 country code of the country this party is operating in.
     */
    @Size(max = 2)
    @Field("country_code")
    private String countryCode;
}
