package model;

import java.sql.Timestamp;

public class CustomerFeedback {
    private int feedbackId;
    private int ticketId;
    private int rating;
    private String comment;
    private Timestamp createdAt;
    private String status;
    private String userName;

    // Constructor đúng với số lượng tham số từ DAO
    public CustomerFeedback(int feedbackId, int ticketId, int rating, String comment, Timestamp createdAt, String status) {
        this.feedbackId = feedbackId;
        this.ticketId = ticketId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.status = status;
    }

    public CustomerFeedback() {

    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Getters và Setters
    public int getFeedbackId() { return feedbackId; }
    public void setFeedbackId(int feedbackId) { this.feedbackId = feedbackId; }

    public int getTicketId() { return ticketId; }
    public void setTicketId(int ticketId) { this.ticketId = ticketId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
