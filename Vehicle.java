package com.parking.parking;

public abstract class Vehicle{
        private final String plateNumber;
        private final String ownerName;

        public Vehicle(String plateNumber, String ownerName){
            this.plateNumber = plateNumber;
            this.ownerName = ownerName;
        }
        public String getPlateNumber(){
            return plateNumber;
        }
        public String getOwnerName(){
            return ownerName;
        }

        public abstract double calculateFee(int hours);

}

