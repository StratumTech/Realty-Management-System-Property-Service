package com.stratumtech.realtyproperty.service;

import java.util.Map;
import java.util.UUID;
import java.util.Optional;

import com.stratumtech.realtyproperty.dto.PropertyDTO;
import com.stratumtech.realtyproperty.dto.request.PropertyCreateRequest;

public interface PropertyService {

    Optional<PropertyDTO> findPropertyByUuid(UUID propertyUuid);

    Optional<PropertyDTO> savePropertyByRequestDetails(UUID userUuid, PropertyCreateRequest saveRequestDetails);

    Optional<PropertyDTO> changePropertyByUuid(UUID propertyUuid, Map<String, Object> changes);

    boolean removePropertyByUuid(UUID propertyUuid);
}
