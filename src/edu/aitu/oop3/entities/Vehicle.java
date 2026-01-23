package edu.aitu.oop3.entities;

public class Vehicle {
    private int id ;
    private String plateNumber;
    public Vehicle(int id, String plateNumber) {
        this.id = id;
        this.plateNumber = plateNumber;;
    }

    public int getId() {
        return id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

}