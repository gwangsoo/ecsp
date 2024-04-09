package com.example.orders.eventuate.command;

import com.example.orders.domain.dto.OrdersDTO;
import io.eventuate.tram.commands.common.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersReceptionCommand implements Command {
    private OrdersDTO orders;
}
