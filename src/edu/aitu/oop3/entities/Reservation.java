package edu.aitu.oop3.entities;

import java.sql.Timestamp;

public class Reservation {
    private int id;
    private int vehicleId;
    private int spotId;
    private int tariffId;
    private Timestamp startTime;
    private Timestamp endTime; // может быть null

    public Reservation(int id ,int vehicleId, int spotId, int tariffId, Timestamp startTime) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.spotId = spotId;
        this.tariffId = tariffId;
        this.startTime = startTime;
        this.endTime = null;
    }
    public Reservation(int id ,int vehicleId, int spotId, int tariffId, Timestamp startTime, Timestamp endTime) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.spotId = spotId;
        this.tariffId = tariffId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public int getSpotId() {
        return spotId;
    }

    public int getTariffId() {
        return tariffId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }


    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
    @Override
    public String toString() {
        String status = (endTime == null) ? "ACTIVE" : "FINISHED";

        return "Reservation{" +
                "id=" + id +
                ", vehicleId=" + vehicleId +
                ", spotId=" + spotId +
                ", tariffId=" + tariffId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                '}';
    }
}

