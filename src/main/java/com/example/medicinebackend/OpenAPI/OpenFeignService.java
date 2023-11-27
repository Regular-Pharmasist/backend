package com.example.medicinebackend.OpenAPI;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OpenFeignService {
    private final OpenFeignClient feignClient;

    @Value("${api.serviceKey}")
    private String serviceKey;

    public DataResponse getMedicineData(int page, int perPage){
        return feignClient.getMedicineData(serviceKey, page, perPage);
    }
}

