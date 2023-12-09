package com.example.medicinebackend.OpenAPI;


import java.util.List;
import java.util.stream.Collectors;
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

    public List<MedicineData> getMedicineDataByName(String productName) {
        DataResponse response = getMedicineData(0, 100); // 모든 데이터 가져오기
        if (response != null && response.getData() != null) {
            return response.getData().stream()
                    .filter(data -> data.getProductName().contains(productName)) // 제품명이 파라미터와 일치하는 데이터 필터링
                    .collect(Collectors.toList()); // 결과를 리스트로 수집
        }
        return List.of(); // 빈 리스트 반환
    }
}

