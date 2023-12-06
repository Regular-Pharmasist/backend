package com.example.medicinebackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicineController {
    private final MedicineService medicineService;

    @Autowired
    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @GetMapping("/data-fetch/pregnant")
    public ResponseEntity<String> fetchAndStorePregnantData(){
        medicineService.fetchDataFromAPIandStore();
        return ResponseEntity.ok("임산부 주의 데이터 저장 완료");
    }


}
