package com.example.bfi.domain.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

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
public class PublishTokenType implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    @Id
    @Field("uid")
    private String id;

    @Field("type")
    private String tokenType;

    @Size(max = 64)
    @Field("visual_number")
    private String partyId;

    @Size(max = 64)
    @Field("issuer")
    private String issuer;

    @Size(max = 36)
    @Field("group_id")
    private String name;
}
