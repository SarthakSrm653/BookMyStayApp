import java.util.Stack;

/**
 * Use Case 10: Booking Cancellation & Inventory Rollback
 * Goal: Enable safe cancellation of bookings and restore inventory using a Stack (LIFO).
 */
public class Book_My_APP_01 {

    // Stack to track allocated Room IDs for rollback
    private final Stack<String> roomAllocationTracker = new Stack<>();
    private int availableInventory = 10;

    /**
     * Simulates booking a room and pushing the ID onto the stack
     */
    public void bookRoom(String roomId) {
        if (availableInventory > 0) {
            roomAllocationTracker.push(roomId);
            availableInventory--;
            System.out.println("Booked: " + roomId + " | Inventory: " + availableInventory);
        } else {
            System.out.println("Booking failed: No inventory.");
        }
    }

    /**
     * Performs a rollback (Cancellation)
     * Reverses the last operation performed
     */
    public void cancelLastBooking() {
        System.out.println("\nInitiating Cancellation...");

        if (!roomAllocationTracker.isEmpty()) {
            // LIFO: Pop the most recent room ID
            String rolledBackRoom = roomAllocationTracker.pop();

            // Increment inventory (State restoration)
            availableInventory++;

            System.out.println("Rollback Success: Room " + rolledBackRoom + " is now available.");
            System.out.println("Updated Inventory: " + availableInventory);
        } else {
            System.out.println("Cancellation Failed: No active bookings to roll back.");
        }
    }

    public static void main(String[] args) {
        Book_My_APP_01 app = new Book_My_APP_01();

        // 1. Perform some bookings
        app.bookRoom("Room_101");
        app.bookRoom("Room_102");
        app.bookRoom("Room_103");

        // 2. Guest decides to cancel the most recent booking
        app.cancelLastBooking();

        // 3. Guest cancels another one
        app.cancelLastBooking();

        // 4. Final state check
        System.out.println("\nFinal Inventory: " + app.availableInventory);
    }
}