package VehicleParkingSystem;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FileHandler {

    private static final String FILE_PATH = "data/tickets.txt";

    public static void saveTickets(ArrayList<Ticket> tickets) {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (Ticket t : tickets) {
                String type = (t.getVehicle() instanceof Car) ? "Car" : "Motorbike";
                writer.println(t.getTicketId() + ","
                        + t.getVehicle().getPlateNumber() + ","
                        + t.getVehicle().getOwnerName() + ","
                        + type + ","
                        + t.getSlot().getSlotId() + ","
                        + t.getEntryTime());
            }
        } catch (IOException e) {
            System.out.println("Error saving tickets: " + e.getMessage());
        }
    }

    public static void loadTickets(ParkingManager manager) {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] p = line.split(",");
                String ticketId = p[0];
                String plate = p[1];
                String owner = p[2];
                String type = p[3];
                String slotId = p[4];
                LocalDateTime entry = LocalDateTime.parse(p[5]);

                Vehicle vehicle = type.equals("Car")
                        ? new Car(plate, owner)
                        : new Motorbike(plate, owner);

                ParkingSlot slot = findSlot(manager, slotId);
                if (slot != null) {
                    manager.restoreTicket(new Ticket(ticketId, vehicle, slot, entry));
                }
            }
            manager.rebuildFreeSlots();
        } catch (IOException e) {
            System.out.println("Error loading tickets: " + e.getMessage());
        }
    }

    private static ParkingSlot findSlot(ParkingManager manager, String slotId) {
        for (ParkingSlot s : manager.getAllSlots()) {
            if (s.getSlotId().equals(slotId)) {
                return s;
            }
        }
        return null;
    }
}