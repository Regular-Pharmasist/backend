package com.example.medicinebackend.Repository;

import com.example.medicinebackend.Entitiy.Medicine;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine,Long> {
    Long findMedicineIdByItemName(String itemName);

    Optional<Medicine> findByItemName(String name);

    Optional<Medicine> findByItemCode(String itemCode);
}
