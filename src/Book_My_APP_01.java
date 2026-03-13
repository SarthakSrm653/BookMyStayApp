import java.util.*;

public class Book_My_APP_01 {

    public static void main(String[] args) {

        // Guest reservation
        Reservation reservation = new Reservation("R101", "John", "Deluxe");

        // Add-On Service Manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Guest selects services
        manager.addService(reservation.getReservationId(), new AddOnService("Breakfast", 500));
        manager.addService(reservation.getReservationId(), new AddOnService("Spa Access", 1200));
        manager.addService(reservation.getReservationId(), new AddOnService("Airport Pickup", 800));

        // Display selected services
        manager.displayServices(reservation.getReservationId());
    }
}

/* ---------- Reservation Class ---------- */

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/* ---------- Add-On Service ---------- */

class AddOnService {

    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return serviceName + " : ₹" + price;
    }
}

/* ---------- Add-On Service Manager ---------- */

class AddOnServiceManager {

    // Map<ReservationID, List of Services>
    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

    public void addService(String reservationId, AddOnService service) {

        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println(service.getServiceName() + " added to reservation " + reservationId);
    }

    public void displayServices(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        double total = 0;

        System.out.println("\nServices for Reservation " + reservationId);

        for (AddOnService service : services) {
            System.out.println(service);
            total += service.getPrice();
        }

        System.out.println("Total Add-On Cost: ₹" + total);
    }
}