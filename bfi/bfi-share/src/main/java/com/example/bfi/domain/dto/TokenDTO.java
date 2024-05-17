package com.example.bfi.domain.dto;

import com.example.bfi.domain.dto.enumeration.ProfileType;
import com.example.bfi.domain.dto.enumeration.WhitelistType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * Token 모듈
 *  토큰
 */
@Schema(description = "토큰")
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDTO extends AbstractAuditingDTO<String> implements Serializable {
    @NotNull
    @Size(max = 36)
    private String id;

    @Size(max = 2)
    private String countryCode;

    @Size(max = 3)
    private String partyId;

    @Size(max = 36)
    private String uid;

    private String tokenType;

    @Size(max = 36)
    private String contractId;

    @Size(max = 64)
    private String visualNumber;

    @Size(max = 64)
    private String issuer;

    @Size(max = 36)
    private String groupId;

    @Size(max = 36)
    private Boolean valid;

    private WhitelistType whitelistType;

    @Size(max = 2)
    private String language;

    private ProfileType profileType;

    private EnergyContractDTO energyContract;

    private Instant lastUpdated;

}
