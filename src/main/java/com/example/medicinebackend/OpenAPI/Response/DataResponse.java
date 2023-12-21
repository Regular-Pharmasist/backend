package com.example.medicinebackend.OpenAPI.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class DataResponse {
    private int page;
    private int perPage;
    private int totalCount;
    private int currentCount;
    private int matchCount;

    @JsonProperty("data")
    private List<MedicineData> data;

    @Getter
    @NoArgsConstructor
    @ToString
    public static class MedicineData {
        @JsonProperty("성분명")
        private String ingredientName;

        @JsonProperty("성분코드")
        private String ingredientCode;

        @JsonProperty("의약품코드")
        private int medicineCode;

        @JsonProperty("의약품명")
        private String medicineName;

        @JsonProperty("업체명")
        private String companyName;

        @JsonProperty("관리번호")
        private int managementNumber;

        @JsonProperty("관리일자")
        private String managementDate;

        @JsonProperty("비고")
        private String remarks;

        @JsonProperty("버전정보")
        private int versionInfo;

        @JsonProperty("급여여부")
        private String paymentStatus;

    }
}
