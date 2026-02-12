package edu.aitu.oop3.components;

import edu.aitu.oop3.common.ListResult;
import edu.aitu.oop3.entities.Reservation;
import edu.aitu.oop3.services.ReservationService;

public class ReportingComponent {

    private final ReservationService reservationService;

    public ReportingComponent(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public ListResult<Reservation> byPlate(String plateNumber) {
        return reservationService.listReservationsByPlate(plateNumber);
    }
}

