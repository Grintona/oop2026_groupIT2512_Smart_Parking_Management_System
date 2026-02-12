package edu.aitu.oop3.components;

import edu.aitu.oop3.common.ListResult;
import edu.aitu.oop3.entities.ParkingSpot;
import edu.aitu.oop3.entities.Reservation;
import edu.aitu.oop3.services.ReservationService;

public class ReservationComponent {
    private final ReservationService reservationService;
    public ReservationComponent(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    public ListResult<ParkingSpot> listFreeSpots(){
        return reservationService.listFreeSpots();
    }

    public Reservation reserve(String plate, int tariffId){
        return reservationService.reserveSpot(plate,tariffId);
    }

    public Reservation reserveByNumber(String plate, String spot, int tariffId){
        return reservationService.reserveSpotByNumber(plate,spot,tariffId);
    }

    public Reservation release(int reservationId){
        return reservationService.releaseSpot(reservationId);
    }

    public ListResult<Reservation> byPlate(String plate){
        return reservationService.listReservationsByPlate(plate);
    }

}
