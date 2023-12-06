package com.example.medicinebackend;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "medicine")
public class MedicineEntity {
    @Id
    private int productCode;
    private String ingredientName;
    private int ingredientCode;
    private String productName;
    private String companyName;
    private String noticeDate;
    private int noticeNumber;
    private String benefit;
    private String tabooLevel;
    private String detail;

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(String noticeLog) {
        this.noticeDate = noticeLog;
    }

    public int getNoticeNumber() {
        return noticeNumber;
    }

    public void setNoticeNumber(int noticeNumber) {
        this.noticeNumber = noticeNumber;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public String getTabooLevel() {
        return tabooLevel;
    }

    public void setTabooLevel(String tabooLevel) {
        this.tabooLevel = tabooLevel;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
