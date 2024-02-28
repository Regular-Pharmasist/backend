package com.example.medicinebackend.Repository;

import com.example.medicinebackend.Entitiy.MedicineUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineUsageRepository extends JpaRepository<MedicineUsage,Long> {
}
