package com.stratumtech.realtyproperty.dto.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.stratumtech.realtyproperty.entity.Feature;

@Mapper(componentModel = "spring")
public interface FeatureMapper {

    FeatureMapper INSTANCE = Mappers.getMapper(FeatureMapper.class);

    default Set<Long> toIdSet(Set<Feature> features) {
        return features == null ? Set.of() :
                features.stream().map(Feature::getId).collect(Collectors.toSet());
    }

    default Set<Feature> toEntitySet(Set<Long> ids) {
        return ids == null ? Set.of() :
                ids.stream().map(id -> {
                    Feature f = new Feature();
                    f.setId(id);
                    return f;
                }).collect(Collectors.toSet());
    }
}

