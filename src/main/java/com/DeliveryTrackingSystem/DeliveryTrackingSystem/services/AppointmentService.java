package com.DeliveryTrackingSystem.DeliveryTrackingSystem.services;

import com.DeliveryTrackingSystem.DeliveryTrackingSystem.models.Appointment;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AppointmentService {

    AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService (AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;

    }
    public void createAppointment(Appointment appointment){
        appointmentRepository.save(appointment);
    }

    public ArrayList<Appointment> viewAppointmentsByDate(String date){
        return (ArrayList<Appointment>) appointmentRepository.findAppointmentByDateOfVisit(date);
    }


}
