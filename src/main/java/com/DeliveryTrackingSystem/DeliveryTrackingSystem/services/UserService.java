package com.DeliveryTrackingSystem.DeliveryTrackingSystem.services;

import com.DeliveryTrackingSystem.DeliveryTrackingSystem.models.Appointment;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.models.User;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.models.UserLogin;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.repository.AppointmentRepository;
import com.DeliveryTrackingSystem.DeliveryTrackingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public void createUser(User user) throws Exception {
        if (user.getIsManager() == null) {
            user.setIsManager(false);
            userRepository.save(user);
        } else {
            userRepository.save(user);
        }
    }

    public User verifyUser(UserLogin userLogin) throws Exception {
        User user = userRepository.findByUserNameAndPassword(userLogin.getUsername(), userLogin.getPassword());
        if (user == null) {throw new Exception("Username or password incorrect");}
        return user;
    }

    public User getUserById(Long id){
        for (User user : userRepository.findAll()) {
            if (user.getId() == id) return user;
        }
        return null;
    }


}
