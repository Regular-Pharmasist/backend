package com.example.medicinebackend.Controller;

import com.example.medicinebackend.Service.MedicineUsageService;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MedicineUsageController {
    private final MedicineUsageService medicineUsageService;

    @PostMapping("medicine/save")
    public void usageSave(@RequestParam("medicineName") String name,
                          @RequestParam("dailyFrequency") int frequency,
                          @RequestParam("duration") int duration,
                          @RequestParam("isActive") boolean isActive,
                          @RequestParam("startDate") Date startDate,
                          @RequestParam("endDate") Date endDate) {
        medicineUsageService.saveUserUsage(name, frequency, duration, isActive, startDate, endDate);
    }
}
