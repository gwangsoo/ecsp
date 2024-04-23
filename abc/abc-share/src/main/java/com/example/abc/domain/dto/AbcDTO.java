package com.example.abc.domain.dto;

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
public class AbcDTO extends AbstractAuditingDTO<String> implements Serializable {
    private static final long serialVersionUID = 1L;

    public static enum AbcStatus {
        OPEN,
        CLOSE
    }

//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Size(max = 16)
    private String id;

    @Size(max = 256)
    @Schema(description = "데이타", required = true)
    private String data;

    @Min(value = 0L)
    @Max(value = 300L)
    @Schema(description = "사이즈")
    private Long size;

    @Schema(description = "상태 (OPEN/CLOSE)")
    private AbcStatus status;

    // TODO 마이크로서비스간 연계 방법 고려해야 함.
    private String tenantId;
}
