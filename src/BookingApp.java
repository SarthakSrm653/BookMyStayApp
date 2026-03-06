/**
 * BookingApp - Use Case 5: Booking Request Queue with User Input (FIFO)
 *
 * Collects booking requests from users via console input,
 * stores them in a FIFO queue, and displays queued requests.
 *
 * Author: SarthakSrm653
 * Version: 1.4.1
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

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

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
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Book My Stay App - Use Case 5 with User Input\n");

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

        // Booking request queue
        Queue<BookingRequest> bookingQueue = new LinkedList<>();

        System.out.println("\nEnter booking requests (type 'done' to finish):");

        while (true) {
            System.out.print("Guest Name: ");
            String guestName = scanner.nextLine();
            if (guestName.equalsIgnoreCase("done")) break;

            System.out.print("Room Type (Single Room / Double Room / Suite Room): ");
            String roomType = scanner.nextLine();
            if (roomType.equalsIgnoreCase("done")) break;

            // Add request to queue
            bookingQueue.add(new BookingRequest(guestName, roomType));
            System.out.println("Booking request added!\n");
        }

        // Display queued requests
        System.out.println("\n===== Booking Requests Queue =====");
        for (BookingRequest request : bookingQueue) {
            System.out.println(request.toString());
        }
        System.out.println("=================================");

        System.out.println("\nApplication terminated (requests collected, no allocation yet).");
        scanner.close();
    }
}