package com.stratumtech.realtyproperty.dto.request;

import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

@Getter
@RequiredArgsConstructor
public final class PropertyUpdateRequest {
    @NotNull
    @NotEmpty
    private final Map<String, Object> changes;
}

