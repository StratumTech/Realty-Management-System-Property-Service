package com.stratumtech.realtyproperty.config;

import com.stratumtech.realtyproperty.dto.PropertyDTO;
import com.stratumtech.realtyproperty.entity.Property;
import com.stratumtech.realtyproperty.dto.converter.BaseConverter;
import com.stratumtech.realtyproperty.dto.converter.PropertyConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ConverterBeans {

    @Bean
    @Scope("singelton")
    public BaseConverter<Property, PropertyDTO> propertyToPropertyDTOConverter(){
        return new PropertyConverter();
    }

}
