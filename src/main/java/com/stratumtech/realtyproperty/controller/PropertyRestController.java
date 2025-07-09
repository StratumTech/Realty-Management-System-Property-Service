package com.stratumtech.realtyproperty.controller;

import java.util.UUID;
import java.util.Optional;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stratumtech.realtyproperty.dto.PropertyDTO;
import com.stratumtech.realtyproperty.service.PropertyService;
import com.stratumtech.realtyproperty.dto.request.PropertyCreateRequest;
import com.stratumtech.realtyproperty.dto.request.PropertyUpdateRequest;

import com.stratumtech.realtyproperty.exception.BadRequestException;
import com.stratumtech.realtyproperty.exception.PropertyNotFoundException;
import com.stratumtech.realtyproperty.exception.FailedToDeletePropertyException;
import com.stratumtech.realtyproperty.exception.FailedToUpdatePropertyException;
import com.stratumtech.realtyproperty.exception.FailedToCreateNewPropertyException;

@Slf4j
@RequiredArgsConstructor
@RestController(value = "/api/v1")
public class PropertyRestController {

    private final PropertyService propertyService;

    @RequestMapping(value = "/properties", method = RequestMethod.POST)
    public ResponseEntity<PropertyDTO> createProperty(@Valid @RequestBody PropertyCreateRequest createRequestDetails,
                                                      @RequestHeader(name = "X-USER") UUID userUuid){
        if(createRequestDetails == null){
            log.error("Create property request is null");
            throw new BadRequestException("Create property cannot be null");
        }

        if(userUuid == null){
            log.error("User UUID is null");
            throw new BadRequestException("User UUID cannot be null");
        }

        Optional<PropertyDTO> createdProperty =
                propertyService.savePropertyByRequestDetails(userUuid, createRequestDetails);
        if(createdProperty.isEmpty()){
            log.error("Could not create property for user: {}", userUuid);
            throw new FailedToCreateNewPropertyException(userUuid);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProperty.get());
    }

    @RequestMapping(value = "/properties/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<PropertyDTO> readProperty(@PathVariable(name = "uuid") UUID propertyUuid){
        if(propertyUuid == null){
            log.error("UUID is null");
            throw new BadRequestException("Property UUID cannot be null");
        }

        Optional<PropertyDTO> propertyByUuid = propertyService.findPropertyByUuid(propertyUuid);
        if(propertyByUuid.isEmpty()){
            log.error("Could not find property by uuid");
            throw new PropertyNotFoundException(propertyUuid);
        }

        return ResponseEntity.ok(propertyByUuid.get());
    }

    @RequestMapping(value = "/properties/{uuid}", method = RequestMethod.PUT)
    public ResponseEntity<PropertyDTO> updateProperty(@PathVariable(name = "uuid") UUID propertyUuid,
                                            @Valid @RequestBody PropertyUpdateRequest updateRequestDetails){
        if(propertyUuid == null){
            log.error("UUID is null");
            throw new BadRequestException("Property UUID cannot be null");
        }

        if(updateRequestDetails == null){
            log.error("Update request details is null");
            throw new BadRequestException("Update request details cannot be null");
        }

        final var changes = updateRequestDetails.getChanges();
        if(changes == null){
            log.warn("The request has no changes");
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        Optional<PropertyDTO> updatedProperty =
                propertyService.changePropertyByUuid(propertyUuid, updateRequestDetails.getChanges());
        if(updatedProperty.isEmpty()){
            log.error("Could not update property");
            throw new FailedToUpdatePropertyException(propertyUuid);
        }

        return ResponseEntity.ok(updatedProperty.get());
    }

    @RequestMapping(value = "/properties/{uuid}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProperty(@PathVariable(name = "uuid") UUID propertyUuid){
        if(propertyUuid == null){
            log.error("UUID is null");
            throw new BadRequestException("Property UUID cannot be null");
        }

        boolean success =
                propertyService.removePropertyByUuid(propertyUuid);
        if(!success){
            log.error("Could not delete property");
            throw new FailedToDeletePropertyException(propertyUuid);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
