package com.example.bfi.domain.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
@Document(collection = "credentials")
public class Credentials implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    @Id
    private String id;

    /**
     * Case Sensitive, ASCII only. The credentials token for the other party to authenticate in your system.
     * Not encoded in Base64 or any other encoding.
     */
    @Field("token")
    @Size(max = 64)
    private String token;

    /**
     * The URL to your API versions endpoint.
     */
    @Size(max = 255)
    @Field("url")
    private String url;

    /**
     * Set of the roles this party provides.
     */
    @Field("roles")
    private Set<CredentialsRole> roles = new HashSet<>();
}
