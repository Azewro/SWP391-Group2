package model;

import java.math.BigDecimal;

public class RouteStop {
    private int id;
    private Route route;
    private BusStop stop;
    private int stopOrder;
    private BigDecimal distanceFromStart;
    private int estimatedTimeFromStart;

    public RouteStop() {}

    public RouteStop(int id) {
        this.id = id;
    }

    public RouteStop(int id, Route route, BusStop stop, int stopOrder, BigDecimal distanceFromStart, int estimatedTimeFromStart) {
        this.id = id;
        this.route = route;
        this.stop = stop;
        this.stopOrder = stopOrder;
        this.distanceFromStart = distanceFromStart;
        this.estimatedTimeFromStart = estimatedTimeFromStart;
    }

    public RouteStop(Route route, BusStop stop, int stopOrder, BigDecimal distanceFromStart, int estimatedTimeFromStart) {
        this.route = route;
        this.stop = stop;
        this.stopOrder = stopOrder;
        this.distanceFromStart = distanceFromStart;
        this.estimatedTimeFromStart = estimatedTimeFromStart;
    }
    public RouteStop(int id, BusStop stop, int stopOrder, BigDecimal distanceFromStart, int estimatedTimeFromStart) {
        this.id = id;
        this.stop = stop;
        this.stopOrder = stopOrder;
        this.distanceFromStart = distanceFromStart;
        this.estimatedTimeFromStart = estimatedTimeFromStart;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Route getRoute() { return route; }
    public void setRoute(Route route) { this.route = route; }

    public BusStop getStop() { return stop; }
    public void setStop(BusStop stop) { this.stop = stop; }

    public int getStopOrder() { return stopOrder; }
    public void setStopOrder(int stopOrder) { this.stopOrder = stopOrder; }

    public BigDecimal getDistanceFromStart() { return distanceFromStart; }
    public void setDistanceFromStart(BigDecimal distanceFromStart) { this.distanceFromStart = distanceFromStart; }

    public int getEstimatedTimeFromStart() { return estimatedTimeFromStart; }
    public void setEstimatedTimeFromStart(int estimatedTimeFromStart) { this.estimatedTimeFromStart = estimatedTimeFromStart; }
}
