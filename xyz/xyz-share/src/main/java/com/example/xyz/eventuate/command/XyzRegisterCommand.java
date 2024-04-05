package com.example.xyz.eventuate.command;

import com.example.xyz.domain.dto.XyzDTO;
import io.eventuate.tram.commands.common.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XyzRegisterCommand implements Command {
    private XyzDTO xyz;
}
