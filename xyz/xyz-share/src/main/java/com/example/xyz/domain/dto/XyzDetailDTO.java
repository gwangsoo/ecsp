package com.example.xyz.domain.dto;

import com.example.ecsp.common.jpa.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Schema(description = "XYZ 서비스")
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class XyzDetailDTO extends AbstractAuditingDTO<String> implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Size(max = 16)
    private String id;

    @Size(max = 32)
    @Schema(description = "속성명")
    private String attrName;

    @Size(max = 256)
    @Schema(description = "속성값")
    private String attrValue;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "XYZ")
    private XyzDTO xyz;
}
