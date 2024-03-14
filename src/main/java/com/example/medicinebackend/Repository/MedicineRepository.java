package com.example.medicinebackend.Repository;

import com.example.medicinebackend.Entitiy.Medicine;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine,Long> {

    List<Medicine> findByItemName(String name);

    List<Medicine> findByItemCode(String itemCode);


}
