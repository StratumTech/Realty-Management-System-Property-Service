package com.stratumtech.realtyproperty.dto;

import java.util.Set;
import java.util.List;
import java.util.UUID;
import java.sql.Timestamp;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Builder;
import lombok.Singular;

@Getter
@Builder
public final class PropertyDTO {
    private final UUID id;
    private final UUID agentUuid;
    private final Long regionId;
    private final String title;
    private final String type;
    private final String dealType;
    private final BigDecimal price;
    private final Integer rooms;
    private final Double area;
    private final String address;
    private final String description;
    private final String layout;
    private final Double latitude;
    private final Double longitude;
    private final Timestamp createdAt;
    private final Timestamp updatedAt;
    private final String ownerName;
    private final String ownerSurname;
    private final String ownerPhone;
    private final Boolean paid;

    @Singular("feature")
    private final Set<FeatureDTO> features;

    @Singular("image")
    private final List<PropertyImageDTO> images;

    @Singular("calendarEntry")
    private final List<CalendarEntryDTO> calendarEntries;

    public Set<FeatureDTO> getFeatureIds() {
        return Set.copyOf(features);
    }

    public List<PropertyImageDTO> getImageUrls() {
        return List.copyOf(images);
    }

    public List<CalendarEntryDTO> getCalendarEntries() {
        return List.copyOf(calendarEntries);
    }
}
