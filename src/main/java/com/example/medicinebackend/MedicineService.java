package com.example.medicinebackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MedicineService {
    private final MedicineRepository medicineRepository;

    @Value("${api.serviceKey}")
    private String serviceKey;
    @Autowired
    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public void fetchDataFromAPIandStore() {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://api.odcloud.kr/api/15089735/v1/resource?serviceKey=" + serviceKey;
        ApiResponse response = restTemplate.getForObject(apiUrl, ApiResponse.class);

        // API 응답 엔티티로 변환하고 저장
        for (ApiResponse.MedicineData data : response.getData()) {
            MedicineEntity entity = convertToEntity(data);
            medicineRepository.save(entity);
        }
    }

    private MedicineEntity convertToEntity(ApiResponse.MedicineData data) {
        MedicineEntity medicineEntity = new MedicineEntity();

        medicineEntity.setBenefit(data.getBenefit());
        medicineEntity.setDetail(data.getDetails());
        medicineEntity.setCompanyName(data.getCompanyName());
        medicineEntity.setIngredientCode(data.getIngredientCode());
        medicineEntity.setIngredientName(data.getIngredientName());
        medicineEntity.setNoticeDate(data.getNoticeDate());
        medicineEntity.setNoticeNumber(data.getNoticeNumber());
        medicineEntity.setProductCode(data.getProductCode());
        medicineEntity.setProductName(data.getProductName());
        medicineEntity.setTabooLevel(data.getTabooLevel());

        return medicineEntity;
    }
}
