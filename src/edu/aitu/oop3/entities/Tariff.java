package edu.aitu.oop3.entities;


public class Tariff {

    private int id;
    private String name;
    private int pricePerHour;

    public Tariff(int id, String name, int pricePerHour) {
        this.id = id;
        this.name = name;
        this.pricePerHour = pricePerHour;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(int pricePerHour) {
        if (pricePerHour < 0) {
            throw new IllegalArgumentException("Price per hour cannot be negative");
        }
        this.pricePerHour = pricePerHour;
    }
    @Override
    public String toString(){
        return "Tariff{" +
                "id="+ id +
                ", name='" +name +
                ", pricePerHour=" + pricePerHour + "}";
    }
}
