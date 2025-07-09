package com.stratumtech.realtyproperty.service;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.stratumtech.realtyproperty.dto.PropertyDTO;
import com.stratumtech.realtyproperty.entity.Property;
import com.stratumtech.realtyproperty.dto.converter.BaseConverter;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultPropertyServiceImpl implements PropertyService {

    private final BaseConverter<Property, PropertyDTO> propertyConverter;

}
