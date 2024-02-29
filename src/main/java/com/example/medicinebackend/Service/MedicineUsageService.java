package com.example.medicinebackend.Service;

import com.example.medicinebackend.Entitiy.Medicine;
import com.example.medicinebackend.Entitiy.MedicineUsage;
import com.example.medicinebackend.Entitiy.Member;
import com.example.medicinebackend.Repository.MedicineRepository;
import com.example.medicinebackend.Repository.MedicineUsageRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MedicineUsageService {
    private final MedicineRepository medicineRepository;
    private final MedicineUsageRepository medicineUsageRepository;

    @Transactional
    public void saveUserUsage(String name, int frequency, int duration, boolean isActive, Date startDate, Date endDate){
        Medicine medicine = medicineRepository.findByItemName(name)
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Medicine not found with name: " + name));
        ;
        Member memberId = SocialLoginService.currentMemberId;

        MedicineUsage medicineUsage = new MedicineUsage();
        medicineUsage.setMedicineId(medicine);
        medicineUsage.setMemberId(memberId);
        medicineUsage.setDailyFrequency(frequency);
        medicineUsage.setDuration(duration);
        medicineUsage.setIsActive(isActive);
        medicineUsage.setStartDate(startDate);
        medicineUsage.setEndDate(endDate);

        medicineUsageRepository.save(medicineUsage);
        log.info("MedicineUsage saved successfully for medicine: {}", name);

    }

}
