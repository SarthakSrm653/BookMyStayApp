import java.util.ArrayList;
import java.util.List;

/**
 * Use Case 8: Booking History & Reporting
 * Goal: Maintain a chronological record of all confirmed bookings for administrative review.
 */
public class Book_My_APP_01 {

    // Internal class to represent a Booking record
    static class Reservation {
        String id;
        String guestName;
        String roomType;

        Reservation(String id, String guestName, String roomType) {
            this.id = id;
            this.guestName = guestName;
            this.roomType = roomType;
        }

        @Override
        public String toString() {
            return String.format("ID: %s | Guest: %s | Room: %s", id, guestName, roomType);
        }
    }

    // List to store history (Preserves insertion order)
    private final List<Reservation> bookingHistory = new ArrayList<>();

    // Method to simulate confirming a booking and adding it to history
    public void confirmAndRecord(String id, String name, String room) {
        Reservation newBooking = new Reservation(id, name, room);

        // Logic: Add to historical record
        bookingHistory.add(newBooking);

        System.out.println("System Message: Booking " + id + " confirmed and added to history.");
    }

    // Method to generate a summary report
    public void generateHistoryReport() {
        System.out.println("\n--- ADMINISTRATIVE BOOKING REPORT ---");
        if (bookingHistory.isEmpty()) {
            System.out.println("No records found.");
        } else {
            for (Reservation res : bookingHistory) {
                System.out.println("[RECORD] " + res);
            }
        }
        System.out.println("Total Transactions: " + bookingHistory.size());
        System.out.println("-------------------------------------\n");
    }

    public static void main(String[] args) {
        Book_My_APP_01 app = new Book_My_APP_01();

        // Simulating sequence of bookings
        app.confirmAndRecord("B-001", "Alice Johnson", "Deluxe");
        app.confirmAndRecord("B-002", "Bob Smith", "Standard");
        app.confirmAndRecord("B-003", "Charlie Brown", "Suite");

        // Admin requests the report
        app.generateHistoryReport();
    }
}