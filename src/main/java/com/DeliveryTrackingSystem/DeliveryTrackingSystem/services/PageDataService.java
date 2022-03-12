package com.DeliveryTrackingSystem.DeliveryTrackingSystem.services;

import com.DeliveryTrackingSystem.DeliveryTrackingSystem.models.Page;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class PageDataService {
    @Value("${trackingsystem.title}")
    String appTitle;
    @Value("${trackingsystem.short_title}")
    String shortTitle;
    List<Page> availablePages = new ArrayList<>();
    List<Page> managerOptions = new ArrayList<>();
    List<Page> userOptions = new ArrayList<>();

    PageDataService() {

        availablePages.add(new Page("index", "Home", "Home page", "/"));
        availablePages.add(new Page("register", "Register", "Create an account", "/register"));
        availablePages.add(new Page("login", "Login", "Login to use app", "/login"));
        availablePages.add(new Page("userMenu", "Welcome to DTS", "User Menu", "/userMenu"));
        availablePages.add(new Page("managerMenu", "Welcome to DTS", "Manager Menu", "/managerMenu"));
        availablePages.add(new Page("login", "Logout", "Logout", "/"));
        availablePages.add(new Page("registerParcel", "Register Parcel", "Register a Parcel", "/registerParcel"));
        availablePages.add(new Page("bookAppointment", "Book an Appointment", "Book an Appointment", "/bookAppointment"));
        availablePages.add(new Page("success", "Success", "Success", "/success"));
        availablePages.add(new Page("viewAppointments", "Appointments", "Users Appointments", "/viewAppointments"));
        availablePages.add(new Page("findParcel", "Find a Parcel", "Find a Parcel", "/findParcel"));
        availablePages.add(new Page("trackParcel", "Track Parcel", "Track a Parcel", "/trackParcel"));
        availablePages.add(new Page("userParcels", "My Parcels", "My Parcels", "/userParcels"));



        userOptions.add(new Page("registerParcel", "Register Parcel", "Register a Parcel", "/registerParcel"));
        userOptions.add(new Page("trackParcel", "Track Parcel", "Track a Parcel", "/trackParcel"));
        userOptions.add(new Page("userParcels", "My Parcels", "My Parcels", "/userParcels"));


        managerOptions.add(new Page("findParcel", "Find a Parcel", "Find a Parcel", "/findParcel"));
        managerOptions.add(new Page("viewAppointments", "View appointments", "View appointments", "/viewAppointments"));




    }

    public Page getPage(String pageName){
        for (Page page: this.availablePages){
            if (page.getName().equalsIgnoreCase(pageName)) return page;
        }
        return null;
    }



    public String getAppTitle() {
        return appTitle;
    }

    public String getShortTitle() {
        return shortTitle;
    }


    public List<Page> getManagerOptions() {
        return managerOptions;
    }

    public List<Page> getUserOptions() {
        return userOptions;
    }
}


