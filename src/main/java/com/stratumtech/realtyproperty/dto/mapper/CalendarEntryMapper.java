package com.stratumtech.realtyproperty.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Named;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.stratumtech.realtyproperty.dto.CalendarEntryDTO;
import com.stratumtech.realtyproperty.entity.CalendarEntry;

@Mapper(componentModel = "spring")
public interface CalendarEntryMapper {

    @Mapping(source = "calendarId", target = "id")
    CalendarEntryDTO toDTO(CalendarEntry entry);

    @Mapping(source = "id", target = "calendarId")
    @Mapping(target = "property", ignore = true)
    CalendarEntry toEntity(CalendarEntryDTO dto);

    @Named("mapCalendarEntries")
    default List<CalendarEntryDTO> toDTOList(List<CalendarEntry> entries){
        return entries == null
                ? List.of()
                : entries.stream()
                            .map(this::toDTO)
                            .collect(Collectors.toList());
    }

    @Named("mapCalendarEntriesInverse")
    default List<CalendarEntry> toEntityList(List<CalendarEntryDTO> dtos){
        return dtos == null
                ? List.of()
                : dtos.stream()
                            .map(this::toEntity)
                            .collect(Collectors.toList());
    }
}
