package com.stratumtech.realtyproperty.dto.mapper;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.time.Instant;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

import org.mapstruct.factory.Mappers;

import org.springframework.test.util.ReflectionTestUtils;

import com.stratumtech.realtyproperty.dto.PropertyDTO;
import com.stratumtech.realtyproperty.dto.CalendarEntryDTO;
import com.stratumtech.realtyproperty.dto.request.PropertyCreateRequest;

import com.stratumtech.realtyproperty.entity.Feature;
import com.stratumtech.realtyproperty.entity.Property;
import com.stratumtech.realtyproperty.entity.CalendarEntry;
import com.stratumtech.realtyproperty.entity.PropertyImage;

class PropertyMapperTest {

    private PropertyMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PropertyMapperImpl();

        FeatureMapper featureMapper = Mappers.getMapper(FeatureMapper.class);
        PropertyImageMapper imageMapper = Mappers.getMapper(PropertyImageMapper.class);
        CalendarEntryMapper calendarMapper = Mappers.getMapper(CalendarEntryMapper.class);

        ReflectionTestUtils.setField(mapper, "featureMapper", featureMapper);
        ReflectionTestUtils.setField(mapper, "propertyImageMapper", imageMapper);
        ReflectionTestUtils.setField(mapper, "calendarEntryMapper", calendarMapper);
    }


    @Test
    void toDTO_shouldMapEntityToDto() {
        UUID propertyId = UUID.randomUUID();
        UUID agentUuid = UUID.randomUUID();

        Property property = new Property();
        property.setId(propertyId);
        property.setAgentUuid(agentUuid);
        property.setTitle("Test Title");
        property.setDealType("rent");
        property.setPrice(BigDecimal.valueOf(123000));

        PropertyDTO dto = mapper.toDTO(property);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(propertyId);
        assertThat(dto.getAgentUuid()).isEqualTo(agentUuid);
        assertThat(dto.getTitle()).isEqualTo("Test Title");
        assertThat(dto.getDealType()).isEqualTo("rent");
        assertThat(dto.getPrice()).isEqualTo(BigDecimal.valueOf(123000));
    }

    @Test
    void toEntity_shouldMapDtoToEntity() {
        UUID propertyId = UUID.randomUUID();
        UUID agentUuid = UUID.randomUUID();

        PropertyDTO dto = PropertyDTO.builder()
                .id(propertyId)
                .agentUuid(agentUuid)
                .title("Test Title")
                .dealType("sale")
                .price(BigDecimal.valueOf(777000))
                .build();

        Property entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(propertyId);
        assertThat(entity.getAgentUuid()).isEqualTo(agentUuid);
        assertThat(entity.getTitle()).isEqualTo("Test Title");
        assertThat(entity.getDealType()).isEqualTo("sale");
        assertThat(entity.getPrice()).isEqualTo(BigDecimal.valueOf(777000));
    }

    @Test
    void toEntity_shouldMapCreateRequestToEntity() {
        UUID agentUuid = UUID.randomUUID();

        PropertyCreateRequest request = new PropertyCreateRequest(
                agentUuid,
                1L,
                "Test Title",
                "flat",
                "sale",
                BigDecimal.valueOf(200000),
                2,
                42.0,
                Set.of(),
                "Some Address",
                "Some Description",
                List.of(),
                "layout string",
                55.7558,
                37.6173,
                "OwnerName",
                "OwnerSurname",
                "88005553535"
        );

        Property entity = mapper.toEntity(request);

        assertThat(entity).isNotNull();
        assertThat(entity.getAgentUuid()).isEqualTo(agentUuid);
        assertThat(entity.getTitle()).isEqualTo("Test Title");
        assertThat(entity.getType()).isEqualTo("flat");
        assertThat(entity.getDealType()).isEqualTo("sale");
        assertThat(entity.getPrice()).isEqualTo(BigDecimal.valueOf(200000));
        assertThat(entity.getRooms()).isEqualTo(2);
        assertThat(entity.getArea()).isEqualTo(42.0);
        assertThat(entity.getAddress()).isEqualTo("Some Address");
        assertThat(entity.getDescription()).isEqualTo("Some Description");
        assertThat(entity.getLayout()).isEqualTo("layout string");
        assertThat(entity.getLatitude()).isEqualTo(55.7558);
        assertThat(entity.getLongitude()).isEqualTo(37.6173);
        assertThat(entity.getOwnerName()).isEqualTo("OwnerName");
        assertThat(entity.getOwnerSurname()).isEqualTo("OwnerSurname");
        assertThat(entity.getOwnerPhone()).isEqualTo("88005553535");
    }

    @Test
    void toDTO_withNullCollections_shouldProduceEmptyListsAndSets() {
        Property property = new Property();
        property.setId(UUID.randomUUID());
        property.setAgentUuid(UUID.randomUUID());

        PropertyDTO dto = mapper.toDTO(property);

        assertThat(dto.getFeatures()).isEmpty();
        assertThat(dto.getImages()).isEmpty();
        assertThat(dto.getCalendarEntries()).isEmpty();
    }

    @Test
    void toDTO_withPopulatedCollections_shouldMapAllElements() {
        Property property = new Property();
        property.setId(UUID.randomUUID());
        property.setAgentUuid(UUID.randomUUID());

        Feature feat = new Feature(); feat.setId(42L); feat.setName("Pool");
        property.setFeatures(Set.of(feat));

        PropertyImage img = new PropertyImage();
        img.setId(7L);
        img.setFileUrl("https://example.com/img.jpg");
        img.setCreatedAt(Timestamp.from(Instant.now()));
        property.setImages(List.of(img));

        CalendarEntry entry = new CalendarEntry();
        entry.setCalendarId(13L);
        entry.setStartTime(OffsetDateTime.parse("2025-07-10T10:00:00Z"));
        entry.setEndTime(OffsetDateTime.parse("2025-07-10T11:00:00Z"));
        property.setCalendarEntries(List.of(entry));

        PropertyDTO dto = mapper.toDTO(property);

        assertThat(dto.getFeatures().contains("Pool")).isTrue();
        assertThat(dto.getImages()).contains("https://example.com/img.jpg");

        assertThat(dto.getCalendarEntries())
                .extracting(CalendarEntryDTO::getId, CalendarEntryDTO::getStartTime, CalendarEntryDTO::getEndTime)
                .containsExactly(tuple(
                        13L,
                        OffsetDateTime.parse("2025-07-10T10:00:00Z"),
                        OffsetDateTime.parse("2025-07-10T11:00:00Z")
                ));
    }

    @Test
    void toEntityDTO_roundtrip_shouldLoseCollectionsOnDtoToEntity() {
        Property property = new Property();
        property.setId(UUID.randomUUID());
        property.setAgentUuid(UUID.randomUUID());

        Feature feature = new Feature();
        feature.setName("A");

        PropertyImage image = new PropertyImage();
        image.setFileUrl("image.jpg");

        property.setFeatures(Set.of(feature));
        property.setImages(List.of(image));
        property.setCalendarEntries(List.of(new CalendarEntry()));

        PropertyDTO dto = mapper.toDTO(property);
        Property back = mapper.toEntity(dto);

        assertThat(back.getFeatures()).isNotEmpty();
        assertThat(back.getImages()).isNotEmpty();
        assertThat(back.getCalendarEntries()).isNotEmpty();
    }

    @Test
    void toEntity_fromCreateRequest_shouldMapAllPrimitiveFields() {
        UUID agent = UUID.randomUUID();
        PropertyCreateRequest req = new PropertyCreateRequest(
                agent,
                99L,
                "Title",
                "house",
                "sale",
                BigDecimal.valueOf(500_000),
                3,
                120.5,
                Set.of(),
                "Addr",
                "Desc",
                List.of(),
                "layout",
                51.5,
                -0.1,
                "Owner",
                "Surname",
                "+123456"
        );

        Property entity = mapper.toEntity(req);

        assertThat(entity.getId()).isNull();
        assertThat(entity.getAgentUuid()).isEqualTo(agent);
        assertThat(entity.getRegionId()).isEqualTo(99L);
        assertThat(entity.getType()).isEqualTo("house");
        assertThat(entity.getDealType()).isEqualTo("sale");
        assertThat(entity.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(500_000));
        assertThat(entity.getRooms()).isEqualTo(3);
        assertThat(entity.getArea()).isEqualTo(120.5);
        assertThat(entity.getFeatures()).isNullOrEmpty();
        assertThat(entity.getAddress()).isEqualTo("Addr");
        assertThat(entity.getDescription()).isEqualTo("Desc");
        assertThat(entity.getLayout()).isEqualTo("layout");
        assertThat(entity.getLatitude()).isEqualTo(51.5);
        assertThat(entity.getLongitude()).isEqualTo(-0.1);
        assertThat(entity.getOwnerName()).isEqualTo("Owner");
        assertThat(entity.getOwnerSurname()).isEqualTo("Surname");
        assertThat(entity.getOwnerPhone()).isEqualTo("+123456");
    }

}
