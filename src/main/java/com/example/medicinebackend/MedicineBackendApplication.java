package com.example.medicinebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MedicineBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(MedicineBackendApplication.class, args);
    }

}
