package com.stratumtech.realtyproperty.service;

import java.util.*;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.stratumtech.realtyproperty.dto.PropertyDTO;
import com.stratumtech.realtyproperty.dto.mapper.PropertyMapper;
import com.stratumtech.realtyproperty.dto.request.PropertyCreateRequest;

import com.stratumtech.realtyproperty.entity.Feature;
import com.stratumtech.realtyproperty.entity.Property;

import com.stratumtech.realtyproperty.repository.FeatureRepository;
import com.stratumtech.realtyproperty.repository.PropertyRepository;
import com.stratumtech.realtyproperty.repository.PropertyImageRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DefaultPropertyServiceImpl implements PropertyService {

    private final PropertyMapper propertyMapper;
    private final FeatureRepository featureRepository;
    private final PropertyRepository propertyRepository;
    private final PropertyImageRepository propertyImageRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional(readOnly = true)
    public Optional<PropertyDTO> findPropertyByUuid(UUID propertyUuid) {
        return propertyRepository.findById(propertyUuid)
                .map(propertyMapper::toDTO);
    }

    @Override
    public Optional<PropertyDTO> savePropertyByRequestDetails(PropertyCreateRequest saveRequestDetails) {
        Property entityToSave = propertyMapper.toEntity(saveRequestDetails);
        log.debug("Convert save request details to property");

        entityToSave.setPaid(true);

        entityToSave.getImages().forEach(image -> image.setProperty(entityToSave));
        log.debug("Set for each image link to property");

        log.debug("Search exists features");
        Set<Feature> managedFeatures = entityToSave.getFeatures().stream()
                .map(Feature::getName)
                .map(String::trim)
                .map(name ->
                        featureRepository.findByName(name)
                                .orElseGet(() -> {
                                    Feature f = new Feature();
                                    f.setName(name);
                                    return f;
                                }))
                .collect(Collectors.toSet());

        List<Feature> newOnes = managedFeatures.stream()
                .filter(f -> f.getId() == null)
                .toList();
        featureRepository.saveAll(newOnes);
        log.debug("Save new features to database");

        entityToSave.setFeatures(managedFeatures);
        Property saved = propertyRepository.save(entityToSave);
        log.debug("Save new property to database");

        return Optional.of(propertyMapper.toDTO(saved));
    }

    @Override
    public Optional<PropertyDTO> changePropertyByUuid(UUID propertyUuid, Map<String, Object> changes) {
        return propertyRepository.findById(propertyUuid)
                .map(property -> {
                    applyChanges(property, changes);
                    log.debug("Apply changes to existing property");
                    Property saved = propertyRepository.save(property);
                    log.debug("Save changes to database");
                    return propertyMapper.toDTO(saved);
                });
    }

    @Override
    public boolean removePropertyByUuid(UUID propertyUuid) {
        return propertyRepository.findById(propertyUuid)
                .map(property -> {
                    propertyRepository.delete(property);
                    log.debug("Delete property from database");
                    return true;
                })
                .orElse(false);
    }

    private void applyChanges(Property property, Map<String, Object> changes) {
        try {
            objectMapper.updateValue(property, changes);
        } catch (JsonMappingException e) {
            log.error("Error while updating property", e);
            throw new RuntimeException(e);
        }
    }
}

