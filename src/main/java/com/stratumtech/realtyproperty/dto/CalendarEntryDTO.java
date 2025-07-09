package com.stratumtech.realtyproperty.dto;

import java.util.UUID;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Builder;

@Getter
@Builder
public final class CalendarEntryDTO {
    private final Long id;
    private final OffsetDateTime startTime;
    private final OffsetDateTime endTime;
    private final UUID userUuid;
    private final String status;
}
