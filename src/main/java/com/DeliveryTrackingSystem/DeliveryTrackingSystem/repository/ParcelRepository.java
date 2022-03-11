package com.DeliveryTrackingSystem.DeliveryTrackingSystem.repository;

import com.DeliveryTrackingSystem.DeliveryTrackingSystem.models.Parcel;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.models.ParcelTracking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface ParcelRepository extends CrudRepository<Parcel, Long> {

    List<Parcel> findAllBySenderId(Long senderId);

    Parcel findParcelByTrackingNumber(String trackingNumber);
}
