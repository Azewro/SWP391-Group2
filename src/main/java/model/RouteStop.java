package model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "RouteStops")
public class RouteStop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @ManyToOne
    @JoinColumn(name = "stop_id", nullable = false)
    private BusStop stop;

    @Column(name = "stop_order", nullable = false)
    private int stopOrder;

    @Column(name = "distance_from_start", nullable = false, precision = 10, scale = 2)
    private BigDecimal distanceFromStart;

    @Column(name = "estimated_time_from_start", nullable = false)
    private int estimatedTimeFromStart;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public BusStop getStop() {
        return stop;
    }

    public void setStop(BusStop stop) {
        this.stop = stop;
    }

    public int getStopOrder() {
        return stopOrder;
    }

    public void setStopOrder(int stopOrder) {
        this.stopOrder = stopOrder;
    }

    public BigDecimal getDistanceFromStart() {
        return distanceFromStart;
    }

    public void setDistanceFromStart(BigDecimal distanceFromStart) {
        this.distanceFromStart = distanceFromStart;
    }

    public int getEstimatedTimeFromStart() {
        return estimatedTimeFromStart;
    }

    public void setEstimatedTimeFromStart(int estimatedTimeFromStart) {
        this.estimatedTimeFromStart = estimatedTimeFromStart;
    }
}

