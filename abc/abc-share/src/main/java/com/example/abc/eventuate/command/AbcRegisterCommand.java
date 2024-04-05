package com.example.abc.eventuate.command;

import com.example.abc.domain.dto.AbcDTO;
import io.eventuate.tram.commands.common.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbcRegisterCommand implements Command {
    private AbcDTO abc;
}
