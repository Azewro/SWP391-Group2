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

    // ✅ Constructor không tham số (Bắt buộc cho Hibernate)
    public RouteStop() {}

    // ✅ Constructor chỉ có ID (Dùng để xóa hoặc tham chiếu đơn giản)
    public RouteStop(int id) {
        this.id = id;
    }

    // ✅ Constructor đầy đủ để lấy danh sách điểm dừng
    public RouteStop(int id, Route route, BusStop stop, int stopOrder, BigDecimal distanceFromStart, int estimatedTimeFromStart) {
        this.id = id;
        this.route = route;
        this.stop = stop;
        this.stopOrder = stopOrder;
        this.distanceFromStart = distanceFromStart;
        this.estimatedTimeFromStart = estimatedTimeFromStart;
    }

    // ✅ Constructor dùng để thêm mới điểm dừng
    public RouteStop(Route route, BusStop stop, int stopOrder, BigDecimal distanceFromStart, int estimatedTimeFromStart) {
        this.route = route;
        this.stop = stop;
        this.stopOrder = stopOrder;
        this.distanceFromStart = distanceFromStart;
        this.estimatedTimeFromStart = estimatedTimeFromStart;
    }

    // ✅ Constructor dùng khi cập nhật điểm dừng (có ID)
    public RouteStop(int id, int stopOrder, BigDecimal distanceFromStart, int estimatedTimeFromStart) {
        this.id = id;
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

