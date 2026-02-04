package edu.aitu.oop3.builder;

import edu.aitu.oop3.entities.Invoice;

public class InvoiceBuilder {

    private int reservationId;
    private long hours;
    private int cost;
    private String plate;
    private String tariffName;

    public InvoiceBuilder reservationId(int reservationId) {
        this.reservationId = reservationId;
        return this;
    }

    public InvoiceBuilder hours(long hours) {
        this.hours = hours;
        return this;
    }

    public InvoiceBuilder cost(int cost) {
        this.cost = cost;
        return this;
    }

    public InvoiceBuilder plate(String plate) {
        this.plate = plate;
        return this;
    }

    public InvoiceBuilder tariffName(String tariffName) {
        this.tariffName = tariffName;
        return this;
    }

    public Invoice build() {
        return new Invoice(reservationId, hours, cost, plate, tariffName);
    }
}
