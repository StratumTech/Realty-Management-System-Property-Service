package com.stratumtech.realtyproperty.controller;

import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stratumtech.realtyproperty.dto.PropertyDTO;
import com.stratumtech.realtyproperty.service.PropertyService;
import com.stratumtech.realtyproperty.dto.PropertySaveRequest;
import com.stratumtech.realtyproperty.dto.PropertyChangeRequest;

@Slf4j
@RequiredArgsConstructor
@RestController(value = "/api/v1")
public class PropertyRestController {

    private final PropertyService propertyService;

    @RequestMapping(value = "/properties", method = RequestMethod.GET)
    public ResponseEntity<List<PropertyDTO>> findProperties(){
        return ResponseEntity.ok(List.of(new PropertyDTO()));
    }

    @RequestMapping(value = "/properties/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<?> findPropertyByUuid(@PathVariable(name = "uuid") UUID propertyUuid){
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/properties", method = RequestMethod.POST)
    public ResponseEntity<?> saveProperty(@RequestBody PropertySaveRequest saveRequest){
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/properties/{uuid}", method = RequestMethod.PUT)
    public ResponseEntity<?> changeProperty(@PathVariable(name = "uuid") UUID propertyUuid,
                                            @RequestBody PropertyChangeRequest changeRequest){
        return ResponseEntity.ok(null);
    }
}
