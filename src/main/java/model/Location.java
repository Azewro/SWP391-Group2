package model;

import java.math.BigDecimal;

public class Location {
    private int locationId;
    private String name;

    public Location() {}

    public Location(int locationId) {
        this.locationId = locationId;
    }

    public Location(int locationId, String name) {
        this.locationId = locationId;
        this.name = name;
    }

    public int getLocationId() { return locationId; }
    public void setLocationId(int locationId) { this.locationId = locationId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
