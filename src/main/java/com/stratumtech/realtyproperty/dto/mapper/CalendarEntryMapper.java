package com.stratumtech.realtyproperty.dto.mapper;

import com.stratumtech.realtyproperty.dto.CalendarEntryDTO;
import com.stratumtech.realtyproperty.entity.CalendarEntry;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CalendarEntryMapper {

    CalendarEntryDTO toDTO(CalendarEntry entry);

    @Mapping(target = "property", ignore = true)
    CalendarEntry toEntity(CalendarEntryDTO dto);

    List<CalendarEntryDTO> toDTOList(List<CalendarEntry> entries);

    List<CalendarEntry> toEntityList(List<CalendarEntryDTO> dtos);
}

