package com.example.medicinebackend.Controller;

import com.example.medicinebackend.Service.OpenFeignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeignController {
    private final OpenFeignService openFeignService;

    @GetMapping("/search")
    public Object getMedicineDataByName(
            @RequestParam("productName") String productName) {
        return openFeignService.getMedicineDataByName(productName);
    }

}
