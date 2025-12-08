package com.scaler.userserviceauth.repositories;

import com.scaler.userserviceauth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//import static org.hibernate.boot.model.NamedEntityGraphDefinition.Source.JPA;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    Optional<User> findById(Long aLong);


    Optional<User> findByEmail(String email);

//    User Save(User user);
}
