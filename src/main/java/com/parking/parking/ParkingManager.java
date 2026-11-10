package com.parking.parking;

import java.util.ArrayList;

    public class ParkingManager {

        private static final int TOTAL_SLOTS = 10;

        private final ParkingSlot[] allSlots = new ParkingSlot[TOTAL_SLOTS];
        private final SlotStack freeSlots = new SlotStack(TOTAL_SLOTS);
        private final ArrayList<Ticket> activeTickets = new ArrayList<>();
        private int ticketCounter = 0;

        public ParkingManager() {

            for (int i = 0; i < TOTAL_SLOTS; i++) {
                allSlots[i] = new ParkingSlot("S" + (i + 1));
            }

            for (int i = TOTAL_SLOTS - 1; i >= 0; i--) {
                freeSlots.push(allSlots[i]);
            }
        }

        public Ticket parkVehicle(Vehicle vehicle) {
            if (freeSlots.isEmpty()) {
                return null;
            }
            ParkingSlot slot = freeSlots.pop();
            slot.setAvailable(false);

            ticketCounter++;
            String ticketId = "T" + ticketCounter;

            Ticket ticket = new Ticket(ticketId, vehicle, slot);
            activeTickets.add(ticket);
            return ticket;
        }

        public Ticket findTicket(String ticketId) {
            for (Ticket t : activeTickets) {
                if (t.getTicketId().equalsIgnoreCase(ticketId)) {
                    return t;
                }
            }
            return null;
        }

        public double exitVehicle(String ticketId) {
            Ticket ticket = findTicket(ticketId);
            if (ticket == null) {
                return -1;
            }
            double fee = ticket.getVehicle().calculateFee(ticket.getHours());

            ParkingSlot slot = ticket.getSlot();
            slot.setAvailable(true);
            freeSlots.push(slot);
            activeTickets.remove(ticket);
            return fee;
        }

        public ArrayList<Ticket> getActiveTickets() {
            return activeTickets;
        }

        public ParkingSlot[] getSortedSlots() {
            ParkingSlot[] copy = allSlots.clone();
            SlotSorter.quickSort(copy, 0, copy.length - 1);
            return copy;
        }

        public int getFreeSlotCount() {
            return TOTAL_SLOTS - activeTickets.size();
        }
        public ParkingSlot[] getAllSlots() {
            return allSlots;
        }

        public void restoreTicket(Ticket ticket) {
            activeTickets.add(ticket);
            ticket.getSlot().setAvailable(false);
            int num = Integer.parseInt(ticket.getTicketId().substring(1));
            if (num > ticketCounter) {
                ticketCounter = num;
            }
        }

        public void rebuildFreeSlots() {
            while (!freeSlots.isEmpty()) {
                freeSlots.pop();
            }
            for (int i = TOTAL_SLOTS - 1; i >= 0; i--) {
                if (allSlots[i].isAvailable()) {
                    freeSlots.push(allSlots[i]);
                }
            }
        }
    }
