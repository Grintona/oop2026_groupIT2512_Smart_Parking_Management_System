package edu.aitu.oop3;

import edu.aitu.oop3.repositories.*;
import edu.aitu.oop3.services.*;
import edu.aitu.oop3.entities.*;

import java.sql.Timestamp;

public class Main {
    public static void main(String[] args) {


        VehicleRepository vehicleRepository = new VehicleRepository();
        ParkingSpotRepository parkingSpotRepository = new ParkingSpotRepository();
        ReservationRepository reservationRepository = new ReservationRepository();
        TariffRepository tariffRepository = new TariffRepository();
        }
}