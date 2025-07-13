package com.stratumtech.realtyproperty.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;

import com.stratumtech.realtyproperty.entity.Property;
import com.stratumtech.realtyproperty.dto.PropertyDTO;
import com.stratumtech.realtyproperty.dto.request.PropertyIndexRequest;
import com.stratumtech.realtyproperty.dto.request.PropertyCreateRequest;

@Mapper(
        componentModel = "spring",
        uses = { FeatureMapper.class, PropertyImageMapper.class, CalendarEntryMapper.class }
)
public interface PropertyMapper {

    @Mapping(source = "features",        target = "features",        qualifiedByName = "mapFeatures")
    @Mapping(source = "images",          target = "images",          qualifiedByName = "mapImages")
    @Mapping(source = "calendarEntries", target = "calendarEntries", qualifiedByName = "mapCalendarEntries")
    PropertyDTO toDTO(Property property);

    @InheritInverseConfiguration
    @Mapping(source = "features",        target = "features",        qualifiedByName = "mapFeaturesInverse")
    @Mapping(source = "images",          target = "images",          qualifiedByName = "mapImagesInverse")
    @Mapping(source = "calendarEntries", target = "calendarEntries", qualifiedByName = "mapCalendarEntriesInverse")
    Property toEntity(PropertyDTO propertyDTO);

    @InheritInverseConfiguration
    @Mapping(target = "id",               ignore = true)
    @Mapping(target = "createdAt",        ignore = true)
    @Mapping(target = "updatedAt",        ignore = true)
    @Mapping(target = "paid",             ignore = true)
    @Mapping(target = "calendarEntries",  ignore = true)
    @Mapping(source = "features",        target = "features",        qualifiedByName = "mapFeaturesInverse")
    @Mapping(source = "images",          target = "images",          qualifiedByName = "mapImagesInverse")
    Property toEntity(PropertyCreateRequest request);

    @Mapping(source = "id", target = "propertyId")
    PropertyIndexRequest toPropertyIndexRequest(PropertyDTO propertyDTO);
}