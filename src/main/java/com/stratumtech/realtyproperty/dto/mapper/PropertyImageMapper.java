package com.stratumtech.realtyproperty.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Named;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.stratumtech.realtyproperty.entity.PropertyImage;

@Mapper(componentModel = "spring")
public interface PropertyImageMapper {

    @Named("mapImageUrl")
    default String mapImageUrl(PropertyImage image){
        return image.getFileUrl();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "property", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    PropertyImage toEntity(String imageUrl);

    @Named("mapImage")
    default PropertyImage mapImage(String imageUrl) {
        PropertyImage i = new PropertyImage();
        i.setFileUrl(imageUrl);
        return i;
    }

    @Named("mapImages")
    default List<String> toFileUrlsSet(List<PropertyImage> images) {
        return images == null
                ? List.of()
                : images.stream()
                .map(this::mapImageUrl)
                .collect(Collectors.toList());
    }

    @Named("mapImagesInverse")
    default List<PropertyImage> toEntityList(List<String> fileUrls) {
        return fileUrls == null
                ? List.of()
                : fileUrls.stream()
                .map(this::mapImage)
                .collect(Collectors.toList());
    }
}

