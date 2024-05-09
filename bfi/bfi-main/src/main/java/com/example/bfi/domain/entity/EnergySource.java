package com.example.bfi.domain.entity;

import com.example.bfi.domain.dto.enumeration.EnergySourceCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * EnergySource
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Document(collection = "energySource")
public class EnergySource implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    @Id
    private String id;

    @Field("source")
    private EnergySourceCategory source;

    @Field("percentage")
    private Integer percentage;
}
