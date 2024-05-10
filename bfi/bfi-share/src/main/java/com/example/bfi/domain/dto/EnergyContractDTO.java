package com.example.bfi.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * Token 모듈
 *  토큰에 속한 에너지 계약 정보
 */
@Schema(description = "토큰")
@Data
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnergyContractDTO implements Serializable {

    @Size(max=64)
    private String supplierName;

    @Size(max=64)
    private String contractId;
}