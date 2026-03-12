import java.util.HashMap;
import java.util.Map;

/**
 * =====================================================
 * ABSTRACT CLASS – Room
 * =====================================================
 *
 * Represents a generic hotel room.
 *
 * @author ManasaPraveen-03
 * @version 3.1
 */

abstract class Room {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}

/**
 * =====================================================
 * CLASS – SingleRoom
 * =====================================================
 *
 * Represents a single room in the hotel.
 *
 * @author ManasaPraveen-03
 * @version 3.1
 */

class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

/**
 * =====================================================
 * CLASS – DoubleRoom
 * =====================================================
 *
 * Represents a double room in the hotel.
 *
 * @author ManasaPraveen-03
 * @version 3.1
 */

class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

/**
 * =====================================================
 * CLASS – SuiteRoom
 * =====================================================
 *
 * Represents a suite room in the hotel.
 *
 * @author ManasaPraveen-03
 * @version 3.1
 */

class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}


/**
 * =====================================================
 * CLASS – RoomInventory
 * =====================================================
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * Description:
 * This class acts as the single source of truth
 * for room availability in the hotel.
 *
 * Room pricing and characteristics are obtained
 * from Room objects, not duplicated here.
 *
 * @author ManasaPraveen-03
 * @version 3.1
 */

class RoomInventory {

    /**
     * Stores available room count for each room type.
     * Key   -> Room type name
     * Value -> Available room count
     */
    private Map<String, Integer> roomAvailability;

    /**
     * Constructor initializes the inventory
     * with default availability values.
     */
    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    /**
     * Initializes room availability data.
     */
    private void initializeInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    /**
     * Returns the availability map.
     */
    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    /**
     * Updates availability for a specific room type.
     */
    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}


/**
 * =====================================================
 * MAIN CLASS – BookMyStayApp
 * =====================================================
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * Description:
 * Demonstrates how room availability is managed
 * using a centralized inventory.
 *
 * @author ManasaPraveen-03
 * @version 3.1
 */

public class BookMyStayApp {

    /**
     * Application entry point.
     */
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        SingleRoom singleRoom = new SingleRoom();
        DoubleRoom doubleRoom = new DoubleRoom();
        SuiteRoom suiteRoom = new SuiteRoom();

        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("Hotel Room Inventory Status\n");

        System.out.println("Single Room:");
        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + availability.get("Single"));

        System.out.println();

        System.out.println("Double Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + availability.get("Double"));

        System.out.println();

        System.out.println("Suite Room:");
        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + availability.get("Suite"));
    }
}