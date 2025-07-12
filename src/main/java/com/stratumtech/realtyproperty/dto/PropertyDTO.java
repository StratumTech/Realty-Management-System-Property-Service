package com.stratumtech.realtyproperty.dto;

import java.util.Set;
import java.util.List;
import java.util.UUID;
import java.util.HashSet;
import java.sql.Timestamp;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Builder;
import lombok.AccessLevel;

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

    @Builder.Default
    @Getter(AccessLevel.NONE)
    private Set<String> features = new HashSet<>();

    @Builder.Default
    @Getter(AccessLevel.NONE)
    private List<String> images = List.of();

    @Builder.Default
    @Getter(AccessLevel.NONE)
    private List<CalendarEntryDTO> calendarEntries = List.of();

    public Set<String> getFeatures() {
        return Set.copyOf(features);
    }

    public List<String> getImages() {
        return List.copyOf(images);
    }

    public List<CalendarEntryDTO> getCalendarEntries() {
        return List.copyOf(calendarEntries);
    }
}
