package com.stratumtech.realtyproperty.entity;

import java.util.Set;
import java.util.UUID;
import java.sql.Timestamp;
import java.math.BigDecimal;

import jakarta.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "properties")
public  class Property implements BaseEntity {

    @Id
    @Column(name = "property_uuid", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "agent_uuid", nullable = false)
    private UUID agentUuid;

    @Column(name = "region_id", nullable = false)
    private Long regionId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "deal_type", nullable = false)
    private String dealType;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "rooms", nullable = false)
    private Integer rooms;

    @Column(name = "area", nullable = false)
    private Double area;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "layout", nullable = false)
    private String layout;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(
            name = "created_at",
            insertable = false,
            updatable = false,
            columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private Timestamp createdAt;

    @Column(
            name = "updated_at",
            insertable = false,
            updatable = false,
            columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private Timestamp updatedAt;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "owner_surname")
    private String ownerSurname;

    @Column(name = "owner_phone")
    private String ownerPhone;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "properties_to_features",
            joinColumns = @JoinColumn(name = "property_uuid"),
            inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private Set<Feature> features;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PropertyImage> images;
}
