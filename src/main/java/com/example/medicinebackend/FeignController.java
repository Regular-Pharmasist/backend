package com.example.medicinebackend;

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
    private final ImageProcessService imageProcessService;

    @GetMapping("/search")
    public Object getMedicineDataByName(
            @RequestParam("productName") String productName) {
        return openFeignService.getMedicineDataByName(productName);
    }

    @PostMapping("/image")
    public Object postMedicineImage(
            @RequestParam("imageFile") MultipartFile imageFile){
        List<String> productNames = imageProcessService.extractTextFromImage(imageFile);
        //여러 데이터 한꺼번에 나오도록 코드 수정하기
        return openFeignService.getMedicineDataByName(productNames);
    }

    @GetMapping("/general")
    public Object getGeneralMedicineDataByName(
            @RequestParam("productName") String productName) {
        return openFeignService.getGeneralMedicineData(productName);
    }

}
