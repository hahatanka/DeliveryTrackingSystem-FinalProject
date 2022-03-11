package com.DeliveryTrackingSystem.DeliveryTrackingSystem.models;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    @Column(name="user_name", unique = true)
    private String userName;
    private String password;
    private String residenceCountry;
    private String city;
    private Boolean isManager;



}





