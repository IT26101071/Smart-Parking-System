package com.parking.parking;

public class Motorbike extends Vehicle {

    public Motorbike(String plateNumber,String ownerName){
        super(plateNumber,ownerName);
    }

    @Override
    public double calculateFee(int hours) {
        return hours * 100;
    }
}
