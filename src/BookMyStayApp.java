import java.io.*;
import java.util.*;

/**
 * =====================================================
 * CLASS – RoomInventory
 * =====================================================
 * Stores current room inventory.
 *
 * @author ManasaPraveen-03
 * @version 12.0
 */

class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String type, int count) {
        roomAvailability.put(type, count);
    }
}


/**
 * =====================================================
 * CLASS – FilePersistenceService
 * =====================================================
 *
 * Use Case 12: Data Persistence & System Recovery
 *
 * Responsible for saving and restoring
 * system inventory state to a text file.
 *
 * @author ManasaPraveen-03
 * @version 12.0
 */

class FilePersistenceService {

    /**
     * Saves inventory state to file.
     *
     * Format:
     * roomType=roomCount
     */
    public void saveInventory(RoomInventory inventory, String filePath) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            for (Map.Entry<String, Integer> entry :
                    inventory.getRoomAvailability().entrySet()) {

                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }

            System.out.println("Inventory saved successfully.");

        } catch (IOException e) {

            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    /**
     * Loads inventory state from file.
     */
    public void loadInventory(RoomInventory inventory, String filePath) {

        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("=");

                if (parts.length == 2) {

                    String roomType = parts[0];
                    int count = Integer.parseInt(parts[1]);

                    inventory.updateAvailability(roomType, count);
                }
            }

        } catch (IOException e) {

            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }
}


/**
 * =====================================================
 * MAIN CLASS – BookMyStayApp
 * =====================================================
 *
 * Use Case 12: Data Persistence & System Recovery
 *
 * Demonstrates restoring system state
 * from a saved file before operations begin.
 *
 * @author ManasaPraveen-03
 * @version 12.0
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("System Recovery");

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistence = new FilePersistenceService();

        String filePath = "inventory.txt";

        // load inventory
        persistence.loadInventory(inventory, filePath);

        System.out.println("\nCurrent Inventory:");

        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("Single: " + availability.get("Single"));
        System.out.println("Double: " + availability.get("Double"));
        System.out.println("Suite: " + availability.get("Suite"));

        // save inventory
        persistence.saveInventory(inventory, filePath);
    }
}