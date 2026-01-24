package edu.aitu.oop3.services;

import edu.aitu.oop3.entities.Reservation;
import edu.aitu.oop3.entities.Tariff;
import edu.aitu.oop3.repositories.TariffRepository;

public class PricingService {

    private final TariffRepository tariffRepository;

    public PricingService(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    public int calculatePrice(Reservation reservation, int hours) {
        if (hours <= 0) {
            throw new IllegalArgumentException("Hours must be positive");
        }

        Tariff tariff = tariffRepository.findById(reservation.getTariffId());
        if (tariff == null) {
            throw new RuntimeException("Tariff not found: " + reservation.getTariffId());
        }

        return tariff.getPricePerHour() * hours;
    }
}
