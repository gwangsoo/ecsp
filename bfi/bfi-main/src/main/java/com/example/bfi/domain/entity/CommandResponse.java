package com.example.bfi.domain.entity;

import com.example.bfi.domain.dto.enumeration.CommandResponseType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Commands 모듈
 *  Command 응답
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommandResponse implements Serializable {

    @Id
    @NotNull
    @Field("id")
    private String id;

    @Field("result")
    private CommandResponseType result;

    @Field("timeout")
    private Integer timeout;

    @Field("message")
    private DisplayText message;

}
