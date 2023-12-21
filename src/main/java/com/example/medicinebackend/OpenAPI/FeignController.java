package com.example.medicinebackend.OpenAPI;

import com.example.medicinebackend.ApiResponse.MedicineData;
import com.example.medicinebackend.OpenAPI.Client.GeneralMedicineClient;
import com.example.medicinebackend.OpenAPI.Response.DrugsDataResponse;
import com.example.medicinebackend.OpenAPI.Response.GeneralMedicineResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeignController {
    private final OpenFeignService openFeignService;

   /* @GetMapping("/test")
    public DataResponse getPregMedicineData(
            @RequestParam("page") int page,
            @RequestParam("perPage") int perPage) {
        return openFeignService.getPregnantMedicineData(page, perPage);
    }*/
    @GetMapping("/search")
    public Object getMedicineDataByName(
            @RequestParam("productName") String productName) {
        return openFeignService.getMedicineDataByName(productName);
    }
    @GetMapping("/pregnant")
    public Object getPregnantDataByName(
            @RequestParam("productName") String productName) {
        return openFeignService.getRiskMedicineDataByName(productName);
    }

    @GetMapping("/drugs")
    public List<DrugsDataResponse.Item> getDrugsDataByName(
            @RequestParam("productName") String productName
    ){
        return openFeignService.getDrugMedicineDataByName(productName);
    }

    @GetMapping("/general")
    public List<GeneralMedicineResponse.Item> getGeneralMedicineDataByName(
            @RequestParam("productName") String productName
    ){
        return openFeignService.getGeneralMedicineData(productName);
    }


}
