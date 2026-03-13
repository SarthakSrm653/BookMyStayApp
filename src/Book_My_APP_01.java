import java.util.*;

public class Book_My_APP_01 {
    static class Service {
        String name;
        double price;
        Service(String n, double p) { name = n; price = p; }
        public String toString() { return name + " ($" + price + ")"; }
    }

    private Map<String, List<Service>> addOnMap = new HashMap<>();

    public void addService(String resId, Service s) {
        addOnMap.computeIfAbsent(resId, k -> new ArrayList<>()).add(s);
        System.out.println("Added " + s + " to Reservation: " + resId);
    }

    public static void main(String[] args) {
        Book_My_APP_01 app = new Book_My_APP_01();
        app.addService("RES_001", new Service("WiFi", 10.0));
        app.addService("RES_001", new Service("Late Checkout", 20.0));
    }
}