package com.example.medicinebackend.Controller;

import com.example.medicinebackend.Service.MedicineUsageService;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MedicineUsageController {
    private final MedicineUsageService medicineUsageService;

    @PostMapping("medicine/save")
    public void usageSave(@RequestParam(value = "medicineName", defaultValue = "") String name,
                          @RequestParam(value = "dailyFrequency", defaultValue = "0") int frequency,
                          @RequestParam(value = "duration",defaultValue = "0") int duration,
                          @RequestParam(value = "isActive",defaultValue ="0") boolean isActive,
                          @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                          @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        medicineUsageService.saveUserUsage(name, frequency, duration, isActive, startDate, endDate);
    }
}
