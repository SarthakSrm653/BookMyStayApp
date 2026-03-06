/**
 * BookingApp - Use Case 2: Basic Room Types & Static Availability
 *
 * This demonstrates abstract class usage, inheritance, and polymorphism
 * by defining room types and showing their static availability.
 *
 * @author SarthakSrm653
 * @version 1.1
 */

abstract class Room {
    private int numberOfBeds;
    private double size;  // in square meters
    private double price; // price per night

    public Room(int numberOfBeds, double size, double price) {
        this.numberOfBeds = numberOfBeds;
        this.size = size;
        this.price = price;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public double getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getRoomType();

    @Override
    public String toString() {
        return String.format("%s: Beds=%d, Size=%.2fm², Price=%.2f",
                getRoomType(), numberOfBeds, size, price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 15.0, 50.0);
    }

    @Override
    public String getRoomType() {
        return "Single Room";
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 25.0, 90.0);
    }

    @Override
    public String getRoomType() {
        return "Double Room";
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(4, 45.0, 180.0);
    }

    @Override
    public String getRoomType() {
        return "Suite Room";
    }
}

public class BookingApp {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App - Use Case 2\n");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables (hardcoded)
        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        // Display room details and availability
        Room[] rooms = {single, doubleRoom, suite};

        for (Room room : rooms) {
            int availability = 0;
            if (room instanceof SingleRoom) {
                availability = singleAvailability;
            } else if (room instanceof DoubleRoom) {
                availability = doubleAvailability;
            } else if (room instanceof SuiteRoom) {
                availability = suiteAvailability;
            }

            System.out.println(room.toString());
            System.out.println("Available rooms: " + availability);
            System.out.println();
        }

        System.out.println("Application terminated.");
    }
}