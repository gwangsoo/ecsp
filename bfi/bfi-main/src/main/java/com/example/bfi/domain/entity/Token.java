package com.example.bfi.domain.entity;

import com.example.bfi.domain.dto.enumeration.ProfileType;
import com.example.bfi.domain.dto.enumeration.TokenType;
import com.example.bfi.domain.dto.enumeration.WhitelistType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.Instant;

/**
 * Token 모듈
 *  토큰
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max=36)
    @Id
    @Field("id")
    private String id;

    @Size(min = 2)
    @Field("country_code")
    private String countryCode;

    @Size(max = 3)
    @Field("party_id")
    private String partyId;

    @Size(max = 36)
    @Field("uid")
    private String uid;

    @Field("type")
    private TokenType tokenType;

    @Size(max = 36)
    @Field("contract_id")
    private String contractId;

    @Size(max = 64)
    @Field("visual_number")
    private String visualNumber;

    @Size(max = 64)
    @Field("issuer")
    private String issuer;

    @Size(max = 36)
    @Field("group_id")
    private String groupId;

    @Field("valid")
    private Boolean valid;

    @Field("whitelist")
    private WhitelistType whitelistType;

    @Size(max = 2)
    @Field("language")
    private String language;

    @Field("default_profile_type")
    private ProfileType profileType;

    @Field("energy_contract")
    private EnergyContract energyContract;

    @Field("last_updated")
    private Instant lastUpdated;
}
