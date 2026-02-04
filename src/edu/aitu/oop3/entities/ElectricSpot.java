package edu.aitu.oop3.entities;

public class ElectricSpot extends ParkingSpot{
    public ElectricSpot(int id, String spotNumber, boolean isFree) {
        super(id, spotNumber, isFree);
    }

    @Override
    public String toString() {
        return "ElectricSpot{" + "id=" + getId() +  ", number='" + getSpotNumber() + '\'' + ", free=" + isFree() +'}';
    }
}
