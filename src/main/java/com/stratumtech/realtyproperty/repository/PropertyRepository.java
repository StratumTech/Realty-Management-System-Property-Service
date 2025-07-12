package com.stratumtech.realtyproperty.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.stratumtech.realtyproperty.entity.Property;

public interface PropertyRepository extends CrudRepository<Property, UUID> {

}
