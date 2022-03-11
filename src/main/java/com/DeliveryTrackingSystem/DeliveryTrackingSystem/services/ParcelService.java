package com.DeliveryTrackingSystem.DeliveryTrackingSystem.services;

import com.DeliveryTrackingSystem.DeliveryTrackingSystem.models.Parcel;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.models.ParcelTracking;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.models.User;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.models.UserLogin;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.repository.ParcelRepository;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.repository.TrackingRepository;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class ParcelService {
    ParcelRepository parcelRepository;
    TrackingRepository trackingRepository;

    @Autowired
    public ParcelService(ParcelRepository parcelRepository, TrackingRepository trackingRepository) {
        this.parcelRepository = parcelRepository;
        this.trackingRepository = trackingRepository;
    }

    public void createParcel(Parcel parcel) throws Exception {
            parcelRepository.save(parcel);
        }
    public void addTrackingEvent(ParcelTracking parcelTracking) throws Exception{
        trackingRepository.save(parcelTracking);
    }

    public ArrayList<ParcelTracking> viewTrackingEvents(String trackingNumber){
        return (ArrayList<ParcelTracking>) trackingRepository.findAllByTrackingNumber(trackingNumber);
    }


    public ArrayList<Parcel> viewUsersParcels(Long userId){
        return (ArrayList<Parcel>) parcelRepository.findAllBySenderId(userId);
    }

    public Parcel findParcelByTrackingNumber(String trackingNumber)throws Exception{
        return parcelRepository.findParcelByTrackingNumber(trackingNumber);
    }
}
