package com.example.medicinebackend.Service;


import static java.util.Collections.emptyList;

import com.example.medicinebackend.Client.OpenFeignClient;
import com.example.medicinebackend.Entitiy.Medicine;
import com.example.medicinebackend.Entitiy.MedicineUsage;
import com.example.medicinebackend.Repository.MedicineRepository;
import com.example.medicinebackend.Repository.MedicineUsageRepository;
import com.example.medicinebackend.Response.ApiResponse.MedicineData;
import com.example.medicinebackend.Response.GeneralMedicineResponse;
import com.example.medicinebackend.Response.MedicineResponseDto;
import com.example.medicinebackend.Response.RiskDataResponse;
import com.example.medicinebackend.Response.RiskDataResponse.Item;
import io.jsonwebtoken.lang.Objects;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OpenFeignService {
    private final OpenFeignClient feignClient;
    private final MedicineRepository medicineRepository;

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
        List<MedicineResponseDto> generalMedicineData = getGeneralMedicineData(productName);

        if (!generalMedicineData.isEmpty()) {
            saveMedicineData(generalMedicineData);
            return generalMedicineData;
        }

        else if (!riskData.isEmpty()) {
            saveMedicineData(riskData);
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
//        List<MedicineUsage> medicineUsages = medicineUsageRepository.findAll();
//        List<Medicine> medicines = medicineRepository.findAll();
//        log.info("medicineUsage : {}", medicineUsages);
//        log.info("medicines : {}", medicines);

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

    public void saveMedicineData(List<MedicineResponseDto> dtoList) {
        List<Medicine> medicines = new ArrayList<>();
        for (MedicineResponseDto dto : dtoList) {
            Optional<Medicine> existingMedicine = medicineRepository.findByItemCode(dto.getItemCode());

            if (!existingMedicine.isPresent()) {
                Medicine medicine = new Medicine();
                medicine.setItemName(dto.getItemName());
                medicine.setItemCode(dto.getItemCode());
                medicine.setEfficiency(dto.getEfficiency());
                medicine.setWarn(dto.getWarn());
                medicine.setSideEffect(dto.getSideEffect());
                medicine.setImage(dto.getImage());
                medicine.setMaterial(dto.getMaterial());
                medicine.setTypeName(dto.getTypeName());
                medicines.add(medicine);
            }
        }
        log.info("Saved {} medicines to the database", medicines.size());
        medicineRepository.saveAll(medicines);
    }


}







