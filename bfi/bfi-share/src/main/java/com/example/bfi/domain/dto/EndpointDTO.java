package com.example.bfi.domain.dto;

import com.example.bfi.domain.dto.enumeration.InterfaceRole;
import com.example.bfi.domain.dto.enumeration.ModuleID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * EndpointDTO
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EndpointDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max=36)
    private String id;

    private ModuleID identifier;

    private InterfaceRole role;

    @Size(max = 255)
    private String url;
}
