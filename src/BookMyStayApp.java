import java.util.*;

/**
 * =====================================================
 * CLASS – Reservation
 * =====================================================
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Represents a booking request made by a guest.
 *
 * @author ManasaPraveen-03
 * @version 6.0
 */

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}


/**
 * =====================================================
 * CLASS – BookingRequestQueue
 * =====================================================
 *
 * Stores booking requests using FIFO queue.
 *
 * @author ManasaPraveen-03
 * @version 6.0
 */

class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}


/**
 * =====================================================
 * CLASS – RoomInventory
 * =====================================================
 *
 * Stores available room counts.
 *
 * @author ManasaPraveen-03
 * @version 6.0
 */

class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability.put("Single", 2);
        roomAvailability.put("Double", 1);
        roomAvailability.put("Suite", 1);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}


/**
 * =====================================================
 * CLASS – RoomAllocationService
 * =====================================================
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Responsible for confirming booking requests
 * and assigning rooms.
 *
 * It ensures:
 * - Each room ID is unique
 * - Inventory is updated immediately
 * - No room is double-booked
 *
 * @author ManasaPraveen-03
 * @version 6.0
 */

class RoomAllocationService {

    /** Stores all allocated room IDs */
    private Set<String> allocatedRoomIds;

    /** Stores assigned room IDs by room type */
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    /**
     * Confirms booking request and allocates a room.
     */
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String roomType = reservation.getRoomType();

        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (availability.get(roomType) <= 0) {
            System.out.println("No rooms available for type: " + roomType);
            return;
        }

        String roomId = generateRoomId(roomType);

        allocatedRoomIds.add(roomId);

        assignedRoomsByType.putIfAbsent(roomType, new HashSet<>());
        assignedRoomsByType.get(roomType).add(roomId);

        inventory.updateAvailability(roomType, availability.get(roomType) - 1);

        System.out.println(
                "Booking confirmed for Guest: "
                        + reservation.getGuestName()
                        + ", Room ID: "
                        + roomId);
    }

    /**
     * Generates unique room ID
     */
    private String generateRoomId(String roomType) {

        assignedRoomsByType.putIfAbsent(roomType, new HashSet<>());

        int id = assignedRoomsByType.get(roomType).size() + 1;

        return roomType + "-" + id;
    }
}


/**
 * =====================================================
 * MAIN CLASS – BookMyStayApp
 * =====================================================
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Demonstrates booking confirmation and
 * safe room allocation using FIFO requests.
 *
 * @author ManasaPraveen-03
 * @version 6.0
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing\n");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        // Create booking requests
        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));
        bookingQueue.addRequest(new Reservation("Vannathi", "Suite"));

        // Process requests FIFO
        while (bookingQueue.hasPendingRequests()) {

            Reservation reservation = bookingQueue.getNextRequest();

            allocationService.allocateRoom(reservation, inventory);
        }
    }
}