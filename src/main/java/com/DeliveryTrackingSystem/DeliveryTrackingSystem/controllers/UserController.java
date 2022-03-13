package com.DeliveryTrackingSystem.DeliveryTrackingSystem.controllers;

import com.DeliveryTrackingSystem.DeliveryTrackingSystem.models.Appointment;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.models.Page;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.models.User;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.models.UserLogin;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.services.AppointmentService;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.services.CountryService;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.services.PageDataService;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


@Controller
public class UserController {

    UserService userService;
    PageDataService pageDataService;
    CountryService countryService;
    AppointmentService appointmentService;

    @Autowired
    public UserController(UserService userService, PageDataService pageDataService, CountryService countryService, AppointmentService appointmentService) {
        this.userService = userService;
        this.pageDataService = pageDataService;
        this.countryService = countryService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/about")
    public String showAboutPage(){
        return "about";
    }

    @GetMapping("/contact")
    public String showContactPage(){
        return "contact";
    }


    @GetMapping("/register")
    public String showRegisterPage(
            @RequestParam(name = "isManager", required = false) Boolean isManager,
            Model model
    ) {
        model.addAttribute("isManager", isManager);
        model.addAttribute("appTitle", pageDataService.getAppTitle());
        model.addAttribute("shortAppTitle", pageDataService.getShortTitle());
        model.addAttribute("countriesList", countryService.getCountriesList());

        return "register";
    }

    @PostMapping("/register")
    public String handleUserRegister(User user) {
        try {
            userService.createUser(user);
            return "redirect:login?status=signup_success";
        } catch (Exception ex) {
            return "redirect:register?status=signup_failed&message=" + ex.getMessage();
        }
    }

    @GetMapping("/login")
    public String showLoginPage(
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "message", required = false) String message,
            Model model
    ) {

        model.addAttribute("appTitle", pageDataService.getAppTitle());
        model.addAttribute("status", status);
        model.addAttribute("message", message);
        return "login";
    }


    @PostMapping("/login")
    public String handleUserLogin(UserLogin userLogin) {
        try {
            User user = userService.verifyUser(userLogin);
            if (user.getIsManager()) {
                return "redirect:managerMenu/" + user.getId();
            } else {
                return "redirect:userMenu/" + user.getId();
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return "redirect:login?status=login_failed&message=" + exception.getMessage();
        }

    }


    @GetMapping("/managerMenu/{userId}")
    public String showManagerMenu(Model model, @PathVariable Long userId) {
        model.addAttribute("appTitle", pageDataService.getAppTitle());
        model.addAttribute("pageInfo", pageDataService.getPage("managerMenu"));
        model.addAttribute("managerOptions", pageDataService.getManagerOptions());
        model.addAttribute("userName", userService.getUserById(userId).getName());
        model.addAttribute("userId", userId);

        return "managerMenu";
    }

    @GetMapping("/userMenu/{userId}")
    public String showUserMenu(@PathVariable Long userId,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "message", required = false) String message,
            @RequestParam(name = "tracking", required = false) String trackingNumber,
            Model model

    ) {
        model.addAttribute("appTitle", pageDataService.getAppTitle());
        model.addAttribute("pageInfo", pageDataService.getPage("userMenu"));
        model.addAttribute("userOptions", pageDataService.getUserOptions());
        model.addAttribute("userName", userService.getUserById(userId).getName());
        model.addAttribute("userId", userId);
        model.addAttribute("status", status);
        model.addAttribute("message", message);
        model.addAttribute("trackingNumber", trackingNumber);

        return "userMenu";
    }

    @GetMapping("/bookAppointment/{userId}/{trackingNumber}")
    public String loadBookAppointmentPage(@PathVariable String trackingNumber, Appointment appointment,
              Model model, @PathVariable Long userId,
              @RequestParam(name = "status", required = false) String status,
              @RequestParam(name = "message", required = false) String message
    ) {
        model.addAttribute("appTitle", pageDataService.getAppTitle());
        model.addAttribute("shortAp pTitle", pageDataService.getShortTitle());
        model.addAttribute("pageInfo", pageDataService.getPage("bookAppointment"));
        model.addAttribute("userOptions", pageDataService.getUserOptions());
        model.addAttribute("receiverName", userService.getUserById(userId).getName());
        model.addAttribute("status", status);
        model.addAttribute("message", message);
        model.addAttribute("userId", userId);


        System.out.println("Visitor id  "+userId);

        appointment.setTrackingNumber(trackingNumber);
        appointment.setReceiverName(userService.getUserById(userId).getName());

        return "/bookAppointment";
    }

    @PostMapping("/bookAppointment")
    public String handleBookAppointment(Appointment appointment,
                @RequestParam(name = "userId", required = false) Long userId) {
        try {
            appointment.setVisitorId(userId);
            appointmentService.createAppointment(appointment);
            System.out.println("Appointment:" + appointment);
            return "redirect:bookAppointment/" + userId + "/" + appointment.getTrackingNumber() + "?status=register_success";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:bookAppointment/" + userId + "/" + appointment.getTrackingNumber() + "?status=register_failed";
        }
    }

    @GetMapping("/viewAppointments/{userId}")
    public String loadAppointmemtsPage(@RequestParam(name = "date", required = false) String date,
            Model model, @PathVariable Long userId ) {
        model.addAttribute("appTitle", pageDataService.getAppTitle());
        model.addAttribute("pageInfo", pageDataService.getPage("viewAppointments"));
        model.addAttribute("managerOptions", pageDataService.getManagerOptions());
        model.addAttribute("userId", userId);
        model.addAttribute("date", date);
        model.addAttribute("userName", userService.getUserById(userId).getName());

        try {
            model.addAttribute("listOfAppointments", appointmentService.viewAppointmentsByDate(date));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "/viewAppointments";
    }

    @GetMapping("/viewAppointments")
    public String handleViewAppointments(
            Model model,
             @RequestParam(name = "date", required = false) String date,
             @RequestParam(name = "userId", required = false) Long userId
             ){

        try {
            model.addAttribute("listOfAppointments", appointmentService.viewAppointmentsByDate(date));
            model.addAttribute("appTitle", pageDataService.getAppTitle());
            model.addAttribute("pageInfo", pageDataService.getPage("viewAppointments"));
            model.addAttribute("managerOptions", pageDataService.getManagerOptions());
            model.addAttribute("date", date);
            model.addAttribute("userId", userId);
            model.addAttribute("userName", userService.getUserById(userId).getName());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "viewAppointments";
    }

    @GetMapping("/send")
    public String sendMessage(){
        return "redirect:/";
    }
}


