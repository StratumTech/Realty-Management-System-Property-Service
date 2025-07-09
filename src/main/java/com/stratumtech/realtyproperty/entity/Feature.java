package com.stratumtech.realtyproperty.entity;

import java.util.Set;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "features")
public class Feature {

    @Id
    @Column(name = "feature_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "features", fetch = FetchType.LAZY)
    private Set<Property> properties;

}
