package com.stratumtech.realtyproperty.entity;

import java.util.UUID;
import java.sql.Timestamp;
import java.math.BigDecimal;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Table(name = "properties")
public final class Property {

    @Id
    @Column(name = "property_uuid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private final UUID id;

    @Column(name = "agent_uuid", nullable = false)
    private final UUID agentUuid;

    @Column(name = "region_id", nullable = false)
    private final Long regionId;

    @Column(name = "title", nullable = false)
    private final String title;

    @Column(name = "type", nullable = false)
    private final String type;

    @Column(name = "deal_type", nullable = false)
    private final String dealType;

    @Column(name = "price", nullable = false)
    private final BigDecimal price;

    @Column(name = "rooms", nullable = false)
    private final Integer rooms;

    @Column(name = "area", nullable = false)
    private final Double area;

    @Column(name = "address", nullable = false)
    private final String address;

    @Column(name = "description")
    private final String description;

    @Column(name = "layout", nullable = false)
    private final String layout;

    @Column(name = "latitude", nullable = false)
    private final Double latitude;

    @Column(name = "longitude", nullable = false)
    private final Double longitude;

    @Column(
            name = "created_at",
            insertable = false,
            updatable = false,
            columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private final Timestamp createdAt;

    @Column(
            name = "updated_at",
            insertable = false,
            updatable = false,
            columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private final Timestamp updatedAt;

    @Column(name = "owner_name")
    private final String ownerName;

    @Column(name = "owner_surname")
    private final String ownerSurname;

    @Column(name = "owner_phone")
    private final String ownerPhone;
}
