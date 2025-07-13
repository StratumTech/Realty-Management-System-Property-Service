package com.stratumtech.realtyproperty.service;

import java.util.Map;
import java.util.UUID;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;

import com.stratumtech.realtyproperty.dto.PropertyDTO;
import com.stratumtech.realtyproperty.producer.PropertyIndexProducer;
import com.stratumtech.realtyproperty.dto.request.PropertyCreateRequest;

@Slf4j
@Service
public class IndexedPropertyServiceImpl implements PropertyService {

    private final PropertyService defaultPropertyService;
    private final PropertyIndexProducer propertyIndexProducer;

    public IndexedPropertyServiceImpl(
            @Qualifier("defaultPropertyServiceImpl") PropertyService defaultPropertyService,
            PropertyIndexProducer propertyIndexProducer
    ) {
        this.defaultPropertyService = defaultPropertyService;
        this.propertyIndexProducer = propertyIndexProducer;
    }

    @Override
    public Optional<PropertyDTO> findPropertyByUuid(UUID propertyUuid) {
        log.debug("Finding property by uuid '{}'", propertyUuid);
        return defaultPropertyService.findPropertyByUuid(propertyUuid);
    }

    @Override
    public Optional<PropertyDTO> savePropertyByRequestDetails(PropertyCreateRequest saveRequestDetails) {
        Optional<PropertyDTO> savedPropertyDTO =
                defaultPropertyService.savePropertyByRequestDetails(saveRequestDetails);
        log.debug("Saved property '{}'", savedPropertyDTO.get().getId());

        log.debug("Send property index request");
        savedPropertyDTO.ifPresent(propertyIndexProducer::sendPropertyIndex);

        return savedPropertyDTO;
    }

    @Override
    public Optional<PropertyDTO> changePropertyByUuid(UUID propertyUuid, Map<String, Object> changes) {
        Optional<PropertyDTO> updatedProperty =
                defaultPropertyService.changePropertyByUuid(propertyUuid, changes);
        log.debug("Updated property '{}'", propertyUuid);

        log.debug("Send property index request");
        if(updatedProperty.isPresent()){
            propertyIndexProducer.sendPropertyUpdate(propertyUuid, changes);
        }

        return updatedProperty;
    }

    @Override
    public boolean removePropertyByUuid(UUID propertyUuid) {
        boolean success =
                defaultPropertyService.removePropertyByUuid(propertyUuid);
        log.debug("Deleted property '{}'", propertyUuid);

        log.debug("Send property index request");
        if(success)
            propertyIndexProducer.sendPropertyDelete(propertyUuid);

        return success;
    }
}
