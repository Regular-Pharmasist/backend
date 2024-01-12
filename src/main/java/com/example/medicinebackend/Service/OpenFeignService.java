package com.example.medicinebackend.Service;


import static java.util.Collections.emptyList;

import com.example.medicinebackend.Client.OpenFeignClient;
import com.example.medicinebackend.Response.GeneralMedicineResponse;
import com.example.medicinebackend.Response.RiskDataResponse.Item;
import com.example.medicinebackend.Response.RiskDataResponse.RiskDataResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OpenFeignService {
    private final OpenFeignClient feignClient;

    @Value("${api.serviceKey}")
    private String serviceKey;


    public Object getMedicineDataByName(String productName) {
        List<Item> riskData = getRiskMedicineDataByName(productName);
        log.info("responseData : {}", riskData);
        if (riskData.isEmpty()) {
            List<GeneralMedicineResponse.Item> generalMedicineData = getGeneralMedicineData(productName);// 모든 데이터 가져오기
            return generalMedicineData;
        } else if (!riskData.isEmpty()) {
            return riskData;
        }
        return emptyList();// 빈 리스트 반환

    }

    public List<GeneralMedicineResponse.Item> getGeneralMedicineData(String productName) {
        //1000페이지까지 봤는데 없을 경우 어케 할지 -> 다음 페이교ㅎ=
        GeneralMedicineResponse response = feignClient.getMedicineData(serviceKey, "json");
        if (response != null && response.getBody() != null) {
            List<GeneralMedicineResponse.Item> items = response.getBody().getItems();
            if (items != null) {
                return items.stream()
                        .filter(item -> item.getItemName().contains(productName))
                        .toList();
            }
        }
        return emptyList();
    }


    public List<Item> getRiskMedicineDataByName(String productName) {
        List<Item> combinedRiskData = new ArrayList<>();
        // 생각해보기 -> 자주 찾는 약물 생각해보기

        // 임부 주의 약물 데이터 조회
        RiskDataResponse pregnantData = feignClient.getPregnantMedicineData(serviceKey, "json");
        filterItemsByProductName(pregnantData, productName, combinedRiskData);

        // 노인 주의 약물 데이터 조회
        RiskDataResponse elderlyData = feignClient.getElderlyMedicineData(serviceKey, "json");
        filterItemsByProductName(elderlyData, productName, combinedRiskData);

        //마약류 주의 데이터 조회
        RiskDataResponse drugsData = feignClient.getDrugsMedicineData(serviceKey, "json");
        filterItemsByProductName(elderlyData, productName, combinedRiskData);

        return combinedRiskData;
    }

    public void filterItemsByProductName(RiskDataResponse subjectData, String productName, List<Item> combinedRiskData) {
        if (subjectData != null && subjectData.getBody() != null && subjectData.getBody().getItems() != null) {
            List<Item> items = subjectData.getBody().getItems();
            for (Item item : items) {
                if (item != null && item.getItemName().contains(productName)) {
                    combinedRiskData.add(item);
                }
            }
        }
    }

}

