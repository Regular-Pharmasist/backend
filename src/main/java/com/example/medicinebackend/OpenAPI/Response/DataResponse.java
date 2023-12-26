package com.example.medicinebackend.OpenAPI.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
public class DataResponse {
    private Header header;
    private Body body;

    @Data
    @NoArgsConstructor
    public static class Header {
        @JsonProperty("resultCode")
        private String resultCode;

        @JsonProperty("resultMsg")
        private String resultMsg;
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class Body {
        @JsonProperty("numOfRows")
        private int numOfRows;

        @JsonProperty("pageNo")
        private int pageNo;

        @JsonProperty("totalCount")
        private int totalCount;

        @JsonProperty("items")
        private ItemWrapper items;

        @Getter
        @NoArgsConstructor
        @ToString
        public static class ItemWrapper {
            @JsonProperty("item")
            private Item item;

            @Getter
            @NoArgsConstructor
            @ToString
            public static class Item {
                @JsonProperty("TYPE_NAME")
                private String typeName;

                @JsonProperty("MIX_TYPE")
                private String mixType;

                @JsonProperty("INGR_CODE")
                private String ingrCode;

                @JsonProperty("INGR_ENG_NAME")
                private String ingrEngName;

                @JsonProperty("INGR_NAME")
                private String ingrName;

                @JsonProperty("MIX_INGR")
                private String mixIngr;

                @JsonProperty("FORM_NAME")
                private String formName;

                @JsonProperty("ITEM_SEQ")
                private String itemSeq;

                @JsonProperty("ITEM_NAME")
                private String itemName;

                @JsonProperty("ITEM_PERMIT_DATE")
                private String itemPermitDate;

                @JsonProperty("ENTP_NAME")
                private String entpName;

                @JsonProperty("CHART")
                private String chart;

                @JsonProperty("CLASS_CODE")
                private String classCode;

                @JsonProperty("CLASS_NAME")
                private String className;

                @JsonProperty("ETC_OTC_NAME")
                private String etcOtcName;

                @JsonProperty("MAIN_INGR")
                private String mainIngr;

                @JsonProperty("NOTIFICATION_DATE")
                private String notificationDate;

                @JsonProperty("PROHBT_CONTENT")
                private String prohbtContent;

                @JsonProperty("REMARK")
                private String remark;

                @JsonProperty("INGR_ENG_NAME_FULL")
                private String ingrEngNameFull;

                @JsonProperty("CHANGE_DATE")
                private String changeDate;
            }
        }
    }
}
