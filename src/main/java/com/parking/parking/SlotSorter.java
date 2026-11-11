package com.parking.parking;

public class SlotSorter {

    public static void quickSort(int[] arr,int low,int high){
        if(low < high){
            int p = partition(arr, low, high);
            quickSort(arr,low,p - 1); //Left side
            quickSort(arr,p + 1,high); //Right side
        }
    }
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    //parking slot version
    public static void quickSort(ParkingSlot[] arr, int low, int high) {
        if (low < high) {
            int p = partition(arr, low, high);
            quickSort(arr, low, p - 1);   // Left side
            quickSort(arr, p + 1, high);  // Right side
        }
    }

    private static int partition(ParkingSlot[] arr, int low, int high) {
        ParkingSlot pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (compare(arr[j], pivot) <= 0) {
                i++;
                ParkingSlot temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        ParkingSlot temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    private static int compare(ParkingSlot a, ParkingSlot b) {
        if (a.isAvailable() != b.isAvailable()) {
            return a.isAvailable() ? -1 : 1;
        }
        int numA = Integer.parseInt(a.getSlotId().substring(1));
        int numB = Integer.parseInt(b.getSlotId().substring(1));
        return Integer.compare(numA, numB);
    }
}
// hhyc

