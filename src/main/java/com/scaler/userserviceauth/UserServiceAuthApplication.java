package com.scaler.userserviceauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UserServiceAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceAuthApplication.class, args);
    }

}

//EnableJpaAuditing, Entity Listeners In basemodel, @CreatedDate, @LastModifiedDate, @CreatedBy, @LastModifiedBy..They will track the changes