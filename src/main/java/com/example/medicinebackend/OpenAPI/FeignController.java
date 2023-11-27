package com.example.medicinebackend.OpenAPI;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeignController {
    private final OpenFeignService openFeignService;

    @GetMapping("/test")
    public DataResponse getMedicineData(
            @RequestParam("page") int page,
            @RequestParam("perPage") int perPage) {
        return openFeignService.getMedicineData(page, perPage);
    }
}
