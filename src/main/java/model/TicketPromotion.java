package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TicketPromotion {
    private int promoId;
    private int ticketId;  // Thay vì đối tượng Ticket, dùng ID để đơn giản hóa JDBC
    private BigDecimal discountPercentage;
    private String promoCode;
    private Timestamp expirationDate;

    public TicketPromotion() {
    }

    public TicketPromotion(int promoId, int ticketId, BigDecimal discountPercentage, String promoCode, Timestamp expirationDate) {
        this.promoId = promoId;
        this.ticketId = ticketId;
        this.discountPercentage = discountPercentage;
        this.promoCode = promoCode;
        this.expirationDate = expirationDate;
    }

    public TicketPromotion(int promoId, int ticketId, double discountPercentage, String promoCode, Timestamp expirationDate) {
    }

    // Getters and Setters
    public int getPromoId() {
        return promoId;
    }

    public void setPromoId(int promoId) {
        this.promoId = promoId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
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
