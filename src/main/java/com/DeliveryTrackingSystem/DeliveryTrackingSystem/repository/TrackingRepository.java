package com.DeliveryTrackingSystem.DeliveryTrackingSystem.repository;

import com.DeliveryTrackingSystem.DeliveryTrackingSystem.models.ParcelTracking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackingRepository extends CrudRepository<ParcelTracking, String>{
    List<ParcelTracking> findAllByTrackingNumber(String trackingNumber);
}
