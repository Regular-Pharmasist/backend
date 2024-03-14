package com.example.medicinebackend.Controller;

import com.example.medicinebackend.Response.MedicineUsageRequest;
import com.example.medicinebackend.Service.MedicineUsageService;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MedicineUsageController {
    private final MedicineUsageService medicineUsageService;

    @PostMapping("medicine/save")
    public void usageSave(@RequestBody MedicineUsageRequest request) {
        medicineUsageService.saveUserUsage(request.getMedicineName(),request.getDailyFrequency(),request.getDuration(),
                request.isActive(), request.getStartDate(), request.getEndDate());
    }
}
