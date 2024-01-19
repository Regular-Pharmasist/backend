package com.example.medicinebackend.Controller;

import com.example.medicinebackend.Service.GoogleCloudVisionApiService;
import com.example.medicinebackend.Service.OpenFeignService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FeignController {
    private final OpenFeignService openFeignService;

    @GetMapping("/search")
    public Object getMedicineDataByName(
            @RequestParam("productName") String productName) {
        return openFeignService.getMedicineDataByName(productName);
    }

    @PostMapping("/medicine")
    public List<Object> getMedicineData(@RequestParam("imageFile")MultipartFile imageFile) {
        GoogleCloudVisionApiService googleCloudVisionApiService = new GoogleCloudVisionApiService();
        return openFeignService.getMedicineData(googleCloudVisionApiService.medicineNameExtractor(imageFile));
    }



}
