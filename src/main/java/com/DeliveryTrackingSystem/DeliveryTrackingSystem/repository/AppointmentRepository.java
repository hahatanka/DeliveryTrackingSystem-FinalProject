package com.DeliveryTrackingSystem.DeliveryTrackingSystem.repository;


import com.DeliveryTrackingSystem.DeliveryTrackingSystem.models.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

    List<Appointment> findAppointmentByDateOfVisit(String date);

    Appointment findAppointmentByVisitorId(String id);

}
