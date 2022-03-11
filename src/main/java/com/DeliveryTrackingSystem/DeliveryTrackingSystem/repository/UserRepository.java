package com.DeliveryTrackingSystem.DeliveryTrackingSystem.repository;

import com.DeliveryTrackingSystem.DeliveryTrackingSystem.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserNameAndPassword(String userName, String password);
    List<User> findAll();
    @Query("select u from User u where u.id = ?1")
    User findById(int id);

}
