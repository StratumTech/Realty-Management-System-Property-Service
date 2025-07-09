package com.stratumtech.realtyproperty.dto;

import java.util.UUID;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Builder;

@Getter
@Builder
public final class PropertyImageDTO {
    private final Long id;
    private final UUID propertyId;
    private final String fileUrl;
    private final Timestamp createdAt;
}

