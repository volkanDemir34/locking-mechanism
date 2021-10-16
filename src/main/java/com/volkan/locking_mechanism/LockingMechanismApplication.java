package com.volkan.locking_mechanism;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class LockingMechanismApplication {

    public static void main(String[] args) {
        SpringApplication.run(LockingMechanismApplication.class, args);
    }

}
