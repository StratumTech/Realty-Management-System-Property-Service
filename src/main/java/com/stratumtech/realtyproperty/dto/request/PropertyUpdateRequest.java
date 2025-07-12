package com.stratumtech.realtyproperty.dto.request;

import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import jakarta.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public final class PropertyUpdateRequest {
    @NotNull
    private final Map<String, Object> changes;
}