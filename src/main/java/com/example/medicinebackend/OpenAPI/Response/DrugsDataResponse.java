package com.example.medicinebackend.OpenAPI.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collection;
import lombok.Data;

import java.util.List;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DrugsDataResponse {

    private Header header;
    private Body body;

    @Data
    public static class Header {
        @JsonProperty("resultCode")
        private String resultCode;

        @JsonProperty("resultMsg")
        private String resultMsg;
    }

    @Data
    @NoArgsConstructor
    public static class Body {
        @JsonProperty("pageNo")
        private int pageNo;

        @JsonProperty("totalCount")
        private int totalCount;

        @JsonProperty("numOfRows")
        private int numOfRows;

        @JsonProperty("items")
        private List<Item> items;

        public List<Item> getItems() {
            return items;
        }
    }
    @NoArgsConstructor
    @Data
    public static class Item {
        @JsonProperty("DRUG_NO")
        private String drugNo;

        @JsonProperty("DRFSTF")
        private String drugName;

        @JsonProperty("DRFSTF_ENG")
        private String drfstfEng;

        @JsonProperty("TYPE_CODE")
        private String typeCode;

        @JsonProperty("PHARM")
        private String pharm;

        @JsonProperty("SIDE_EFFECT")
        private String sideEffect;

        @JsonProperty("MEDICATION")
        private String medication;

        public String getDrugName() {
            return drugName;
        }
    }
}
