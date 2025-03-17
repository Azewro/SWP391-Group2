package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BusMaintenanceLog {
    private int logId;
    private int busId;
    private LocalDateTime maintenanceDate;
    private String description;
    private BigDecimal cost;
    private String status;

    // Constructors
    public BusMaintenanceLog() {
    }

    public BusMaintenanceLog(int logId, int busId, LocalDateTime maintenanceDate, String description, BigDecimal cost, String status) {
        this.logId = logId;
        this.busId = busId;
        this.maintenanceDate = maintenanceDate;
        this.description = description;
        this.cost = cost;
        this.status = status;
    }

    // Getters and Setters
    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public LocalDateTime getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(LocalDateTime maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
