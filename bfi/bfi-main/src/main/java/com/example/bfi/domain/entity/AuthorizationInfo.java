package com.example.bfi.domain.entity;

import com.example.bfi.domain.dto.enumeration.AllowedType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Token 모듈
 *  토큰 인증
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorizationInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @Field("id")
    private String id;

    @Field("allowed")
    private AllowedType allowed;

    @Field("token")
    private Token token;

    @Field("token")
    private LocationReferences locationReferences;

    @Size(max = 36)
    @Field("authorization_reference")
    private String authorizationReference;

    @Field("info")
    private DisplayText info;


}
