package com.parking.parking;

import java.time.Duration;
import java.time.LocalDateTime;

public class Ticket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSlot slot;
    private final LocalDateTime entryTime;

    public Ticket(String ticketId, Vehicle vehicle, ParkingSlot slot) {
        this.ticketId = ticketId;
        this.slot = slot;
        this.vehicle = vehicle;
        this.entryTime = LocalDateTime.now();
    }
    public Ticket(String ticketId,Vehicle vehicle,ParkingSlot slot,LocalDateTime entryTime ){
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.slot = slot;
        this.entryTime = entryTime;
    }


    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSlot getSlot() {
        return slot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public int getHours() {
        Duration d = Duration.between(entryTime, LocalDateTime.now());
        long minutes = d.toMinutes();

        int hours = (int) ((minutes + 59) / 60);

        if (hours < 1) {
            hours = 1;
        }
        return hours;
    }
}


