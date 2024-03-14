package com.example.medicinebackend.Controller;

import com.example.medicinebackend.JWT.AuthTokensGenerator;
import com.example.medicinebackend.Response.ApiResponse.MedicineData;
import com.example.medicinebackend.Response.MedicineRecordResponseDto;
import com.example.medicinebackend.Response.MedicineResponseDto;
import com.example.medicinebackend.Service.GoogleCloudVisionApiService;
import com.example.medicinebackend.Service.OpenFeignService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FeignController {
    private final OpenFeignService openFeignService;
    private final GoogleCloudVisionApiService googleCloudVisionApiService;
    private final AuthTokensGenerator authTokensGenerator;

    @GetMapping("/search")
    public Object getMedicineDataByName(
            @RequestParam("productName") String productName) {
        return openFeignService.getMedicineDataByName(productName);
    }

    @PostMapping("/medicine")
    public List<List<MedicineResponseDto>> getMedicineData(@RequestParam(value = "imageFile", required = false)MultipartFile imageFile) {
        return openFeignService.getMedicineData(googleCloudVisionApiService.medicineNameExtractor(imageFile));
    }

    @GetMapping("/record")
    public List<MedicineRecordResponseDto> getMedicineRecord(@RequestHeader("Authorization") String tokenHeader){
        String token = tokenHeader.substring(7);
        Long memberId = authTokensGenerator.extractMemberId(token);
        return openFeignService.getMedicineRecord(memberId);
    }

    @GetMapping("/record/{medicineName}")
    public MedicineResponseDto getSpecificMedicineData(@PathVariable String medicineName){
       return openFeignService.getSpecificMedicine(medicineName);
    }



}
