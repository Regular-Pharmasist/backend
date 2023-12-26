package com.example.medicinebackend.OpenAPI;


import static java.util.Collections.emptyList;

import com.example.medicinebackend.OpenAPI.Client.GeneralMedicineClient;
import com.example.medicinebackend.OpenAPI.Client.OpenFeignClient;
import com.example.medicinebackend.OpenAPI.Response.DataResponse;
import com.example.medicinebackend.OpenAPI.Response.DrugsDataResponse;
import com.example.medicinebackend.OpenAPI.Response.GeneralMedicineResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
    private final GeneralMedicineClient generalMedicineClient;

    @Value("${api.serviceKey}")
    private String serviceKey;


    public Object getMedicineDataByName(String productName) {
        List<DataResponse.Body.ItemWrapper.Item> riskData = getRiskMedicineDataByName(productName);
        log.info("responseData : {}", riskData);
        List<DrugsDataResponse.Item> drugData = getDrugMedicineDataByName(productName);
        if (riskData.isEmpty() && drugData.isEmpty()) {
            List<GeneralMedicineResponse.Item> generalMedicineData = getGeneralMedicineData(productName);// 모든 데이터 가져오기
            return generalMedicineData;
        } else if (!riskData.isEmpty() && drugData.isEmpty()) {
            return riskData;
        } else if (riskData.isEmpty() && !drugData.isEmpty()) {
            return drugData;
        }
        return emptyList();// 빈 리스트 반환

    }

    public List<GeneralMedicineResponse.Item> getGeneralMedicineData(String productName) {
        //1000페이지까지 봤는데 없을 경우 어케 할지 -> 다음 페이교ㅎ=
        GeneralMedicineResponse response = generalMedicineClient.getMedicineData(serviceKey, 0, 1000, "json");
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

    public List<DrugsDataResponse.Item> getDrugMedicineDataByName(String productName) {
        DrugsDataResponse response = generalMedicineClient.getDrugsMedicineData(serviceKey, 1000, 0, "json");
        if (response != null && response.getBody().getItems() != null) {
            List<DrugsDataResponse.Item> items = response.getBody().getItems();
            if (items != null) {
                return items.stream()
                        .filter(item -> item.getDrugName().contains(productName))
                        .toList();
            }
        }
        return emptyList();
    }



    public List<DataResponse.Body.ItemWrapper.Item> getRiskMedicineDataByName(String productName) {
        List<DataResponse.Body.ItemWrapper.Item> combinedRiskData = new ArrayList<>();

        // 임부 주의 약물 데이터 조회
        DataResponse pregnantData = feignClient.getPregnantMedicineData(serviceKey, 0, 1000,"json");
        log.info("responseData : {}", pregnantData);
        if (pregnantData != null && pregnantData.getBody() != null && pregnantData.getBody().getItems() != null) {
            DataResponse.Body.ItemWrapper.Item item = pregnantData.getBody().getItems().getItem();
            if (item != null && item.getItemName() != null && item.getItemName().contains(productName)) {
                combinedRiskData.add(item);
            }
        }
        log.info("responseData : {}", combinedRiskData);

        // 노인 주의 약물 데이터 조회
        DataResponse elderlyData = feignClient.getElderlyMedicineData(serviceKey, 0, 1000,"json");
        if (elderlyData != null && elderlyData.getBody() != null && elderlyData.getBody().getItems() != null) {
            DataResponse.Body.ItemWrapper.Item item = elderlyData.getBody().getItems().getItem();
            if (item != null && item.getItemName() != null && item.getItemName().contains(productName)) {
                combinedRiskData.add(item);
            }
        }
        return combinedRiskData;
    }
}

