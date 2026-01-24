package edu.aitu.oop3.services;

import edu.aitu.oop3.entities.Reservation;
import edu.aitu.oop3.entities.Tariff;
import edu.aitu.oop3.repositories.TariffRepository;

public class PricingService {

    private final TariffRepository tariffRepository;

    public PricingService(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    public int calculatePrice(Reservation reservation) {

        if (reservation.getEndTime() == null) {
            throw new IllegalStateException("Reservation is not finished yet");
        }

        long diffMillis = reservation.getEndTime().getTime()
                - reservation.getStartTime().getTime();

        long hours = diffMillis / (1000 * 60 * 60);

        Tariff tariff = tariffRepository.findById(reservation.getTariffId());
        if (tariff == null) {
            throw new RuntimeException(
                    "Tariff not found: " + reservation.getTariffId()
            );
        }

        return (int) hours * tariff.getPricePerHour();
    }
}
