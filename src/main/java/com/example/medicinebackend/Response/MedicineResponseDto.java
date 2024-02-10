package com.example.medicinebackend.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MedicineResponseDto {
        private String itemName;
        private String itemCode;
        private String efficiency;
        private String warn;
        private String sideEffect;
        private String image;
        private String material;
        private String typeName;

        // 생성자, getter, setter 생략
    }


