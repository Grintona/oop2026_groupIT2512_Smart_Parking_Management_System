package edu.aitu.oop3;

import edu.aitu.oop3.common.ListResult;
import edu.aitu.oop3.config.TariffConfig;
import edu.aitu.oop3.entities.*;
import edu.aitu.oop3.exceptions.InvalidVehiclePlateException;
import edu.aitu.oop3.exceptions.NoFreeSpotsException;
import edu.aitu.oop3.exceptions.ReservationAlreadyActiveOrExpiredException;

import edu.aitu.oop3.repositories.*;
import edu.aitu.oop3.services.*;

import edu.aitu.oop3.components.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ParkingSpotRepository parkingSpotRepository = new ParkingSpotRepository();
        VehicleRepository vehicleRepository = new VehicleRepository();
        ReservationRepository reservationRepository = new ReservationRepository();
        TariffRepository tariffRepository = new TariffRepository();
        ReservationService reservationService =  new ReservationService(parkingSpotRepository, vehicleRepository, reservationRepository);
        PricingService pricingService = new PricingService(tariffRepository);

        // Components
        ReservationComponent reservationComponent =  new ReservationComponent(reservationService);

        PaymentComponent paymentComponent =  new PaymentComponent(pricingService);

        ReportingComponent reportingComponent = new ReportingComponent(reservationService);

        MonitoringComponent monitoringComponent = new MonitoringComponent(parkingSpotRepository);

        // CLI
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

                        // ✅ ИЗМЕНЕНО — через компонент, не сервис
                        ListResult<ParkingSpot> result =
                                reservationComponent.listFreeSpots();

                        System.out.println("Total free: " + monitoringComponent.countFreeSpots()); // ✅ показали MonitoringComponent

                        if (result.getTotalCount() == 0) {
                            System.out.println("No free spots.");
                            break;
                        }

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

                        System.out.print("Enter plate number: ");
                        String plateNumber = scanner.nextLine();
                        System.out.println("\nAvailable tariffs:");
                        List<Tariff> tariffs = tariffRepository.findAllTariffs();
                        tariffs.forEach(System.out::println);
                        System.out.print("Enter tariff id (Enter = default): ");
                        String input = scanner.nextLine();
                        int tariffId = input.isBlank()
                                ? TariffConfig.getInstance().getDefaultTariffId()
                                : Integer.parseInt(input);

                        // Using component
                        Reservation reservation = reservationComponent.reserve(plateNumber, tariffId);
                        System.out.println("Reserved successfully!");
                        System.out.println(reservation);
                    }

                    case 3 -> {
                        System.out.print("Enter vehicle plate number: ");
                        String plate = scanner.nextLine();

                        // using reportingComponent
                        ListResult<Reservation> result = reportingComponent.byPlate(plate);

                        if (result.getTotalCount() == 0) {
                            System.out.println("No reservations found.");
                            break;
                        }
                        result.getItems().forEach(System.out::println);
                        System.out.print("\nEnter reservation id to release: ");
                        int reservationId =
                                Integer.parseInt(scanner.nextLine());

                        // Using reservationComponent
                        Reservation finished = reservationComponent.release(reservationId);

                        System.out.println("Released!");
                        System.out.println(finished);
                    }

                    case 4 -> {

                        System.out.print("Enter plate number: ");
                        String plate = scanner.nextLine();

                        // Using reportingComponent
                        ListResult<Reservation> result = reportingComponent.byPlate(plate);
                        if (result.getTotalCount() == 0) {
                            System.out.println("No reservations found.");
                            break;
                        }

                        result.getItems().forEach(System.out::println);

                        System.out.print("Enter reservation id: ");
                        int id = Integer.parseInt(scanner.nextLine());

                        Reservation reservation = reservationService.getReservationById(id);

                        //Using paymentComponent
                        Invoice invoice = paymentComponent.buildInvoice(reservation, plate);

                        System.out.println(invoice);
                    }

                    case 5 -> {

                        System.out.print("Enter plate number: ");
                        String plate = scanner.nextLine();

                        System.out.print("Enter spot number: ");
                        String spot = scanner.nextLine();

                        List<Tariff> tariffs =
                                tariffRepository.findAllTariffs();
                        tariffs.forEach(System.out::println);

                        System.out.print("Enter tariff id (Enter = default): ");
                        String input = scanner.nextLine();

                        int tariffId = input.isBlank()
                                ? TariffConfig.getInstance().getDefaultTariffId()
                                : Integer.parseInt(input);

                        //Using reservationComponent
                        Reservation r = reservationComponent.reserveByNumber(plate, spot, tariffId);
                        System.out.println("Reserved:");
                        System.out.println(r);
                    }
                    default -> System.out.println("Unknown option");
                }

            } catch (InvalidVehiclePlateException e) {
                System.out.println(e.getMessage());

            } catch (NoFreeSpotsException e) {
                System.out.println(e.getMessage());

            } catch (ReservationAlreadyActiveOrExpiredException e) {
                System.out.println(e.getMessage());

            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
