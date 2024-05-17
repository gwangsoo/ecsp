package com.example.bfi.domain.entity;

import com.example.bfi.domain.dto.enumeration.EnergySourceCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * EnvironmentalImpact
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Document(collection = "environmentalImpact")
public class EnvironmentalImpact implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    @Id
    private String id;

    @Field("source")
    private EnergySourceCategory category;

    @Field("amount")
    private Integer amount;
}
