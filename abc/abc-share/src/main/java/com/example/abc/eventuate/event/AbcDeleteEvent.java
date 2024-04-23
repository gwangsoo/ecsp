package com.example.abc.eventuate.event;

import com.example.abc.domain.dto.AbcDTO;
import io.eventuate.tram.events.common.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbcDeleteEvent implements DomainEvent {
    private AbcDTO abc;
}
