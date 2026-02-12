package edu.aitu.oop3.services;

import edu.aitu.oop3.builder.InvoiceBuilder;
import edu.aitu.oop3.config.TariffConfig;
import edu.aitu.oop3.entities.Invoice;
import edu.aitu.oop3.entities.Reservation;
import edu.aitu.oop3.entities.Tariff;
import edu.aitu.oop3.repositories.TariffRepository;

public class PricingService {

    private final TariffRepository tariffRepository;

    public PricingService(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    public int calculateCost(Reservation reservation) {
        if (reservation.getEndTime() == null) {
            throw new IllegalStateException("Reservation is not finished yet");
        }
        long diffMillis = reservation.getEndTime().getTime()
                - reservation.getStartTime().getTime();
        if (diffMillis < 0) {
            throw new IllegalStateException("End time is before start time");
        }
        long hours = diffMillis / (1000L * 60 * 60);
        int tariffId = reservation.getTariffId();
        if (tariffId == 0) {
            tariffId = TariffConfig.getInstance().getDefaultTariffId();
        }
        Tariff tariff = tariffRepository.findById(tariffId);
        if (tariff == null) {
            throw new RuntimeException("Tariff not found: " + tariffId);
        }
        int cost = (int) hours * tariff.getPricePerHour();
        return cost;

    }
    public Invoice buildInvoice(Reservation reservation, String plate) {

        int cost = calculateCost(reservation);

        long diffMillis =
                reservation.getEndTime().getTime()
                        - reservation.getStartTime().getTime();

        long hours = Math.max(1, diffMillis / (1000L * 60 * 60));

        Tariff tariff =
                tariffRepository.findById(reservation.getTariffId());

        return new InvoiceBuilder()
                .reservationId(reservation.getId())
                .plate(plate)
                .tariffName(tariff.getName())
                .hours(hours)
                .cost(cost)
                .build();
    }

}