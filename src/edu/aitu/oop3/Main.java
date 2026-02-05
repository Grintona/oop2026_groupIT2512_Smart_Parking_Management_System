package edu.aitu.oop3;

import edu.aitu.oop3.common.ListResult;
import edu.aitu.oop3.entities.ParkingSpot;
import edu.aitu.oop3.entities.Reservation;
import edu.aitu.oop3.entities.Tariff;
import edu.aitu.oop3.exceptions.InvalidVehiclePlateException;
import edu.aitu.oop3.exceptions.NoFreeSpotsException;
import edu.aitu.oop3.exceptions.ReservationAlreadyActiveOrExpiredException;
import edu.aitu.oop3.repositories.ParkingSpotRepository;
import edu.aitu.oop3.repositories.ReservationRepository;
import edu.aitu.oop3.repositories.TariffRepository;
import edu.aitu.oop3.repositories.VehicleRepository;
import edu.aitu.oop3.services.PricingService;
import edu.aitu.oop3.services.ReservationService;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ParkingSpotRepository parkingSpotRepository = new ParkingSpotRepository();
        VehicleRepository vehicleRepository = new VehicleRepository();
        ReservationRepository reservationRepository = new ReservationRepository();
        TariffRepository tariffRepository = new TariffRepository();
        ReservationService reservationService = new ReservationService(parkingSpotRepository, vehicleRepository, reservationRepository);
        PricingService pricingService = new PricingService(tariffRepository);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n**********************PARKING SYSTEM CLI**********************");
            System.out.println("1) List free spots");
            System.out.println("2) Reserve a spot");
            System.out.println("3) Release a spot");
            System.out.println("4) Calculate cost");
            System.out.println("5) Reserve by spot number");
            System.out.println("0) Exit");
            System.out.print("Choose option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            try {
                if (choice == 0) {
                    System.out.println("Bye!");
                    break;
                }

                switch (choice) {

                    case 1 -> {
                        System.out.println("\n**********************FREE SPOTS**********************");

                        ListResult<ParkingSpot> result = reservationService.listFreeSpots();

                        if (result.getTotalCount() == 0) {
                            System.out.println("No free spots.");
                            break;
                        }
                        System.out.println("Total: " + result.getTotalCount());

                        for (ParkingSpot spot : result.getItems()) {
                            System.out.println(
                                    "ID: " + spot.getId()
                                            + " | Spot: " + spot.getSpotNumber()
                                            + " | Free: " + spot.isFree()
                                            + " | Type: " + spot.getType()
                            );
                        }
                    }

                    case 2 -> {
                        System.out.print("Enter plate number (example 777ABC01): ");
                        String plateNumber = scanner.nextLine();
                        if (plateNumber == null || plateNumber.isBlank()) {
                            throw new InvalidVehiclePlateException("Plate number cannot be empty");
                        }
                        String normalized = plateNumber.trim().toUpperCase();
                        if (!normalized.matches("^[0-9]{3}[A-Z]{3}[0-9]{2}$")) {
                            throw new InvalidVehiclePlateException("Invalid KZ plate number format. Example: 777ABC01");
                        }
                        System.out.println("\n**********************AVAILABLE TARIFFS**********************");
                        List<Tariff> tariffs = tariffRepository.findAllTariffs();
                        if (tariffs.isEmpty()) {
                            System.out.println("No tariffs found in database!");
                            break;
                        }
                        for (Tariff t : tariffs) {
                            System.out.println("ID: " + t.getId()
                                    + " | Name: " + t.getName()
                                    + " | Price/hour: " + t.getPricePerHour());
                        }
                        System.out.print("Enter tariff id: ");
                        int tariffId = Integer.parseInt(scanner.nextLine());
                        Reservation reservation = reservationService.reserveSpot(plateNumber, tariffId);
                        System.out.println("\nReserved successfully!");
                        System.out.println("Reservation ID: " + reservation.getId());
                        System.out.println("Spot ID: " + reservation.getSpotId());
                        System.out.println("Start time: " + reservation.getStartTime());
                    }
                    case 3 -> {
                        System.out.print("Enter vehicle plate number: ");
                        String plateNumber = scanner.nextLine();
                        if (plateNumber == null || plateNumber.isBlank()) {
                            throw new InvalidVehiclePlateException(
                                    "Plate number cannot be empty");
                        }
                        String normalized = plateNumber.trim().toUpperCase();
                        if (!normalized.matches("^[0-9]{3}[A-Z]{3}[0-9]{2}$")) {
                            throw new InvalidVehiclePlateException("Invalid KZ plate format. Example: 777ABC01");
                        }
                        ListResult<Reservation> result = reservationService.listReservationsByPlate(normalized);
                        if (result.getTotalCount() == 0) {
                            System.out.println("No reservations found.");
                            break;
                        }
                        System.out.println("\nReservations:");
                        for (Reservation r : result.getItems()) {
                            System.out.println(r);
                        }
                        System.out.print("\nEnter reservation id to release: ");
                        int reservationId = Integer.parseInt(scanner.nextLine());
                        Reservation finished = reservationService.releaseSpot(reservationId);
                        System.out.println("\nReleased successfully!");
                        System.out.println("Reservation ID: " + finished.getId());
                        System.out.println("End time: " + finished.getEndTime());
                    }


                    case 4 -> {
                        System.out.print("Enter vehicle plate number: ");
                        String plateNumber = scanner.nextLine();
                        if (plateNumber == null || plateNumber.isBlank()) {
                            throw new InvalidVehiclePlateException("Plate number cannot be empty");
                        }
                        String normalized = plateNumber.trim().toUpperCase();

                        if (!normalized.matches("^[0-9]{3}[A-Z]{3}[0-9]{2}$")) {
                            throw new InvalidVehiclePlateException(
                                    "Invalid KZ plate format. Example: 777ABC01");
                        }
                        ListResult<Reservation> result = reservationService.listReservationsByPlate(normalized);
                        if (result.getTotalCount() == 0) {
                            System.out.println("No reservations found.");
                            break;
                        }
                        System.out.println("\nReservations:");
                        for (Reservation r : result.getItems()) {
                            System.out.println(r);
                        }
                        System.out.print("\nEnter reservation id to calculate price: ");
                        int reservationId = Integer.parseInt(scanner.nextLine());
                        Reservation reservation = reservationService.getReservationById(reservationId);

                        if (reservation == null) {
                            System.out.println("Reservation not found!");
                            break;
                        }
                        int price = pricingService.calculatePrice(reservation);
                        System.out.println("\nTotal cost: " + price);
                    }


                    case 5 -> {
                        System.out.print("Enter plate number: ");
                        String plate = scanner.nextLine();
                        System.out.print("Enter spot number: ");
                        String spot = scanner.nextLine();
                        List<Tariff> tariffs = tariffRepository.findAllTariffs();
                        for (Tariff t : tariffs) {
                            System.out.println("ID: " + t.getId()
                                    + " | Name: " + t.getName()
                                    + " | Price/hour: " + t.getPricePerHour());
                        }
                        System.out.print("Enter tariff id: ");
                        int tariffId = Integer.parseInt(scanner.nextLine());
                        Reservation r = reservationService.reserveSpotByNumber(plate, spot, tariffId);
                        System.out.println("Reserved: " + r);
                    }
                    default -> System.out.println("Unknown option. Try again.");
                }

            } catch (InvalidVehiclePlateException e) {
                System.out.println("Invalid plate: " + e.getMessage());

            } catch (NoFreeSpotsException e) {
                System.out.println("No free spots: " + e.getMessage());

            } catch (ReservationAlreadyActiveOrExpiredException e) {
                System.out.println("Reservation error: " + e.getMessage());

            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
