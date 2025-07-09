package com.stratumtech.realtyproperty.dto;

import lombok.Getter;
import lombok.Builder;

@Getter
@Builder
public final class FeatureDTO {
    private final Long id;
    private final String name;
}
