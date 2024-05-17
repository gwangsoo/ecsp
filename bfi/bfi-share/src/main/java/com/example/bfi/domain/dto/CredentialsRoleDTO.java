package com.example.bfi.domain.dto;

import com.example.bfi.domain.dto.enumeration.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * CredentialsRole
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CredentialsRoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    private String id;

    /**
     * Type of role.
     */
    private Role role;

    /**
     * Details of this party.
     */
    private BusinessDetailsDTO businessDetails;

    /**
     * CPO, eMSP (or other role) ID of this party (following the ISO-15118 standard).
     */
    @Size(max = 36)
    private String partyId;

    /**
     * ISO-3166 alpha-2 country code of the country this party is operating in.
     */
    @Size(max = 2)
    private String countryCode;
}
