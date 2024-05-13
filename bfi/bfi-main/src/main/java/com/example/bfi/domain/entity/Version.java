package com.example.bfi.domain.entity;

import com.example.bfi.domain.dto.enumeration.VersionNumber;
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
 * version
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "version")
public class Version implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    @Id
    private String id;

    @Size(min = 2)
    @Field("country_code")
    private String countryCode;

    @Size(max = 3)
    @Field("party_id")
    private String partyId;

    @Field("version")
    private VersionNumber version;

    @Size(max = 255)
    @Field("url")
    private String url;

    @Field("endpoints")
    private Set<Endpoint> endpoints = new HashSet<>();
}
