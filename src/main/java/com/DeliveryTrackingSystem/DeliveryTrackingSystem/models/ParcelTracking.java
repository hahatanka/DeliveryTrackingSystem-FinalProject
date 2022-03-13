package com.DeliveryTrackingSystem.DeliveryTrackingSystem.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity

public class ParcelTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    Long id;
    String trackingNumber;
    String status;
    String currentLocation;
    @CreationTimestamp
    private Timestamp date;
    private String comment;

}
