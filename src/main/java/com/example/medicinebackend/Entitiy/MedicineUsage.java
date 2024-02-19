package com.example.medicinebackend.Entitiy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "medicine_usage")
public class MedicineUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usage_id")
    private Long usageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicineId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member memberId;

    @Column(nullable = false)
    private Integer dailyFrequency; // 일일 복용 횟수

    @Column(nullable = false)
    private Integer duration; // 복용 기간 (예: 일수)

    @Column(nullable = false)
    private Boolean isActive; // 복용 중인지 여부

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date startDate; // 복용 시작 날짜

    @Temporal(TemporalType.DATE)
    private Date endDate; // 복용 종료 날짜

}
