package com.example.medicinebackend.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RiskDataResponse {
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
   @AllArgsConstructor
   public static class Item {
         @JsonProperty("ENTP_NAME")
         private String entpName;

         @JsonProperty("ITEM_NAME")
         private String itemName;

         @JsonProperty("ITEM_SEQ")
         private String itemSeq;

         @JsonProperty("ITEM_PERMIT_DATE")
         private String itemPermitDate;

         @JsonProperty("ETC_OTC_CODE")
         private String etcOtcCode;

         @JsonProperty("CLASS_NO")
         private String classNo;

         @JsonProperty("CHART")
         private String chart;

         @JsonProperty("BAR_CODE")
         private String barCode;

         @JsonProperty("MATERIAL_NAME")
         private String materialName;

         @JsonProperty("EE_DOC_ID")
         private String eeDocId;

         @JsonProperty("UD_DOC_ID")
         private String udDocId;

         @JsonProperty("NB_DOC_ID")
         private String nbDocId;

         @JsonProperty("INSERT_FILE")
         private String insertFile;

         @JsonProperty("STORAGE_METHOD")
         private String storageMethod;

         @JsonProperty("VALID_TERM")
         private String validTerm;

         @JsonProperty("PACK_UNIT")
         private String packUnit;

         @JsonProperty("EDI_CODE")
         private String ediCode;

         @JsonProperty("TYPE_NAME")
         private String typeName;

         @JsonProperty("CHANGE_DATE")
         private String changeDate;

         @JsonProperty("BIZRNO")
         private String bizrno;

         // 기타 필요한 getter, setter, 생성자 등
      }

   }

