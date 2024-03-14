package com.example.medicinebackend.Service;


import static java.util.Collections.emptyList;

import com.example.medicinebackend.Client.OpenFeignClient;
import com.example.medicinebackend.Entitiy.Medicine;
import com.example.medicinebackend.Entitiy.MedicineUsage;
import com.example.medicinebackend.Repository.MedicineRepository;
import com.example.medicinebackend.Repository.MedicineUsageRepository;
import com.example.medicinebackend.Response.ApiResponse.MedicineData;
import com.example.medicinebackend.Response.GeneralMedicineResponse;
import com.example.medicinebackend.Response.MedicineRecordResponseDto;
import com.example.medicinebackend.Response.MedicineResponseDto;
import com.example.medicinebackend.Response.PermittedDataResponse;
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
    private final MedicineUsageRepository medicineUsageRepository;

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

        if (!riskData.isEmpty()) {
            saveMedicineData(riskData);
            return riskData;
        }
        else if (!generalMedicineData.isEmpty()) {
            saveMedicineData(generalMedicineData);
            return generalMedicineData;
        }
//        else if (!riskData.isEmpty()) {
//            saveMedicineData(riskData);
//            return riskData;
//        }

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
        PermittedDataResponse response = feignClient.getPermittedMedicineData(serviceKey,"json",productName);
//        RiskDataResponse response = feignClient.getPregnantMedicineData(serviceKey, "json", productName);
//        RiskDataResponse drugsData = feignClient.getDrugsMedicineData(serviceKey, "json", productName);

        if (!Objects.nullSafeEquals(response.getHeader().getResultCode(), "00")) {
            throw new RuntimeException();
        }
        if (response.getBody().getTotalCount() > 10 || response.getBody().getTotalCount() == 0) {
            return emptyList(); //에러처리 하기
        }

//        if (!Objects.nullSafeEquals(response.getHeader().getResultCode(), "00") || !Objects.nullSafeEquals(
//                drugsData.getHeader().getResultCode(), "00")) {
//            throw new RuntimeException();
//        }
//
//        if (response.getBody().getTotalCount() > 10 || response.getBody().getTotalCount() == 0) {
//            if (drugsData.getBody().getTotalCount() > 10 || response.getBody().getTotalCount() == 0) {
//                return emptyList();
//            }
//
//        }
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

    public List<MedicineResponseDto> convertToMedicineResponseDto(PermittedDataResponse input) {
        MedicineResponseDto response = new MedicineResponseDto();
        List<MedicineResponseDto> responses = new ArrayList<>();

        List<PermittedDataResponse.Item> items = input.getBody().getItems();
        items.forEach(item -> {
            response.setItemName(item.getItemName());
            response.setItemCode(item.getItemSeq());
            response.setEfficiency(String.valueOf(item.getEeDocData()));
            response.setWarn(String.valueOf(item.getUdDocData()));
            response.setSideEffect(String.valueOf(item.getNbDocData()));
            response.setImage(null);
            response.setMaterial(item.getMaterialName());
            response.setTypeName(item.getCancelName());
            responses.add(response);
        });

        return responses;
    }

    public void saveMedicineData(List<MedicineResponseDto> dtoList) {
        List<Medicine> medicines = new ArrayList<>();
        for (MedicineResponseDto dto : dtoList) {
            List<Medicine> existingMedicine = medicineRepository.findByItemCode(dto.getItemCode());

            if (existingMedicine.isEmpty()) {
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


    public List<MedicineRecordResponseDto> getMedicineRecord(Long memberId) {
        List<MedicineRecordResponseDto> medicineRecordResponseDtos = new ArrayList<>();
        List<MedicineUsage> medicines = medicineUsageRepository.findByMemberId(memberId);
        for(MedicineUsage item : medicines){
            MedicineRecordResponseDto medicineRecordResponseDto = new MedicineRecordResponseDto();

            Medicine medicine = item.getMedicineId();
            medicineRecordResponseDto.setItemName(medicine.getItemName());
            medicineRecordResponseDto.setIsActive(item.getIsActive());
            medicineRecordResponseDto.setStartDate(item.getStartDate());
            medicineRecordResponseDto.setEndDate(item.getEndDate());
            medicineRecordResponseDto.setDailyFrequency(item.getDailyFrequency());
            medicineRecordResponseDto.setImage(medicine.getImage());
            medicineRecordResponseDto.setTypeName(medicine.getTypeName());

            medicineRecordResponseDtos.add(medicineRecordResponseDto);
        }

        return medicineRecordResponseDtos;

    }

    public MedicineResponseDto getSpecificMedicine(String medicineName) {
        List<Medicine> medicines = medicineRepository.findByItemName(medicineName);
        MedicineResponseDto  medicineResponseDtos = new MedicineResponseDto();
        for(Medicine medicine : medicines){
            medicineResponseDtos.setItemName(medicine.getItemName());
            medicineResponseDtos.setItemCode(medicine.getItemCode());
            medicineResponseDtos.setEfficiency(medicine.getEfficiency());
            medicineResponseDtos.setSideEffect(medicine.getSideEffect());
            medicineResponseDtos.setWarn(medicine.getWarn());
            medicineResponseDtos.setMaterial(medicine.getMaterial());
            medicineResponseDtos.setImage(medicine.getItemName());
        }
        return medicineResponseDtos;
    }
}







