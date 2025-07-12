package com.stratumtech.realtyproperty.repository;

import java.util.Optional;

import com.stratumtech.realtyproperty.entity.Feature;
import org.springframework.data.repository.CrudRepository;

public interface FeatureRepository extends CrudRepository<Feature, Long> {
    Optional<Feature> findByName(String name);
}
