package com.example.medicinebackend.Service;

import com.example.medicinebackend.Entitiy.Medicine;
import com.example.medicinebackend.Entitiy.MedicineUsage;
import com.example.medicinebackend.Entitiy.Member;
import com.example.medicinebackend.Repository.MedicineRepository;
import com.example.medicinebackend.Repository.MedicineUsageRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MedicineUsageService {
    private final MedicineRepository medicineRepository;
    private final MedicineUsageRepository medicineUsageRepository;
    private final MemberService memberService;
    @Transactional
    public void saveUserUsage(String name, int frequency, int duration, boolean isActive, Date startDate, Date endDate){
        Optional<Medicine> optionalMedicine = medicineRepository.findByItemName(name);
        Medicine medicineId = optionalMedicine.get();
        Member member = memberService.getCurrentMemberId();

        MedicineUsage medicineUsage = new MedicineUsage();
        medicineUsage.setMedicineId(medicineId);
        medicineUsage.setMemberId(member);
        medicineUsage.setDailyFrequency(frequency);
        medicineUsage.setDuration(duration);
        medicineUsage.setIsActive(isActive);
        medicineUsage.setStartDate(startDate);
        medicineUsage.setEndDate(endDate);

    }

}
