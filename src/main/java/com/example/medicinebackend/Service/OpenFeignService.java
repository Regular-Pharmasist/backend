package com.example.medicinebackend.Service;


import static java.util.Collections.emptyList;

import com.example.medicinebackend.Client.OpenFeignClient;
import com.example.medicinebackend.Response.GeneralMedicineResponse;
import com.example.medicinebackend.Response.RiskDataResponse;
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

    public List<Object> getMedicineData(List<String> medicineNames) {
        List<Object> combinedResponse = new ArrayList<>();
        for (String name : medicineNames) {
            combinedResponse.add(getMedicineDataByName(name));
        }
        log.info("combinedResponse : {}", combinedResponse);
        return combinedResponse;
    }


    public Object getMedicineDataByName(String productName) {
        List<RiskDataResponse.Item> riskData = getRiskMedicineDataByName(productName);
        if (riskData.isEmpty()) {
            List<GeneralMedicineResponse.Item> generalMedicineData = getGeneralMedicineData(productName);
            return generalMedicineData;
        } else if (!riskData.isEmpty()) {
            return riskData;
        }

        return emptyList();// 빈 리스트 반환

    }

    public List<GeneralMedicineResponse.Item> getGeneralMedicineData(String productName) {
        GeneralMedicineResponse response = feignClient.getMedicineData(serviceKey, "json", productName);
        if (!Objects.nullSafeEquals(response.getHeader().getResultCode(), "00")) {
            throw new RuntimeException();
        }

        if (response.getBody().getTotalCount() > 10 || response.getBody().getTotalCount() == 0) {
            return emptyList();
        }

        return response.getBody().getItems().stream()
                .map(item -> new GeneralMedicineResponse.Item(
                        item.getEntpName(),
                        item.getItemName(),
                        item.getItemSeq(),
                        item.getEfcyQesitm(),
                        item.getUseMethodQesitm(),
                        item.getAtpnWarnQesitm(),
                        item.getAtpnQesitm(),
                        item.getIntrcQesitm(),
                        item.getSeQesitm(),
                        item.getDepositMethodQesitm(),
                        item.getOpenDe(),
                        item.getUpdateDe(),
                        item.getItemImage(),
                        item.getBizrno()))
                .collect(Collectors.toList());

    }


    public List<RiskDataResponse.Item> getRiskMedicineDataByName(String productName) {
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
            } else {
                return convertToRiskDataList(drugsData);
            }
        }

        return convertToRiskDataList(response);

    }

    public List<RiskDataResponse.Item> convertToRiskDataList(RiskDataResponse response) {
        return response.getBody().getItems().stream()
                .map(item -> new RiskDataResponse.Item(
                        item.getEntpName(),
                        item.getItemName(),
                        item.getItemSeq(),
                        item.getItemPermitDate(),
                        item.getEtcOtcCode(),
                        item.getClassNo(),
                        item.getChart(),
                        item.getBarCode(),
                        item.getMaterialName(),
                        item.getEeDocId(),
                        item.getUdDocId(),
                        item.getNbDocId(),
                        item.getInsertFile(),
                        item.getStorageMethod(),
                        item.getValidTerm(),
                        item.getPackUnit(),
                        item.getEdiCode(),
                        item.getTypeName(),
                        item.getChangeDate(),
                        item.getBizrno()))
                .collect(Collectors.toList());
    }

}

//    public void filterItemsByProductName(RiskDataResponse subjectData, String productName,
//                                         List<Item> combinedRiskData) {
//        if (subjectData != null && subjectData.getBody() != null && subjectData.getBody().getItems() != null) {
//            List<Item> items = subjectData.getBody().getItems();
//            for (Item item : items) {
//                if (item != null && item.getItemName().contains(productName)) {
//                    combinedRiskData.add(item);
//                }
//            }
//        }
//    }


