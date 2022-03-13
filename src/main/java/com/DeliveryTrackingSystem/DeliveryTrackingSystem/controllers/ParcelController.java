package com.DeliveryTrackingSystem.DeliveryTrackingSystem.controllers;

import com.DeliveryTrackingSystem.DeliveryTrackingSystem.models.Parcel;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.models.ParcelTracking;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.models.Status;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.services.CountryService;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.services.PageDataService;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.services.ParcelService;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class ParcelController {
    UserService userService;
    PageDataService pageDataService;
    CountryService countryService;
    ParcelService parcelService;

    @Autowired
    public ParcelController(UserService userService, PageDataService pageDataService, CountryService countryService, ParcelService parcelService) {
        this.userService = userService;
        this.pageDataService = pageDataService;
        this.countryService = countryService;
        this.parcelService = parcelService;
    }

    @GetMapping("/registerParcel/{userId}")
    public String showRegisterParcelPage(Model model,
             @PathVariable Long userId) {
        model.addAttribute("appTitle", pageDataService.getAppTitle());
        model.addAttribute("shortAp pTitle", pageDataService.getShortTitle());
        model.addAttribute("pageInfo", pageDataService.getPage("registerParcel"));
        model.addAttribute("userOptions", pageDataService.getUserOptions());
        model.addAttribute("countriesList", countryService.getCountriesList());
        model.addAttribute("userId", userId);
        model.addAttribute("userName", userService.getUserById(userId).getName());

        return "registerParcel";
    }

    @PostMapping("/registerParcel")
    public String handleParcelRegister(Parcel parcel, Model model,
         @RequestParam(name = "userId", required = true) Long userId) {
        model.addAttribute("userId", userId);
        parcel.setSenderId(userId);
        parcel.setStatus(Status.REGISTERED.toString());

        System.out.println(parcel);
        try {
            parcelService.createParcel(parcel);
            parcelService.addTrackingEvent(new ParcelTracking(null, parcel.getTrackingNumber(), parcel.getStatus(),
                    (parcel.getSenderCountry() + "," + parcel.getSenderAddress()), parcel.getDate(), "Item posted / received from customer"));
            return "redirect:userMenu/" + userId + "?status=register_success&tracking=" + parcel.getTrackingNumber();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:registerParcel/" + userId + "?status=register_failed&message=" + ex.getMessage();
        }
    }

    @GetMapping("/userParcels/{userId}")
    public String loadParcelList(Model model,
               @PathVariable Long userId) {
        model.addAttribute("appTitle", pageDataService.getAppTitle());
        model.addAttribute("shortAp pTitle", pageDataService.getShortTitle());
        model.addAttribute("pageInfo", pageDataService.getPage("userParcels"));
        model.addAttribute("userOptions", pageDataService.getUserOptions());
        model.addAttribute("userId", userId);
        model.addAttribute("userName", userService.getUserById(userId).getUserName());
        ArrayList<Parcel> parcels = parcelService.viewUsersParcels(userId);
        model.addAttribute("parcelList", parcels);
        model.addAttribute("userName", userService.getUserById(userId).getName());

        return "/userParcels";
    }

    @GetMapping("/trackParcel/{userId}")
    public String viewTracking(@RequestParam(name = "tracking", required = false) String trackingNumber,
              Model model, @PathVariable Long userId) throws Exception {
        model.addAttribute("appTitle", pageDataService.getAppTitle());
        model.addAttribute("pageInfo", pageDataService.getPage("TrackParcel"));
        model.addAttribute("userOptions", pageDataService.getUserOptions());
        model.addAttribute("userName", userService.getUserById(userId).getName());
        model.addAttribute("userId", userId);
        model.addAttribute("trackingNumber", trackingNumber);
        model.addAttribute("listOfTrackingEvents",
                parcelService.viewTrackingEvents(trackingNumber));
        if (trackingNumber != null) {
            Parcel parcel = parcelService.findParcelByTrackingNumber(trackingNumber);
            model.addAttribute("deliveryCountry", parcel.getDeliveryCountry());
            model.addAttribute("currentUserCountry", userService.getUserById(userId).getResidenceCountry());

            return "/trackParcel";
        }
        return "/trackParcel";
    }


        @GetMapping("/trackParcel")
        public String handleTrackParcel (Model model,
                @RequestParam(name = "trackingNumberInput", required = false) String trackingNumber,
                @RequestParam(name = "userId", required = false) String userId) throws Exception {
            model.addAttribute("userId", userId);
            model.addAttribute("listOfTrackingEvents", parcelService.viewTrackingEvents(trackingNumber));

            return "redirect:trackParcel/" + userId + "?tracking=" + trackingNumber;
        }


    @GetMapping("/findParcel/{userId}")
    public String loadFindParcelPage(@RequestParam(name = "tracking", required = false) String trackingNumber,
            Model model, @PathVariable Long userId) {
        model.addAttribute("appTitle", pageDataService.getAppTitle());
        model.addAttribute("pageInfo", pageDataService.getPage("findParcel"));
        model.addAttribute("managerOptions", pageDataService.getManagerOptions());
        model.addAttribute("userId", userId);
        model.addAttribute("trackingNumber", trackingNumber);
        model.addAttribute("userName", userService.getUserById(userId).getName());
        return "/findParcel";
    }

    @GetMapping("/findParcel")
    public String handleFindParcel(Model model,
        @RequestParam(name="userId", required = false) Long userId,
        @RequestParam(name = "tracking", required = false) String trackingNumber,
        @RequestParam(name="country", required = false) String newLocation,
        @RequestParam(name="status", required = false) String status
                                   ) {
        try {
            Parcel parcel = parcelService.findParcelByTrackingNumber(trackingNumber);

            model.addAttribute("appTitle", pageDataService.getAppTitle());
            model.addAttribute("pageInfo", pageDataService.getPage("findParcel"));
            model.addAttribute("managerOptions", pageDataService.getManagerOptions());
            model.addAttribute("trackingNumber", trackingNumber);
            model.addAttribute("senderName", parcel.getSenderName());
            model.addAttribute("receiverName", parcel.getReceiverName());
            model.addAttribute("deliveryAddress", parcel.getReceiverAddress());
            model.addAttribute("deliveryCountry", parcel.getDeliveryCountry());
            model.addAttribute("status", status);
            model.addAttribute("countriesList", countryService.getCountriesList());
            model.addAttribute("country", newLocation);
            model.addAttribute("userId", userId);
            ParcelTracking parcelTracking = parcelService.findLastTrackingEvent(trackingNumber);
            model.addAttribute("currentLocation", parcelTracking.getCurrentLocation());
            model.addAttribute("currentStatus", parcelTracking.getStatus());
            model.addAttribute("userName", userService.getUserById(userId).getName());

            List<Status> statuses = new ArrayList<>();
            statuses.add(Status.REGISTERED);
            statuses.add(Status.RETURNED);
            statuses.add(Status.DELIVERED);
            statuses.add(Status.IN_TRANSIT);
            statuses.add(Status.RECEIVED);
            model.addAttribute("listOfStatuses", statuses);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "findParcel";
    }

        @GetMapping("/editParcel")
        public String handleEditParcel( Model model, @RequestParam(name="userId", required = false) Long userId,
                @RequestParam(name = "currentTrackNum", required = false) String trackingNumber,
                @RequestParam(name = "comment", required = false) String comment,
                @RequestParam(name="country", required = false) String newLocation,
                @RequestParam(name="status", required = false) String status) throws Exception
        {
            Parcel parcel = parcelService.findParcelByTrackingNumber(trackingNumber);

            model.addAttribute("appTitle", pageDataService.getAppTitle());
            model.addAttribute("pageInfo", pageDataService.getPage("findParcel"));
            model.addAttribute("managerOptions", pageDataService.getManagerOptions());
            model.addAttribute("trackingNumber", trackingNumber);
            model.addAttribute("senderName", parcel.getSenderName());
            model.addAttribute("receiverName", parcel.getReceiverName());
            model.addAttribute("deliveryAddress", parcel.getReceiverAddress());
            model.addAttribute("deliveryCountry", parcel.getDeliveryCountry());
            model.addAttribute("status", status);
            model.addAttribute("countriesList", countryService.getCountriesList());
            model.addAttribute("country", newLocation);
            model.addAttribute("userId", userId);
            ParcelTracking parcelTracking = parcelService.findLastTrackingEvent(trackingNumber);
            model.addAttribute("currentLocation", newLocation);
            model.addAttribute("currentStatus", status);
            model.addAttribute("userName", userService.getUserById(userId).getName());

            List<Status> statuses = new ArrayList<>();
            statuses.add(Status.REGISTERED);
            statuses.add(Status.RETURNED);
            statuses.add(Status.DELIVERED);
            statuses.add(Status.IN_TRANSIT);
            statuses.add(Status.RECEIVED);
            model.addAttribute("listOfStatuses", statuses);

            Date date = new Date();
            Timestamp ts=new Timestamp(date.getTime());

            try {
                parcelService.addTrackingEvent(new ParcelTracking(null,trackingNumber, status, newLocation, ts,comment));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "findParcel";
        }

    }

