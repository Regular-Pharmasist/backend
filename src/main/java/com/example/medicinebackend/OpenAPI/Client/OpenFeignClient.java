package com.example.medicinebackend.OpenAPI.Client;

import com.example.medicinebackend.OpenAPI.Configuration.FeignConfiguration;
import com.example.medicinebackend.OpenAPI.Response.DataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openFeignClient", url = "http://apis.data.go.kr/1471000/DURPrdlstInfoService03", configuration = FeignConfiguration.class)
public interface OpenFeignClient {
    @GetMapping("/getPwnmTabooInfoList2") //endpoint 입력
    DataResponse getPregnantMedicineData(@RequestParam("serviceKey") String serviceKey,
                                         @RequestParam("pageNo") int pageNo,
                                         @RequestParam("numOfRows") int numsOfRows,
                                         @RequestParam("type") String type
    );
    @GetMapping("/getOdsnAtentInfoList3")
    DataResponse getElderlyMedicineData(@RequestParam("serviceKey") String serviceKey,
                                        @RequestParam("pageNo") int pageNo,
                                        @RequestParam("numOfRows") int numsOfRows,
                                        @RequestParam("type") String type
    );
}



