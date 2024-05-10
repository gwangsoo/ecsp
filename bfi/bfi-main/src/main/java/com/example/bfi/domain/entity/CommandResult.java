package com.example.bfi.domain.entity;

import com.example.bfi.domain.dto.enumeration.CommandResultType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Commands 모듈
 *  Command 결과
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommandResult implements Serializable {
    @NotNull
    @Id
    @Field("id")
    private String id;

    @Field("result")
    private CommandResultType result;

    @Field("message")
    private DisplayText message;


}
