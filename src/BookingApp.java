/**
 * BookingApp - Use Case 6: Interactive Reservation Confirmation & Room Allocation
 *
 * Collects booking requests from users, processes them FIFO,
 * assigns unique room IDs, updates inventory, and prevents double-booking.
 *
 * Author: SarthakSrm653
 * Version: 1.6
 */

import java.util.*;

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

// Centralized inventory
class RoomInventory {
    private Map<String, Integer> inventory;
    private Map<String, Set<String>> allocatedRooms;

    public RoomInventory() {
        inventory = new HashMap<>();
        allocatedRooms = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
        allocatedRooms.put(roomType, new HashSet<>());
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Allocate a room: generate unique room ID and update inventory
    public String allocateRoom(String roomType) {
        int available = getAvailability(roomType);
        if (available <= 0) return null; // no rooms available

        Set<String> assigned = allocatedRooms.get(roomType);
        String roomId;
        do {
            roomId = roomType.substring(0, 3).toUpperCase() + (assigned.size() + 1);
        } while (assigned.contains(roomId));

        assigned.add(roomId);
        inventory.put(roomType, available - 1);

        return roomId;
    }

    public void displayInventory() {
        System.out.println("===== Current Room Inventory =====");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type) + " available");
        }
        System.out.println("=================================");
    }
}

// Booking request
class BookingRequest {
    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

public class BookingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Book My Stay App - Use Case 6 Interactive\n");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();
        Room[] rooms = {single, doubleRoom, suite};

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType(single.getRoomType(), 5);
        inventory.addRoomType(doubleRoom.getRoomType(), 3);
        inventory.addRoomType(suite.getRoomType(), 2);

        // Display initial inventory
        inventory.displayInventory();

        // Booking request queue (FIFO)
        Queue<BookingRequest> bookingQueue = new LinkedList<>();

        System.out.println("\nEnter booking requests (type 'done' to finish):");
        while (true) {
            System.out.print("Guest Name: ");
            String guestName = scanner.nextLine();
            if (guestName.equalsIgnoreCase("done")) break;

            System.out.print("Room Type (Single Room / Double Room / Suite Room): ");
            String roomType = scanner.nextLine();
            if (roomType.equalsIgnoreCase("done")) break;

            bookingQueue.add(new BookingRequest(guestName, roomType));
            System.out.println("Booking request added!\n");
        }

        System.out.println("\nProcessing booking requests...\n");

        // Process queued requests FIFO
        while (!bookingQueue.isEmpty()) {
            BookingRequest request = bookingQueue.poll();
            String roomId = inventory.allocateRoom(request.getRoomType());

            if (roomId != null) {
                System.out.println("Reservation confirmed for " + request.getGuestName() +
                        " | Room Type: " + request.getRoomType() +
                        " | Room ID: " + roomId);
            } else {
                System.out.println("Sorry " + request.getGuestName() +
                        ", no " + request.getRoomType() + " available.");
            }
        }

        System.out.println("\nFinal inventory state:");
        inventory.displayInventory();

        System.out.println("\nApplication terminated.");
        scanner.close();
    }
}