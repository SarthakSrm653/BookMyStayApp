import java.util.ArrayList;
import java.util.List;

/**
 * Use Case 9: Error Handling & Validation
 * Goal: Ensure system stability by validating inputs and handling invalid states via Custom Exceptions.
 */

// Custom Exception for Booking-related errors
class BookingException extends Exception {
    public BookingException(String message) {
        super(message);
    }
}

public class Book_My_APP_01 {

    private int inventoryCount = 5; // Initial available rooms
    private List<String> confirmedBookings = new ArrayList<>();

    /**
     * Core logic with Validation
     * @param guestName Name of the guest
     * @param roomsRequested Number of rooms to book
     * @throws BookingException if validation fails
     */
    public void processBooking(String guestName, int roomsRequested) throws BookingException {
        System.out.println("Processing request for " + guestName + " (" + roomsRequested + " rooms)...");

        // 1. Input Validation
        if (roomsRequested <= 0) {
            throw new BookingException("Validation Failed: Requested rooms must be at least 1.");
        }

        // 2. System State Validation (Inventory Check)
        if (roomsRequested > inventoryCount) {
            throw new BookingException("Validation Failed: Not enough rooms available. (Current Inventory: " + inventoryCount + ")");
        }

        // 3. Successful State Update
        inventoryCount -= roomsRequested;
        confirmedBookings.add(guestName + " booked " + roomsRequested + " rooms.");
        System.out.println("Success! Booking confirmed for " + guestName);
    }

    public static void main(String[] args) {
        Book_My_APP_01 app = new Book_My_APP_01();

        // Testing different scenarios
        String[] guests = {"Alice", "Bob", "Charlie"};
        int[] requests = {2, 6, -1}; // Valid, Too many, Invalid input

        for (int i = 0; i < guests.length; i++) {
            try {
                app.processBooking(guests[i], requests[i]);
            } catch (BookingException e) {
                // Graceful Failure Handling
                System.err.println("ALERT: " + e.getMessage());
            } finally {
                System.out.println("System Status: " + app.inventoryCount + " rooms remaining.\n");
            }
        }
    }
}