package com.stratumtech.realtyproperty.dto.request;

import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import jakarta.validation.constraints.*;

import com.stratumtech.realtyproperty.dto.FeatureDTO;

@Getter
@RequiredArgsConstructor
public final class PropertyCreateRequest {

        @NotNull
        private final UUID agentUuid;

        @NotNull
        @Positive
        private final Long regionId;

        @NotNull
        @NotEmpty
        private final String title;

        @NotNull
        @NotEmpty
        private final String type;

        @NotNull
        @NotEmpty
        private final String dealType;

        @NotNull
        private final BigDecimal price;

        @NotNull
        @Positive
        private final Integer rooms;

        @NotNull
        @Positive
        private final Double area;

        @NotNull
        private final List<FeatureDTO> features;

        @NotNull
        private final String address;

        @NotNull
        private final String description;

        @NotNull
        @NotEmpty
        private final String layout;

        @NotNull
        private final Double latitude;

        @NotNull
        private final Double longitude;

        @NotNull
        private final String ownerName;

        @NotNull
        private final String ownerSurname;

        @NotNull
        private final String ownerPhone;
}
