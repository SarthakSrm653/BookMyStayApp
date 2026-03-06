/**
 * BookingApp - Use Case 5: Booking Request Queue (FIFO)
 *
 * Demonstrates collecting booking requests using a Queue
 * to ensure First-Come-First-Served (FIFO) processing.
 *
 * Author: SarthakSrm653
 * Version: 1.4
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;

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

// Centralized inventory class
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // For Use Case 5, we do not update availability yet
    public void displayAvailableRooms(Room[] rooms) {
        System.out.println("===== Available Rooms =====");
        for (Room room : rooms) {
            int available = getAvailability(room.getRoomType());
            if (available > 0) {
                System.out.println(room.toString() + " | Available: " + available);
            }
        }
        System.out.println("===========================");
    }
}

// Booking request class
class BookingRequest {
    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return String.format("Guest: %s | Requested Room: %s", guestName, roomType);
    }
}

public class BookingApp {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App - Use Case 5\n");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();
        Room[] rooms = {single, doubleRoom, suite};

        // Centralized inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType(single.getRoomType(), 5);
        inventory.addRoomType(doubleRoom.getRoomType(), 3);
        inventory.addRoomType(suite.getRoomType(), 2);

        // Display available rooms
        inventory.displayAvailableRooms(rooms);

        // Booking request queue (FIFO)
        Queue<BookingRequest> bookingQueue = new LinkedList<>();

        // Guests submit booking requests
        bookingQueue.add(new BookingRequest("Alice", "Single Room"));
        bookingQueue.add(new BookingRequest("Bob", "Suite Room"));
        bookingQueue.add(new BookingRequest("Charlie", "Double Room"));

        // Show queued booking requests
        System.out.println("\n===== Booking Requests Queue =====");
        for (BookingRequest request : bookingQueue) {
            System.out.println(request.toString());
        }
        System.out.println("=================================");

        System.out.println("\nApplication terminated (requests collected, no allocation yet).");
    }
}