package com.example.medicinebackend.OpenAPI.Client;

import com.example.medicinebackend.OpenAPI.Configuration.FeignConfiguration;
import com.example.medicinebackend.OpenAPI.Response.DataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openFeignClient", url = "https://api.odcloud.kr/api/", configuration = FeignConfiguration.class)
public interface OpenFeignClient {
    @GetMapping("/15089735/v1/uddi:455fb7c2-8a4c-47a5-94eb-9541d8b2d4b3") //endpoint 입력
    DataResponse getPregnantMedicineData(@RequestParam("serviceKey") String serviceKey,
                                         @RequestParam("page") int page,
                                         @RequestParam("perPage") int perPage);
    @GetMapping("/15089521/v1/uddi:734c5674-7ddc-4fad-b2c1-f88a42235cd6")
    DataResponse getElderlyMedicineData(@RequestParam("serviceKey") String serviceKey,
                                         @RequestParam("page") int page,
                                         @RequestParam("perPage") int perPage);
}



