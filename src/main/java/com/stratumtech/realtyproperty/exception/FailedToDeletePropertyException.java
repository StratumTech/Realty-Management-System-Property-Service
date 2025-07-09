package com.stratumtech.realtyproperty.exception;

import java.util.UUID;

public class FailedToDeletePropertyException extends RuntimeException {

    public FailedToDeletePropertyException(UUID propertyUuid) {
        super(String.format("Failed to delete property %s", propertyUuid));
    }

    public FailedToDeletePropertyException(UUID propertyUuid, Throwable cause) {
        super(String.format("Failed to delete property %s", propertyUuid), cause);
    }

    public FailedToDeletePropertyException(Throwable cause) {
        super(cause);
    }
}
