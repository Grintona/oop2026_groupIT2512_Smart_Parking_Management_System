package edu.aitu.oop3.components;

import edu.aitu.oop3.entities.Invoice;
import edu.aitu.oop3.entities.Reservation;
import edu.aitu.oop3.services.PricingService;

public class PaymentComponent {

    private final PricingService pricingService;

    public PaymentComponent(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    public Invoice buildInvoice(Reservation reservation, String plate) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation is null");
        }
        return pricingService.buildInvoice(reservation, plate);
    }
}

