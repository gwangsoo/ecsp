package com.example.bfi.domain.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.Instant;

/**
 * ExceptionalPeriod
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Document(collection = "ExceptionalPeriod")
public class ExceptionalPeriod implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    @Id
    @Field("uid")
    private String id;

    @Field("period_begin")
    private Instant weekday;

    @Field("period_end")
    private Instant periodBegin;
}
