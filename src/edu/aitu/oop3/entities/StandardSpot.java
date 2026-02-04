package edu.aitu.oop3.entities;

public class StandardSpot extends ParkingSpot {


    public StandardSpot(int id, String spotNumber, boolean isFree) {
        super(id, spotNumber, isFree);
    }


    @Override
    public String toString() {
        return "StandardSpot{" +
                "id=" + getId() +
                ", number='" + getSpotNumber() + '\'' +
                ", free=" + isFree() +
                '}';
    }
}
