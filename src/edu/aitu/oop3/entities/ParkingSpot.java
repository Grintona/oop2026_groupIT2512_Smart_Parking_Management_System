package edu.aitu.oop3.entities;

import java.util.List;

public class ParkingSpot  {
    private int id = 0;
    private String spotNumber;
    private boolean isFree = true ;
    private String spot_type;

    public ParkingSpot(int id, String spotNumber, boolean isFree) {
        this.id = id;
        this.spotNumber = spotNumber;
        this.isFree = isFree;
    }


    public String getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(String spotNumber) {
        this.spotNumber = spotNumber;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public int getId() {
        return id;
    }
    public String getType() {
        return getClass().getSimpleName();
    }

}