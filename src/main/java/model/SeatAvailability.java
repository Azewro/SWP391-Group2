/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

/**
 *
 * @author author
 */
public class SeatAvailability {
    private int seatId;
    private int seatNumber;
    private String seatType;
    private boolean isAvailable;

    public SeatAvailability(int seatId, int seatNumber, String seatType, boolean isAvailable) {
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.isAvailable = isAvailable;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "SeatAvailability{" +
                "seatId=" + seatId +
                ", seatNumber=" + seatNumber +
                ", seatType='" + seatType + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}

