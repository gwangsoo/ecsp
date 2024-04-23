package com.example.xyz.eventuate.event;

import com.example.xyz.domain.dto.XyzDTO;
import io.eventuate.tram.events.common.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XyzDeleteEvent implements DomainEvent {
    private XyzDTO xyz;
}
