package com.stratumtech.realtyproperty.entity;

import java.sql.Timestamp;
import java.time.Instant;

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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_uuid", nullable = false)
    private Property property;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Timestamp.from(Instant.now());
    }
}
