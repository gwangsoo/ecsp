package com.example.bfi.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Credentials
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CredentialsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    private String id;

    /**
     * Case Sensitive, ASCII only. The credentials token for the other party to authenticate in your system.
     * Not encoded in Base64 or any other encoding.
     */
    @Size(max = 64)
    private String token;

    /**
     * The URL to your API versions endpoint.
     */
    @Size(max = 255)
    private String url;

    /**
     * Set of the roles this party provides.
     */
    private Set<CredentialsRoleDTO> roles = new HashSet<>();
}
