package com.stratumtech.realtyproperty.entity;

import java.util.UUID;
import java.time.OffsetDateTime;

import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "calendar")
public class CalendarEntry implements BaseEntity {

    @Id
    @Column(name = "calendar_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calendarId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "property_uuid", nullable = false)
    private Property property;

    @Column(name = "start_time", nullable = false)
    private OffsetDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private OffsetDateTime endTime;

    @Column(name = "user_uuid")
    private UUID userUuid;

    @Column(name = "status", nullable = false)
    private String status;

}
