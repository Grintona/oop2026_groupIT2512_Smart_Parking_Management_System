package edu.aitu.oop3.factory;

import edu.aitu.oop3.entities.ParkingSpot;
import edu.aitu.oop3.entities.*;


public class ParkingSpotFactory {
    private ParkingSpotFactory() {}

    public static ParkingSpot create(String type, int id, String spotNumber, boolean isFree) {
        if (type == null) { type = "STANDARD";}
        type = type.toUpperCase();
        return switch (type) {
            case "ELECTRIC" ->  new ElectricSpot(id, spotNumber, isFree);
            case "DISABLED" ->  new DisabledSpot(id, spotNumber, isFree);
            default -> new StandardSpot(id, spotNumber, isFree);
        };
    }
}

