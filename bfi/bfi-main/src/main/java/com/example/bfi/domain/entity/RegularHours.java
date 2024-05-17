package com.example.bfi.domain.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * RegularHours
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Document(collection = "regularHours")
public class RegularHours implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    @Id
    @Field("uid")
    private String id;

    @Size(max = 1)
    @Field("weekday")
    private Integer weekday;

    @Size(max = 5)
    @Field("period_begin")
    private String periodBegin;

    @Size(max = 5)
    @Field("period_end")
    private String periodEnd;
}
