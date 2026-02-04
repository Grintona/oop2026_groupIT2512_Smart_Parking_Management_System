package edu.aitu.oop3.entities;

public class DisabledSpot extends ParkingSpot {

    public DisabledSpot(int id, String spotNumber, boolean isFree) {
        super(id, spotNumber, isFree);
    }


    @Override
    public String toString() {
        return "DisabledSpot{" +
                "id=" + getId() +
                ", number='" + getSpotNumber() + '\'' +
                ", free=" + isFree() +
                '}';
    }

}
