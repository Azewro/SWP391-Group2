package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class UserPromotion {
    private int userPromoId;
    private int userId;  // Thay vì đối tượng User, dùng ID để đơn giản hóa JDBC
    private BigDecimal discountPercentage;
    private String promoCode;
    private Timestamp expirationDate;

    public UserPromotion() {
    }

    public UserPromotion(int userPromoId, int userId, BigDecimal discountPercentage, String promoCode, Timestamp expirationDate) {
        this.userPromoId = userPromoId;
        this.userId = userId;
        this.discountPercentage = discountPercentage;
        this.promoCode = promoCode;
        this.expirationDate = expirationDate;
    }

    public UserPromotion(int userPromoId, int userId, double discountPercentage, String promoCode, Timestamp expirationDate) {
    }

    // Getters and Setters
    public int getUserPromoId() {
        return userPromoId;
    }

    public void setUserPromoId(int userPromoId) {
        this.userPromoId = userPromoId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }
}
