package model;

import java.math.BigDecimal;

public class OrderDetail {
    private int orderDetailId;
    private Order order;
    private Ticket ticket;
    private BigDecimal price;

    public OrderDetail() {}

    public OrderDetail(int orderDetailId, Order order, Ticket ticket, BigDecimal price) {
        this.orderDetailId = orderDetailId;
        this.order = order;
        this.ticket = ticket;
        this.price = price;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
