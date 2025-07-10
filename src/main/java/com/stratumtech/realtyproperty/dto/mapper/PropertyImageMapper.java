package com.stratumtech.realtyproperty.dto.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.stratumtech.realtyproperty.entity.PropertyImage;

@Mapper(componentModel = "spring")
public interface PropertyImageMapper {

    PropertyImageMapper INSTANCE = Mappers.getMapper(PropertyImageMapper.class);

    default List<String> toUrlList(Set<PropertyImage> images) {
        return images == null ? List.of() :
                images.stream().map(PropertyImage::getFileUrl).collect(Collectors.toList());
    }
}

