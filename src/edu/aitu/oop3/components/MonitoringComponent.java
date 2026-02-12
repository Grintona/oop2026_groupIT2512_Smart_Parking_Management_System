package edu.aitu.oop3.components;

import edu.aitu.oop3.common.ListResult;
import edu.aitu.oop3.entities.ParkingSpot;
import edu.aitu.oop3.entities.Reservation;

public class MonitoringComponent {

    public int countFreeSpots(ListResult<ParkingSpot> freeSpots) {
        return freeSpots.getTotalCount();
    }

    public int countActiveReservations(ListResult<Reservation> reservations) {
        return reservations.getTotalCount();
    }
}
