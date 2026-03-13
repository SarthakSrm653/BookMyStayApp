import java.io.*;
import java.util.*;

/**
 * Use Case 12: Data Persistence & System Recovery
 * Goal: Save system state to a file so it survives application restarts.
 */
public class Book_My_APP_01 implements Serializable {
  // SerialVersionUID ensures class compatibility during loading
  private static final long serialVersionUID = 1L;

  private int inventory = 10;
  private List<String> history = new ArrayList<>();

  public void makeBooking(String details) {
    inventory--;
    history.add(details);
    System.out.println("Booking Saved: " + details);
  }

  // --- Persistence Logic ---

  public void saveState(String filename) {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
      out.writeObject(this);
      System.out.println("System state saved to " + filename);
    } catch (IOException e) {
      System.err.println("Save Error: " + e.getMessage());
    }
  }

  public static Book_My_APP_01 loadState(String filename) {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
      return (Book_My_APP_01) in.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.out.println("No saved state found. Starting a new session.");
      return new Book_My_APP_01();
    }
  }

  public static void main(String[] args) {
    String dataFile = "hotel_data.ser";

    // 1. Try to recover previous state
    Book_My_APP_01 app = loadState(dataFile);

    // 2. Perform actions
    System.out.println("Current Inventory: " + app.inventory);
    app.makeBooking("Guest_" + System.currentTimeMillis());

    // 3. Save state before closing
    app.saveState(dataFile);

    System.out.println("Application closing. Run again to see recovered state.");
  }
}