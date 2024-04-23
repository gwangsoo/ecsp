package com.example.bfi.domain.dto;

import com.example.bfi.domain.dto.enumeration.CommandResultType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 *
 */
@Schema(description = "커맨드")
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommandDTO extends AbstractAuditingDTO<String> implements Serializable {

    @NotNull
    @Size(max = 36)
    private String id;

    private CommandResultType result;

    private Integer timeout;

    @Size(max = 512)
    private String message;
}
