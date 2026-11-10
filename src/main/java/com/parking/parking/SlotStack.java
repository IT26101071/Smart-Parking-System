package com.parking.parking;

public class SlotStack {
    private final ParkingSlot[] slots;
    private int top;

    public SlotStack(int capacity){
        slots = new ParkingSlot[capacity];
        top = -1;
    }

    public boolean isFull(){
        return slots.length == top+1;
    }
    public boolean isEmpty(){
        return top<0;
    }
    public void push(ParkingSlot slot){
        if (isFull()){
            System.out.println("Parking slots are full!");
        }
        else{
            slots[++top] = slot;

        }
    }

    public ParkingSlot pop(){
        if(isEmpty()){
            return null;
        }
        return slots[top--];

    }
    public ParkingSlot peek(){
        if(isEmpty()){
            return null;
        }
        return slots[top];
    }

}