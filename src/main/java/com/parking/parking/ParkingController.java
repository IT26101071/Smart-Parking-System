package com.parking.parking;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ParkingController {

    private final ParkingManager parkingManager = new ParkingManager();

    public ParkingController() {
        FileHandler.loadTickets(parkingManager);
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalSlots", parkingManager.getAllSlots().length);
        model.addAttribute("freeSlots", parkingManager.getFreeSlotCount());
        model.addAttribute("allSlots", parkingManager.getAllSlots());
        return "home";
    }

    @GetMapping("/park")
    public String parkPage() {
        return "park";
    }

    @PostMapping("/park")
    public String park(@RequestParam String plateNumber,
                       @RequestParam String ownerName,
                       @RequestParam String type,
                       Model model) {

        if (plateNumber.trim().isEmpty() || ownerName.trim().isEmpty()) {
            model.addAttribute("message", "Plate number and owner name are required!");
            return "park";
        }
        for (Ticket t : parkingManager.getActiveTickets()) {
            if (t.getVehicle().getPlateNumber().equalsIgnoreCase(plateNumber.trim())) {
                model.addAttribute("message", "This vehicle is already parked!");
                return "park";
            }
        }

        Vehicle vehicle;
        if (type.equals("Car")) {
            vehicle = new Car(plateNumber.trim(), ownerName.trim());
        } else {
            vehicle = new Motorbike(plateNumber.trim(), ownerName.trim());
        }

        Ticket ticket = parkingManager.parkVehicle(vehicle);

        if (ticket == null) {
            model.addAttribute("message", "Parking full!");
        } else {
            FileHandler.saveTickets(parkingManager.getActiveTickets());
            model.addAttribute("message", "Parked! Ticket " + ticket.getTicketId()
                    + ", Slot " + ticket.getSlot().getSlotId());
        }
        return "park";
    }
    @GetMapping("/exit")
    public String exitPage() {
        return "exit";
    }

    @PostMapping("/exit")
    public String exit(@RequestParam String ticketId, Model model) {
        double fee = parkingManager.exitVehicle(ticketId.trim());

        if (fee < 0) {
            model.addAttribute("message", "Ticket not found!");
        } else {
            FileHandler.saveTickets(parkingManager.getActiveTickets());
            model.addAttribute("message", "Exit successful. Fee: Rs. " + fee);
        }
        return "exit";
    }
    @GetMapping("/tickets")
    public String tickets(@RequestParam(required = false) String search, Model model) {
        if (search != null && !search.trim().isEmpty()) {
            Ticket found = parkingManager.findTicket(search.trim());
            java.util.ArrayList<Ticket> result = new java.util.ArrayList<>();
            if (found != null) {
                result.add(found);
            }
            model.addAttribute("tickets", result);
            model.addAttribute("search", search.trim());
        } else {
            model.addAttribute("tickets", parkingManager.getActiveTickets());
        }
        return "tickets";
    }
    @GetMapping("/slots")
    public String slots(Model model) {
        model.addAttribute("slots", parkingManager.getSortedSlots());
        return "slots";
    }
}