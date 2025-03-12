package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Ticket {
    private int ticketId;
    private User user;
    private BusTrip trip;
    private Seat seat;
    private LocalDateTime purchaseDate;
    private BigDecimal price;
    private String status;

    public Ticket() {}

    public Ticket(int ticketId, User user, BusTrip trip, Seat seat, LocalDateTime purchaseDate, BigDecimal price, String status) {
        this.ticketId = ticketId;
        this.user = user;
        this.trip = trip;
        this.seat = seat;
        this.purchaseDate = purchaseDate;
        this.price = price;
        this.status = status;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BusTrip getTrip() {
        return trip;
    }

    public void setTrip(BusTrip trip) {
        this.trip = trip;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
