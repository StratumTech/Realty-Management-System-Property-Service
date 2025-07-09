package com.stratumtech.realtyproperty.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "property_images_metadata")
public class PropertyImage {

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
