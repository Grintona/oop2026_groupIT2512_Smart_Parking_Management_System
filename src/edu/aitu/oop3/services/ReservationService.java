package edu.aitu.oop3.services;

import edu.aitu.oop3.entities.DisabledSpot;
import edu.aitu.oop3.entities.ParkingSpot;
import edu.aitu.oop3.entities.Reservation;
import edu.aitu.oop3.entities.Vehicle;
import edu.aitu.oop3.exceptions.DisabledSpotReservationException;
import edu.aitu.oop3.exceptions.NoFreeSpotsException;
import edu.aitu.oop3.exceptions.ReservationAlreadyActiveOrExpiredException;
import edu.aitu.oop3.repositories.ParkingSpotRepository;
import edu.aitu.oop3.repositories.ReservationRepository;
import edu.aitu.oop3.repositories.VehicleRepository;
import edu.aitu.oop3.exceptions.InvalidVehiclePlateException;

import java.sql.Timestamp;
import java.util.List;
public class ReservationService {
    private final ParkingSpotRepository parkingSpotRepository;
    private final VehicleRepository vehicleRepository;
    private final ReservationRepository reservationRepository;
    public ReservationService(ParkingSpotRepository parkingSpotRepository, VehicleRepository vehicleRepository, ReservationRepository reservationRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
        this.vehicleRepository = vehicleRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<ParkingSpot> listFreeSpots() {
        return parkingSpotRepository.findFreeSpots();
    }

    public Reservation reserveSpot(String plateNumber, int tariffId) {
        if (plateNumber == null || plateNumber.isBlank()) {
        throw new InvalidVehiclePlateException("Plate number cannot be empty");
    }
        String normalized = plateNumber.trim().toUpperCase();

        if (!normalized.matches("^[0-9]{3}[A-Z]{3}[0-9]{2}$")) {
            throw new InvalidVehiclePlateException("Invalid KZ plate number format. Example: 777ABC01");
        }
        plateNumber = normalized;
        Vehicle vehicle1 = vehicleRepository.findOrCreate(plateNumber);

        Reservation active = reservationRepository.findActiveByVehicleId(vehicle1.getId());
        if (active != null) {
            throw new ReservationAlreadyActiveOrExpiredException("Vehicle already has active reservation or expired: " + active.getId()
            );
        }
        ParkingSpot freeSpot = parkingSpotRepository.findFirstFreeSpot();
        if(freeSpot == null){
            throw new NoFreeSpotsException("No free parking spots available");
        }
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        int reservationId = reservationRepository.createReservation(vehicle1.getId(), freeSpot.getId(), tariffId, startTime);
        parkingSpotRepository.updateFreeStatus(freeSpot.getId(), false);
        return reservationRepository.findById(reservationId);
    }

    public Reservation releaseSpot(int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId);
        if (reservation == null) {
            throw new RuntimeException("Reservation not found: " + reservationId);
        }
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        boolean finished = reservationRepository.finishReservation(reservationId, endTime);
        if (!finished) {
            throw new RuntimeException("Reservation already finished or cannot be finished: " + reservationId);
        }
        parkingSpotRepository.updateFreeStatus(reservation.getSpotId(), true);
        return reservationRepository.findById(reservationId);
    }

    public List<Reservation> listReservationsByPlate(String plateNumber) {
        Vehicle vehicle = vehicleRepository.findByPlate(plateNumber);
        if (vehicle == null) {
            throw new RuntimeException("Vehicle not found: " + plateNumber);
        }
        return reservationRepository.findAllVehicleNumberReservations(vehicle.getId());
    }

    public Reservation reserveSpotByNumber(String plateNumber, String spotNumber, int tariffId) {
        if (plateNumber == null || plateNumber.isBlank()) {
            throw new InvalidVehiclePlateException("Plate number cannot be empty");
        }
        String normalized = plateNumber.trim().toUpperCase();

        if (!normalized.matches("^[0-9]{3}[A-Z]{3}[0-9]{2}$")) {
            throw new InvalidVehiclePlateException("Invalid KZ plate number format. Example: 777ABC01");
        }
        plateNumber = normalized;
        Vehicle vehicle1 = vehicleRepository.findOrCreate(plateNumber);

        Reservation active = reservationRepository.findActiveByVehicleId(vehicle1.getId());
        if (active != null) {
            throw new ReservationAlreadyActiveOrExpiredException("Vehicle already has active reservation or expired: " + active.getId()
            );
        }

        ParkingSpot spot =
                parkingSpotRepository.findByNumber(spotNumber);

        if (spot == null) {
            throw new RuntimeException(
                    "Spot not found: " + spotNumber
            );
        }

        if (!spot.isFree()) {
            throw new RuntimeException(
                    "Spot is not free"
            );
        }

        if (spot instanceof DisabledSpot) {
            throw new DisabledSpotReservationException("Disabled spot cannot be reserved");}

        Timestamp startTime = new Timestamp(System.currentTimeMillis());

        int reservationId = reservationRepository.createReservation(vehicle1.getId(),spot.getId(),tariffId, startTime);

        parkingSpotRepository.updateFreeStatus(spot.getId(), false);
        return reservationRepository.findById(reservationId);
    }
}