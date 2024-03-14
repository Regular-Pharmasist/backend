package com.example.medicinebackend.Response;

import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class MedicineUsageRequest {
    private String medicineName = ""; // 기본값으로 빈 문자열 설정
    private int dailyFrequency = 0;
    private int duration = 0;
    private boolean isActive = false;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    // 기본 생성자, getter, setter 생략
}
