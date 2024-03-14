package com.example.medicinebackend.Repository;

import com.example.medicinebackend.Entitiy.MedicineUsage;
import com.example.medicinebackend.Entitiy.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineUsageRepository extends JpaRepository<MedicineUsage,Long> {
    @Query("SELECT mu FROM MedicineUsage mu WHERE mu.memberId.memberId = :memberId")
    List<MedicineUsage> findByMemberId(@Param("memberId") Long memberId);

}
