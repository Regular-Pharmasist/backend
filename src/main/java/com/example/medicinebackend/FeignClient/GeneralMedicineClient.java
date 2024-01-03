package com.example.medicinebackend.FeignClient;

import com.example.medicinebackend.Configuration.FeignConfiguration;
import com.example.medicinebackend.Response.GeneralMedicineResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "generalMedicineClient", url = "http://apis.data.go.kr/1471000", configuration = FeignConfiguration.class)
public interface GeneralMedicineClient {
    @GetMapping("/DrbEasyDrugInfoService/getDrbEasyDrugList")
    GeneralMedicineResponse getMedicineData(@RequestParam("serviceKey") String serviceKey,
                                            @RequestParam("pageNo") int pageNo,
                                            @RequestParam("numOfRows") int numOfRows,
                                            @RequestParam("type") String type
    );



}

