package edu.aitu.oop3.components;

import edu.aitu.oop3.repositories.ParkingSpotRepository;

public class MonitoringComponent {

    private final ParkingSpotRepository parkingSpotRepository;

    public MonitoringComponent(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    public int countFreeSpots() {
        return parkingSpotRepository.findFreeSpots().size();
    }
}
