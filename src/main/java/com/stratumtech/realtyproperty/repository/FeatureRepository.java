package com.stratumtech.realtyproperty.repository;

import com.stratumtech.realtyproperty.entity.Feature;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FeatureRepository extends CrudRepository<Feature, Long> {
    Optional<Feature> findByName(String name);
}
