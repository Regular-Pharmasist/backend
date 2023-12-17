package com.example.medicinebackend.OpenAPI;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataResponse {
    private int currentCount;
    private List<MedicineData> data;
    private int matchCount;
    private int page;
    private int perPage;
    private int totalCount;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class MedicineData {
    @JsonProperty("고시번호")
    private String noticeNumber;
    @JsonProperty("고시일자")
    private String noticeDate;
    @JsonProperty("금기등급")
    private String tabooLevel;
    @JsonProperty("급여")
    private String benefit;
    @JsonProperty("상세정보")
    private String details;
    @JsonProperty("성분명")
    private String ingredientName;
    @JsonProperty("성분코드")
    private String ingredientCode;
    @JsonProperty("업체명")
    private String companyName;
    @JsonProperty("제품명")
    private String productName;
    @JsonProperty("제품코드")
    private int productCode;
}
