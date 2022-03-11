package com.DeliveryTrackingSystem.DeliveryTrackingSystem.models;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
public class Parcel {

    Long senderId;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long parcelId;
    String status;
    String senderName;
    String senderAddress;
    String senderCountry;
    String receiverName;
    String receiverAddress;
    String deliveryCountry;
    String description;
    String trackingNumber = UUID.randomUUID().toString().replace("-", "").toUpperCase();
    @CreationTimestamp
    private Timestamp date;

}
