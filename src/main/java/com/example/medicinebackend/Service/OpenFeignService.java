package com.example.medicinebackend.Service;


import static java.util.Collections.emptyList;

import com.example.medicinebackend.Client.OpenFeignClient;
import com.example.medicinebackend.Response.ApiResponse.MedicineData;
import com.example.medicinebackend.Response.GeneralMedicineResponse;
import com.example.medicinebackend.Response.MedicineResponseDto;
import com.example.medicinebackend.Response.RiskDataResponse;
import com.example.medicinebackend.Response.RiskDataResponse.Item;
import io.jsonwebtoken.lang.Objects;
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

    public List<List<MedicineResponseDto>> getMedicineData(List<String> medicineNames) {
        List<List<MedicineResponseDto>> combinedResponse = new ArrayList<>();
        for (String name : medicineNames) {
            combinedResponse.add(getMedicineDataByName(name));
        }
        log.info("combinedResponse : {}", combinedResponse);
        return combinedResponse;
    }


    public List<MedicineResponseDto> getMedicineDataByName(String productName) {
        List<MedicineResponseDto> riskData = getRiskMedicineDataByName(productName);
        if (riskData.isEmpty()||riskData==null) {
            List<MedicineResponseDto> generalMedicineData = getGeneralMedicineData(productName);
            return generalMedicineData;
        } else if (!riskData.isEmpty()) {
            return riskData;
        }

        return emptyList();
    }

    public List<MedicineResponseDto> getGeneralMedicineData(String productName) {
        GeneralMedicineResponse response = feignClient.getMedicineData(serviceKey, "json", productName);
        if (!Objects.nullSafeEquals(response.getHeader().getResultCode(), "00")) {
            throw new RuntimeException();
        }

        if (response.getBody().getTotalCount() > 10 || response.getBody().getTotalCount() == 0) {
            return emptyList(); //에러처리 하기
        }
        return convertToMedicineResponseDto(response);
    }


    public List<MedicineResponseDto> getRiskMedicineDataByName(String productName) {
        // 생각해보기 -> 자주 찾는 약물 생각해보기
        RiskDataResponse response = feignClient.getRiskMedicineData(serviceKey, "json", productName);
        RiskDataResponse drugsData = feignClient.getDrugsMedicineData(serviceKey, "json", productName);

        if (!Objects.nullSafeEquals(response.getHeader().getResultCode(), "00") || !Objects.nullSafeEquals(
                drugsData.getHeader().getResultCode(), "00")) {
            throw new RuntimeException();
        }

        if (response.getBody().getTotalCount() > 10 || response.getBody().getTotalCount() == 0) {
            if (drugsData.getBody().getTotalCount() > 10 || response.getBody().getTotalCount() == 0) {
                return emptyList();
            }

        }
        return convertToMedicineResponseDto(response);

    }
    public List<MedicineResponseDto> convertToMedicineResponseDto(GeneralMedicineResponse input) {
        MedicineResponseDto response = new MedicineResponseDto();
        List<MedicineResponseDto> responses = new ArrayList<>();

        List<GeneralMedicineResponse.Item> items = input.getBody().getItems();
        items.forEach(item -> {
            response.setItemName(item.getItemName());
            response.setItemCode(item.getItemSeq());
            response.setEfficiency(item.getEfcyQesitm());
            response.setWarn(item.getAtpnWarnQesitm() + "\n" + item.getAtpnQesitm()); // 경고 연결
            response.setSideEffect(item.getSeQesitm());
            response.setImage(item.getItemImage());
            response.setMaterial(null);
            response.setTypeName(null);
            responses.add(response);
        });

        return responses;
    }

    public List<MedicineResponseDto> convertToMedicineResponseDto(RiskDataResponse input) {
        MedicineResponseDto response = new MedicineResponseDto();
        List<MedicineResponseDto> responses = new ArrayList<>();

        List<RiskDataResponse.Item> items = input.getBody().getItems();
        items.forEach(item -> {
            response.setItemName(item.getItemName());
            response.setItemCode(item.getItemSeq());
            response.setEfficiency(null);
            response.setWarn(null);
            response.setSideEffect(null);
            response.setImage(null);
            response.setMaterial(item.getMaterialName());
            response.setTypeName(item.getTypeName());
            responses.add(response);
        });

        return responses;
    }

}







