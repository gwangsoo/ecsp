package com.example.orders.domain.dto;

import com.example.orders.domain.entity.Orders;
import com.example.ecsp.common.jpa.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Schema(description = "ABC 서비스")
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersDTO extends AbstractAuditingDTO<String> implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Size(max = 16)
    private String id;

    @Size(max = 256)
    @Schema(description = "상품ID", required = true)
    private String productId;

    @Size(max = 256)
    @Schema(description = "상품명", required = true)
    private String productName;

    @Min(value = 0L)
    @Max(value = 999L)
    @Schema(description = "개수")
    private Long size;

    @Schema(description = "상태 (ACCEPTED/REJECTED/APPROVED)")
    private Orders.OrdersStatus status;
}
