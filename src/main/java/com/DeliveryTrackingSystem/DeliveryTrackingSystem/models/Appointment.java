package com.DeliveryTrackingSystem.DeliveryTrackingSystem.models;

import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    String trackingNumber;
    String receiverName;
    String dateOfVisit;


}
