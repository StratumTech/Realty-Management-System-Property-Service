package com.stratumtech.realtyproperty.service;

import java.util.*;
import java.math.BigDecimal;

import org.mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.stratumtech.realtyproperty.dto.PropertyDTO;
import com.stratumtech.realtyproperty.dto.mapper.PropertyMapper;
import com.stratumtech.realtyproperty.dto.request.PropertyCreateRequest;

import com.stratumtech.realtyproperty.entity.Feature;
import com.stratumtech.realtyproperty.entity.Property;
import com.stratumtech.realtyproperty.entity.PropertyImage;

import com.stratumtech.realtyproperty.repository.FeatureRepository;
import com.stratumtech.realtyproperty.repository.PropertyRepository;
import com.stratumtech.realtyproperty.repository.PropertyImageRepository;

class DefaultPropertyServiceImplTest {

    @InjectMocks
    DefaultPropertyServiceImpl propertyService;

    @Mock
    PropertyMapper propertyMapper;

    @Mock
    FeatureRepository featureRepository;

    @Mock
    PropertyRepository propertyRepository;

    @Mock
    PropertyImageRepository propertyImageRepository;

    final UUID propertyUuid = UUID.randomUUID();
    final UUID agentUuid = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findPropertyByUuid_shouldReturnPropertyDTO_whenFound() {
        Property entity = new Property();
        entity.setId(propertyUuid);
        entity.setAgentUuid(agentUuid);
        entity.setTitle("Test title");

        PropertyDTO dto = PropertyDTO.builder()
                .id(propertyUuid)
                .agentUuid(agentUuid)
                .title("Test title")
                .build();

        when(propertyRepository.findById(propertyUuid)).thenReturn(Optional.of(entity));
        when(propertyMapper.toDTO(entity)).thenReturn(dto);

        Optional<PropertyDTO> result = propertyService.findPropertyByUuid(propertyUuid);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(propertyUuid);
        verify(propertyRepository).findById(propertyUuid);
        verify(propertyMapper).toDTO(entity);
    }

    @Test
    void findPropertyByUuid_shouldReturnEmpty_whenNotFound() {
        when(propertyRepository.findById(propertyUuid)).thenReturn(Optional.empty());

        Optional<PropertyDTO> result = propertyService.findPropertyByUuid(propertyUuid);

        assertThat(result).isEmpty();
        verify(propertyRepository).findById(propertyUuid);
        verifyNoInteractions(propertyMapper);
    }

    @Test
    void savePropertyByRequestDetails_shouldReturnSavedPropertyDTO() {

        PropertyImage image1 = new PropertyImage();
        image1.setFileUrl("image1.jpg");

        PropertyImage image2 = new PropertyImage();
        image2.setFileUrl("image2.jpg");

        Feature feature1 = new Feature();
        feature1.setName("A");

        Feature feature2 = new Feature();
        feature2.setName("B");

        PropertyCreateRequest request = new PropertyCreateRequest(
                agentUuid,
                1L,
                "New Property",
                "type",
                "dealType",
                BigDecimal.valueOf(100000),
                2,
                30.0,
                Set.of("A", "B"),
                "address",
                "description",
                List.of("image1.jpg", "image2.jpg"),
                "layout",
                125.1435531,
                213.1235423,
                "ownerName",
                "ownerSurname",
                "ownerPhone"
        );

        Property entityToSave = new Property();
        entityToSave.setAgentUuid(agentUuid);
        entityToSave.setTitle("New Property");
        entityToSave.setFeatures(Set.of(feature1, feature2));
        entityToSave.setImages(List.of(image1, image2));

        PropertyDTO dto = PropertyDTO.builder()
                .id(propertyUuid)
                .agentUuid(agentUuid)
                .title("New Property")
                .features(Set.of("A", "B"))
                .images(List.of("image1.jpg", "image2.jpg"))
                .build();

        when(propertyMapper.toEntity(any(PropertyCreateRequest.class))).thenReturn(entityToSave);
        when(propertyRepository.save(entityToSave)).thenReturn(entityToSave);
        when(propertyMapper.toDTO(entityToSave)).thenReturn(dto);

        Optional<PropertyDTO> result = propertyService.savePropertyByRequestDetails(request);

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("New Property");

        verify(propertyMapper).toEntity(any(PropertyCreateRequest.class));
        verify(propertyRepository).save(entityToSave);
        verify(propertyMapper).toDTO(entityToSave);
    }

    @Test
    void changePropertyByUuid_shouldApplyChanges_whenPropertyExists() {
        Property property = new Property();
        property.setId(propertyUuid);
        property.setTitle("Old Title");

        Map<String, Object> changes = Map.of("title", "Updated Title");

        PropertyDTO dto = PropertyDTO.builder()
                .id(propertyUuid)
                .title("Updated Title")
                .build();

        when(propertyRepository.findById(propertyUuid)).thenReturn(Optional.of(property));
        when(propertyRepository.save(property)).thenReturn(property);
        when(propertyMapper.toDTO(property)).thenReturn(dto);

        Optional<PropertyDTO> result = propertyService.changePropertyByUuid(propertyUuid, changes);

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Updated Title");

        verify(propertyRepository).findById(propertyUuid);
        verify(propertyRepository).save(property);
        verify(propertyMapper).toDTO(property);
    }

    @Test
    void removePropertyByUuid_shouldDeleteAndReturnTrue_whenExists() {
        Property property = new Property();
        property.setId(propertyUuid);

        when(propertyRepository.findById(propertyUuid)).thenReturn(Optional.of(property));

        boolean result = propertyService.removePropertyByUuid(propertyUuid);

        assertThat(result).isTrue();
        verify(propertyRepository).delete(property);
    }

    @Test
    void removePropertyByUuid_shouldReturnFalse_whenNotExists() {
        when(propertyRepository.findById(propertyUuid)).thenReturn(Optional.empty());

        boolean result = propertyService.removePropertyByUuid(propertyUuid);

        assertThat(result).isFalse();
        verify(propertyRepository, never()).delete(any());
    }
}
