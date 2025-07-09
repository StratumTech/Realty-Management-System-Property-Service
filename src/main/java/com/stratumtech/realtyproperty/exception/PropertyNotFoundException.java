package com.stratumtech.realtyproperty.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class PropertyNotFoundException extends RuntimeException {
    public PropertyNotFoundException(UUID propertyUuid) {
        super(String.format("Property not found: %s", propertyUuid));
    }

    public PropertyNotFoundException(UUID propertyUuid, Throwable cause) {
        super(String.format("Property not found: %s", propertyUuid), cause);
    }

    public PropertyNotFoundException(Throwable cause) {
        super(cause);
    }
}
