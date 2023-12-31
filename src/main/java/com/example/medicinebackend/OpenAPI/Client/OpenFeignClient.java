package com.example.medicinebackend.OpenAPI.Client;

import com.example.medicinebackend.OpenAPI.Configuration.FeignConfiguration;
import com.example.medicinebackend.OpenAPI.Response.GeneralMedicineResponse;
import com.example.medicinebackend.OpenAPI.Response.RiskDataResponse.RiskDataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openFeignClient", url = "https://apis.data.go.kr/1471000", configuration = FeignConfiguration.class)
public interface OpenFeignClient {
    @GetMapping("/DURPrdlstInfoService03/getPwnmTabooInfoList03") //endpoint 입력
    RiskDataResponse getPregnantMedicineData(@RequestParam("serviceKey") String serviceKey,
                                             @RequestParam("type") String type
    );
    @GetMapping("/DURPrdlstInfoService03/getOdsnAtentInfoList03")
    RiskDataResponse getElderlyMedicineData(@RequestParam("serviceKey") String serviceKey,
                                            @RequestParam("type") String type
    );

    @GetMapping("/NrcdGnrlzInfoService01/getNrcdGnrlzList")
        //endpoint 입력
    RiskDataResponse getDrugsMedicineData(@RequestParam("serviceKey") String serviceKey,
                                          @RequestParam("type") String type
    );

    @GetMapping("/DrbEasyDrugInfoService/getDrbEasyDrugList")
    GeneralMedicineResponse getMedicineData(@RequestParam("serviceKey") String serviceKey,
                                            @RequestParam("type") String type
    );


}



