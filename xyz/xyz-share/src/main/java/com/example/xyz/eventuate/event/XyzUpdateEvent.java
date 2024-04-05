package com.example.xyz.eventuate.event;

import com.example.xyz.domain.entity.Xyz;
import io.eventuate.tram.events.common.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XyzUpdateEvent implements DomainEvent {
    private Xyz xyz;
}
