package model;

import java.math.BigDecimal;

public class Route {
    private int routeId;
    private String routeName;
    private Location startLocation;
    private Location endLocation;
    private float distance;
    private int estimatedDuration;
    private BigDecimal basePrice;
    private Integer estimatedStops;
    private String routeType;

    public Route() {}

    public Route(int routeId) {
        this.routeId = routeId;
    }

    public Route(int routeId, String routeName) {
        this.routeId = routeId;
        this.routeName = routeName;
    }

    public Route(int routeId, String routeName, Location startLocation, Location endLocation,
                 float distance, int estimatedDuration, BigDecimal basePrice) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.distance = distance;
        this.estimatedDuration = estimatedDuration;
        this.basePrice = basePrice;
    }

    public Route(int routeId, String routeName, Location startLocation, Location endLocation,
                 float distance, int estimatedDuration, BigDecimal basePrice, Integer estimatedStops, String routeType) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.distance = distance;
        this.estimatedDuration = estimatedDuration;
        this.basePrice = basePrice;
        this.estimatedStops = estimatedStops;
        this.routeType = routeType;
    }


    // Getters and Setters
    public int getRouteId() { return routeId; }
    public void setRouteId(int routeId) { this.routeId = routeId; }

    public String getRouteName() { return routeName; }
    public void setRouteName(String routeName) { this.routeName = routeName; }

    public Location getStartLocation() { return startLocation; }
    public void setStartLocation(Location startLocation) { this.startLocation = startLocation; }

    public Location getEndLocation() { return endLocation; }
    public void setEndLocation(Location endLocation) { this.endLocation = endLocation; }

    public float getDistance() { return distance; }
    public void setDistance(float distance) { this.distance = distance; }

    public int getEstimatedDuration() { return estimatedDuration; }
    public void setEstimatedDuration(int estimatedDuration) { this.estimatedDuration = estimatedDuration; }

    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }

    public Integer getEstimatedStops() { return estimatedStops; }
    public void setEstimatedStops(Integer estimatedStops) { this.estimatedStops = estimatedStops; }

    public String getRouteType() { return routeType; }
    public void setRouteType(String routeType) { this.routeType = routeType; }
}
