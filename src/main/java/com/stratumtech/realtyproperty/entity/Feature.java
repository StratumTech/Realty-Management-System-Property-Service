package com.stratumtech.realtyproperty.entity;

import java.util.Set;

import jakarta.persistence.*;

import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "features")
public class Feature implements BaseEntity {

    @Id
    @Column(name = "feature_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "features", fetch = FetchType.LAZY)
    private Set<Property> properties;

}
