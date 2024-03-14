package com.example.medicinebackend.Response;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MedicineRecordResponseDto {
    private String itemName;
    private Boolean isActive;
    private Integer dailyFrequency;
    private Date startDate;
    private Date endDate;
    private String image;
    private String typeName;
}
