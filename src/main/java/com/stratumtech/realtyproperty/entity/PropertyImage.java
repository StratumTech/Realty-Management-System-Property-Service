package com.stratumtech.realtyproperty.entity;

import java.sql.Timestamp;

import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "property_images_metadata")
public class PropertyImage implements BaseEntity {

    @Id
    @Column(name = "image_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Column(name = "property_uuid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_uuid", nullable = false)
    private Property property;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(
            name = "created_at",
            insertable = false,
            updatable = false,
            columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private Timestamp createdAt;
}
