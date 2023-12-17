package com.example.medicinebackend;

import java.util.List;

public class ApiResponse {
    private int page;
    private int perPage;
    private int totalCount;
    private int currentCount;
    private int matchCount;
    private List<MedicineData> data;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public List<MedicineData> getData() {
        return data;
    }

    public void setData(List<MedicineData> data) {
        this.data = data;
    }

    public static class MedicineData {
        private int noticeNumber;
        private String noticeDate;
        private String tabooLevel;
        private String benefit;
        private String details;
        private String ingredientName;
        private int ingredientCode;
        private String companyName;
        private String productName;
        private int productCode;

        public int getNoticeNumber() {
            return noticeNumber;
        }

        public void setNoticeNumber(int noticeNumber) {
            this.noticeNumber = noticeNumber;
        }

        public String getNoticeDate() {
            return noticeDate;
        }

        public void setNoticeDate(String noticeDate) {
            this.noticeDate = noticeDate;
        }

        public String getTabooLevel() {
            return tabooLevel;
        }

        public void setTabooLevel(String tabooLevel) {
            this.tabooLevel = tabooLevel;
        }

        public String getBenefit() {
            return benefit;
        }

        public void setBenefit(String benefit) {
            this.benefit = benefit;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getIngredientName() {
            return ingredientName;
        }

        public void setIngredientName(String ingredientName) {
            this.ingredientName = ingredientName;
        }

        public int getIngredientCode() {
            return ingredientCode;
        }

        public void setIngredientCode(int ingredientCode) {
            this.ingredientCode = ingredientCode;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getProductCode() {
            return productCode;
        }

        public void setProductCode(int productCode) {
            this.productCode = productCode;
        }
    }

}
