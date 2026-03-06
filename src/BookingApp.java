/**
 * BookingApp - Use Case 3: Centralized Room Inventory
 *
 * This demonstrates centralized inventory management using HashMap
 * to maintain room availability in one place.
 *
 * Author: SarthakSrm653
 * Version: 1.2
 */

import java.util.HashMap;
import java.util.Map;

abstract class Room {
    private int numberOfBeds;
    private double size;
    private double price;

    public Room(int numberOfBeds, double size, double price) {
        this.numberOfBeds = numberOfBeds;
        this.size = size;
        this.price = price;
    }

    public int getNumberOfBeds() { return numberOfBeds; }
    public double getSize() { return size; }
    public double getPrice() { return price; }
    public abstract String getRoomType();

    @Override
    public String toString() {
        return String.format("%s: Beds=%d, Size=%.2fm², Price=%.2f",
                getRoomType(), numberOfBeds, size, price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() { super(1, 15.0, 50.0); }
    @Override
    public String getRoomType() { return "Single Room"; }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super(2, 25.0, 90.0); }
    @Override
    public String getRoomType() { return "Double Room"; }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super(4, 45.0, 180.0); }
    @Override
    public String getRoomType() { return "Suite Room"; }
}

// Centralized Inventory class
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Register room type with initial availability
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Get availability for a room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    // Display current inventory
    public void displayInventory() {
        System.out.println("===== Current Room Inventory =====");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type) + " available");
        }
        System.out.println("=================================");
    }
}

public class BookingApp {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App - Use Case 3\n");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Centralized inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType(single.getRoomType(), 5);
        inventory.addRoomType(doubleRoom.getRoomType(), 3);
        inventory.addRoomType(suite.getRoomType(), 2);

        // Display room details
        Room[] rooms = {single, doubleRoom, suite};
        for (Room room : rooms) {
            System.out.println(room.toString());
        }

        System.out.println();

        // Display inventory
        inventory.displayInventory();

        System.out.println("Application terminated.");
    }
}