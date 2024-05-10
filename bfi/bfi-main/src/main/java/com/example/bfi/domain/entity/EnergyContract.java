package com.example.bfi.domain.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Token 모듈
 *  토큰에 속한 에너지 계약 정보
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "supplierName")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnergyContract implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Size(max=64)
    @Field("supplier_name")
    private String supplierName;

    @Size(max=64)
    @Field("contract_id")
    private String contractId;

}