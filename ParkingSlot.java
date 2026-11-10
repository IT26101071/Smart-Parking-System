package com.parking.parking;

public class ParkingSlot {

    private final String slotId;
    private boolean isAvailable;

    public ParkingSlot(String slotId){
        this.slotId = slotId;
        isAvailable = true;
    }
    public String getSlotId(){
        return slotId;
    }
    public boolean isAvailable(){
        return isAvailable;
    }
    public void setAvailable(boolean available){
        isAvailable = available;
    }
}
