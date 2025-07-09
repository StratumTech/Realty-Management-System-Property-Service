package com.stratumtech.realtyproperty.exception;

import java.util.UUID;

public class FailedToCreateNewPropertyException extends RuntimeException {
    public FailedToCreateNewPropertyException(UUID userUuid) {
        super(String.format("Could not create a new property for user %s", userUuid));
    }

    public FailedToCreateNewPropertyException(UUID userUuid, Throwable cause) {
        super(String.format("Could not create a new property for user %s", userUuid), cause);
    }
}
