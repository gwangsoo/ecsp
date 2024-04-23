package com.example.bfi.web.rest;

import com.example.bfi.domain.dto.CommandDTO;
import com.example.bfi.domain.dto.enumeration.CommandResultType;
import com.example.bfi.domain.dto.enumeration.CommandType;
import com.example.ecsp.common.util.HeaderUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/commands/{cpo_id}/{location_id}/{evse_uid}/{connector_id}")
public class CommandResource {

    private final Logger log = LoggerFactory.getLogger(CommandResource.class);

    private static final String ENTITY_NAME = "command";

    @Value("${application.clientApp.name}")
    private String applicationName;

    @PostMapping("/{command_type}")
    public ResponseEntity<CommandDTO> updateLocation(@PathVariable(value = "cpo_id", required = true) final String cpoId, @PathVariable(value = "location_id", required = true) final String locationId, @PathVariable(value = "evse_uid", required = true) final String evseUid, @PathVariable(value = "connector_id", required = true) final String connectorId, @PathVariable(value = "command_type", required = true) final CommandType commandType) throws URISyntaxException {

        log.debug("REST request to {} : {}/{}/{}/{}", commandType, cpoId, locationId, evseUid, connectorId);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commandType + cpoId + locationId + evseUid + connectorId)).body(CommandDTO.builder().id("test").result(CommandResultType.ACCEPTED).message(commandType.toString()).build());
    }

}
