package com.example.medicinebackend.Client;

import com.example.medicinebackend.Configuration.FeignConfiguration;
import com.example.medicinebackend.Response.GeneralMedicineResponse;
import com.example.medicinebackend.Response.PermittedDataResponse;
import com.example.medicinebackend.Response.RiskDataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openFeignClient", url = "https://apis.data.go.kr/1471000", configuration = FeignConfiguration.class)
public interface OpenFeignClient {

    @GetMapping("/DURPrdlstInfoService03/getDurPrdlstInfoList03")
    RiskDataResponse getRiskMedicineData(@RequestParam("serviceKey") String serviceKey,
                                         @RequestParam("type") String type,
                                         @RequestParam("itemName") String itemName
    );

    @GetMapping("/DrugPrdtPrmsnInfoService04/getDrugPrdtPrmsnDtlInq03")
        //endpoint 입력
    PermittedDataResponse getPermittedMedicineData(@RequestParam("serviceKey") String serviceKey,
                                                   @RequestParam("type") String type,
                                                   @RequestParam("item_name") String item_name
    );

//    @GetMapping("/DURPrdlstInfoService03/getPwnmTabooInfoList03")
//        //endpoint 입력
//    RiskDataResponse getPregnantMedicineData(@RequestParam("serviceKey") String serviceKey,
//                                             @RequestParam("type") String type,
//                                             @RequestParam("itemName") String itemName
//    );
//
////    @GetMapping("/DURPrdlstInfoService03/getOdsnAtentInfoList03")
////    RiskDataResponse getElderlyMedicineData(@RequestParam("serviceKey") String serviceKey,
////                                            @RequestParam("type") String type,
////                                            @RequestParam("itemName") String itemName
////    );
//    @GetMapping("/DURPrdlstInfoService03/getSpcifyAgrdeTabooInfoList03")
//    RiskDataResponse getAgeMedicineData(@RequestParam("serviceKey") String serviceKey,
//                                            @RequestParam("type") String type,
//                                            @RequestParam("itemName") String itemName
//    );
//    @GetMapping("/DURPrdlstInfoService03/getMdctnPdAtentInfoList03")
//    RiskDataResponse getDateMedicineData(@RequestParam("serviceKey") String serviceKey,
//                                        @RequestParam("type") String type,
//                                        @RequestParam("itemName") String itemName
//    );
//
//
//    @GetMapping("/NrcdGnrlzInfoService01/getNrcdGnrlzList")
//        //endpoint 입력
//    RiskDataResponse getDrugsMedicineData(@RequestParam("serviceKey") String serviceKey,
//                                          @RequestParam("type") String type,
//                                          @RequestParam("itemName") String itemName
//    );
//    @GetMapping("/DURPrdlstInfoService03/getEfcyDplctInfoList03")
//        //endpoint 입력
//    RiskDataResponse getDuplicateMedicineData(@RequestParam("serviceKey") String serviceKey,
//                                          @RequestParam("type") String type,
//                                          @RequestParam("itemName") String itemName
//    );


    @GetMapping("/DrbEasyDrugInfoService/getDrbEasyDrugList")
    GeneralMedicineResponse getMedicineData(@RequestParam("serviceKey") String serviceKey,
                                            @RequestParam("type") String type,
                                            @RequestParam("itemName") String itemName);


}



