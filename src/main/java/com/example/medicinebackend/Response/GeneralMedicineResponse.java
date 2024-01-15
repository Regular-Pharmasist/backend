package com.example.medicinebackend.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GeneralMedicineResponse {

    private Header header;
    private Body body;

    // Getters and setters...

    @Data
    public static class Header {
        private String resultCode;
        private String resultMsg;

        // Getters and setters...

    }

    @Data
    @NoArgsConstructor
    public static class Body {
        private int pageNo;
        private int totalCount;
        private int numOfRows;
        private List<Item> items;

        public List<Item> getItems() {
            return items;
        }
    }

    @Data
    @NoArgsConstructor
    public static class Item {
        @JsonProperty("entpName")
        private String entpName;

        @JsonProperty("itemName")
        private String itemName;

        @JsonProperty("itemSeq")
        private String itemSeq;

        @JsonProperty("efcyQesitm")
        private String efcyQesitm;

        @JsonProperty("useMethodQesitm")
        private String useMethodQesitm;

        @JsonProperty("atpnWarnQesitm")
        private String atpnWarnQesitm;

        @JsonProperty("atpnQesitm")
        private String atpnQesitm;

        @JsonProperty("intrcQesitm")
        private String intrcQesitm;

        @JsonProperty("seQesitm")
        private String seQesitm;

        @JsonProperty("depositMethodQesitm")
        private String depositMethodQesitm;

        @JsonProperty("openDe")
        private String openDe;

        @JsonProperty("updateDe")
        private String updateDe;

        @JsonProperty("itemImage")
        private String itemImage;

        @JsonProperty("bizrno")
        private String bizrno;

        public String getItemName() {
            return itemName;
        }

        // Getters and setters...
    }
}
