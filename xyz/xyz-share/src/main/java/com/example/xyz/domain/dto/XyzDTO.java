package com.example.xyz.domain.dto;

import com.example.common.jpa.AbstractAuditingDTO;
import com.example.xyz.domain.entity.Xyz;
import com.example.xyz.domain.entity.XyzDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Schema(description = "XYZ 서비스")
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class XyzDTO extends AbstractAuditingDTO<String> implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Size(max = 16)
    private String id;

    @Size(max = 256)
    @Schema(description = "이름")
    private String name;

    @Min(value = 0L)
    @Max(value = 150L)
    @Schema(description = "나이")
    private Long age;

    @Schema(description = "상태 (ACTIVE/STANDBY/DEACTIVE)")
    private Xyz.XyzStatus status;

    private Set<XyzDetailDTO> xyzDetails;
}
