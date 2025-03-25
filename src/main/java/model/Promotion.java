package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Promotion {
    private int promotionId;
    private String promoCode;
    private BigDecimal discountAmount;
    private BigDecimal discountPercentage;
    private Timestamp validFrom;
    private Timestamp validTo;
    private boolean isActive;

    // Constructor đầy đủ
    public Promotion(int promotionId, String promoCode, BigDecimal discountAmount, BigDecimal discountPercentage, Timestamp validFrom, Timestamp validTo, boolean isActive) {
        this.promotionId = promotionId;
        this.promoCode = promoCode;
        this.discountAmount = discountAmount;
        this.discountPercentage = discountPercentage;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.isActive = isActive;
    }

    // Getters và Setters
    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Timestamp getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Timestamp validFrom) {
        this.validFrom = validFrom;
    }

    public Timestamp getValidTo() {
        return validTo;
    }

    public void setValidTo(Timestamp validTo) {
        this.validTo = validTo;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
