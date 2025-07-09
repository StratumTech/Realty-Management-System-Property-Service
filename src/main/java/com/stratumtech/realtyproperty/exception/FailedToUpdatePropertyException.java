package com.stratumtech.realtyproperty.exception;

import java.util.UUID;

public class FailedToUpdatePropertyException extends RuntimeException {
    public FailedToUpdatePropertyException(UUID propertyUuid) {
        super(String.format("Failed to update property %s", propertyUuid));
    }

    public FailedToUpdatePropertyException(UUID propertyUuid, Throwable cause) {
        super(String.format("Failed to update property %s", propertyUuid), cause);
    }
}
