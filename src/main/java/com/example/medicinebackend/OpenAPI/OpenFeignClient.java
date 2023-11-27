package com.example.medicinebackend.OpenAPI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openFeignClient", url = "https://api.odcloud.kr/api/15089735/v1", configuration = FeignConfiguration.class)
public interface OpenFeignClient {
    @GetMapping("/uddi:c75b089a-ce89-4b19-98e4-f2b59ac71c27")
    DataResponse getMedicineData(@RequestParam("serviceKey") String serviceKey,
                                 @RequestParam("page") int page,
                                 @RequestParam("perPage") int perPage);
}

