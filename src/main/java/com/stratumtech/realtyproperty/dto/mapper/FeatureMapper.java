package com.stratumtech.realtyproperty.dto.mapper;

import org.mapstruct.Named;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

import com.stratumtech.realtyproperty.entity.Feature;

@Mapper(componentModel = "spring")
public interface FeatureMapper {

    @Named("mapFeatureName")
    default String mapFeatureName(Feature feature){
        return feature.getName();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "properties", ignore = true)
    Feature toEntity(String featureName);

    @Named("mapFeature")
    default Feature mapFeature(String featureName) {
        Feature f = new Feature();
        f.setName(featureName);
        return f;
    }

    @Named("mapFeatures")
    default Set<String> toNamesSet(Set<Feature> features) {
        return features == null
                ? Set.of()
                : features.stream()
                            .map(this::mapFeatureName)
                            .collect(Collectors.toSet());
    }

    @Named("mapFeaturesInverse")
    default Set<Feature> toEntitySet(Set<String> names) {
        return names == null
                ? Set.of()
                : names.stream()
                        .map(this::mapFeature)
                        .collect(Collectors.toSet());
    }
}
