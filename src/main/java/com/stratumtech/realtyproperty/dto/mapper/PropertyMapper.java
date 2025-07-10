package com.stratumtech.realtyproperty.dto.mapper;

import org.mapstruct.*;
import lombok.RequiredArgsConstructor;

import com.stratumtech.realtyproperty.dto.PropertyDTO;
import com.stratumtech.realtyproperty.entity.Property;

@Mapper(componentModel = "spring", uses = {
        FeatureMapper.class,
        PropertyImageMapper.class,
        CalendarEntryMapper.class
})
@RequiredArgsConstructor
public abstract class PropertyMapper {

    protected FeatureMapper featureMapper;
    protected PropertyImageMapper propertyImageMapper;
    protected CalendarEntryMapper calendarEntryMapper;

    @Mapping(source = "features", target = "featureIds", qualifiedByName = "toIdSet")
    @Mapping(source = "images", target = "imageUrls", qualifiedByName = "toUrlList")
    @Mapping(source = "calendarEntries", target = "calendarEntries")
    public abstract PropertyDTO toDTO(Property property);

    @InheritInverseConfiguration
    @Mapping(target = "images", ignore = true)
    public abstract Property toEntity(PropertyDTO dto);
}


