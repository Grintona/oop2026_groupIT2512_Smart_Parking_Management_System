package edu.aitu.oop3.entities;

public class Invoice {
    private final int reservationId;
    private final long hours;
    private final int cost;
    private final String plate;
    private final String tariffName;

    public Invoice(int reservationId, long hours, int cost, String plate, String tariffName) {
        this.reservationId = reservationId;
        this.hours = hours;
        this.cost = cost;
        this.plate = plate;
        this.tariffName = tariffName;
    }

    public int getReservationId() {
        return reservationId;
    }

    public long getHours() {
        return hours;
    }

    public int getCost() {
        return cost;
    }

    public String getPlate() {
        return plate;
    }

    public String getTariffName() {
        return tariffName;
    }

    @Override
    public String toString() {
        return "========== INVOICE ==========\n"
                + "Reservation ID : " + reservationId + "\n"
                + "Plate          : " + plate + "\n"
                + "Tariff         : " + tariffName + "\n"
                + "Hours          : " + hours + "\n"
                + "Total cost     : " + cost + "\n"
                + "=============================";
    }
}
