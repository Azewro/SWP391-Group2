package model;

public class BusStop {
    private int stopId;
    private Location location;
    private String stopName;
    private Route route;
    private int stopOrder;
    private Integer estimatedWaitingTime;
    private boolean isActive;
    private String description;

    public BusStop() {}

    public BusStop(int stopId, String stopName) {
        this.stopId = stopId;
        this.stopName = stopName;
    }

    public BusStop(int stopId, Location location, String stopName, Route route, int stopOrder, Integer estimatedWaitingTime, boolean isActive, String description) {
        this.stopId = stopId;
        this.location = location;
        this.stopName = stopName;
        this.route = route;
        this.stopOrder = stopOrder;
        this.estimatedWaitingTime = estimatedWaitingTime;
        this.isActive = isActive;
        this.description = description;
    }

    public BusStop(int stopId, String stopName, Route route, int stopOrder, int estimatedWaitingTime, boolean isActive, String s) {
    }

    public int getStopId() { return stopId; }
    public void setStopId(int stopId) { this.stopId = stopId; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public String getStopName() { return stopName; }
    public void setStopName(String stopName) { this.stopName = stopName; }

    public Route getRoute() { return route; }
    public void setRoute(Route route) { this.route = route; }

    public int getStopOrder() { return stopOrder; }
    public void setStopOrder(int stopOrder) { this.stopOrder = stopOrder; }

    public Integer getEstimatedWaitingTime() { return estimatedWaitingTime; }
    public void setEstimatedWaitingTime(Integer estimatedWaitingTime) { this.estimatedWaitingTime = estimatedWaitingTime; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    public boolean getIsActive() { return isActive; }


    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getLocationId() {
        return (location != null) ? location.getLocationId() : 0; // Tránh lỗi NullPointerException
    }

}
