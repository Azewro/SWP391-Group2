package model;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private int routeId;

    @Column(name = "route_name", nullable = false, unique = true, length = 100)
    private String routeName;

    @ManyToOne
    @JoinColumn(name = "start_location_id", nullable = false)
    private Location startLocation;

    @ManyToOne
    @JoinColumn(name = "end_location_id", nullable = false)
    private Location endLocation;

    @Column(name = "distance", nullable = false)
    private float distance;

    @Column(name = "estimated_duration", nullable = false)
    private int estimatedDuration;

    @Column(name = "base_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "estimated_stops")
    private Integer estimatedStops;

    @Column(name = "route_type", length = 50)
    private String routeType;

    // ✅ Constructor không tham số (bắt buộc cho Hibernate)
    public Route() {}

    // ✅ Constructor chỉ có routeId (Dùng trong getRouteStops)
    public Route(int routeId) {
        this.routeId = routeId;
    }

    // ✅ Constructor đầy đủ tham số (Dùng khi lấy danh sách tuyến)
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

    // Getters and Setters
    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public Integer getEstimatedStops() {
        return estimatedStops;
    }

    public void setEstimatedStops(Integer estimatedStops) {
        this.estimatedStops = estimatedStops;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }
}
