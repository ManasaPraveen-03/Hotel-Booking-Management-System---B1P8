import java.util.*;

/**
 * =====================================================
 * CLASS – Reservation
 * =====================================================
 * Represents a booking request.
 *
 * @author ManasaPraveen-03
 * @version 11.0
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
 * FIFO booking queue.
 *
 * @author ManasaPraveen-03
 * @version 11.0
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
 * Shared inventory resource.
 *
 * @author ManasaPraveen-03
 * @version 11.0
 */

class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 3);
        roomAvailability.put("Double", 2);
        roomAvailability.put("Suite", 1);
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
 * CLASS – RoomAllocationService
 * =====================================================
 * Handles room allocation safely.
 *
 * @author ManasaPraveen-03
 * @version 11.0
 */

class RoomAllocationService {

    private Map<String, Integer> roomCounters = new HashMap<>();

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String type = reservation.getRoomType();

        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (availability.get(type) <= 0) {
            System.out.println("No rooms available for " + type);
            return;
        }

        int id = roomCounters.getOrDefault(type, 0) + 1;
        roomCounters.put(type, id);

        String roomId = type + "-" + id;

        inventory.updateAvailability(type, availability.get(type) - 1);

        System.out.println(
                "Booking confirmed for Guest: "
                        + reservation.getGuestName()
                        + ", Room ID: "
                        + roomId);
    }
}


/**
 * =====================================================
 * CLASS – ConcurrentBookingProcessor
 * =====================================================
 *
 * Use Case 11: Concurrent Booking Simulation
 *
 * Represents a booking processor executed by threads.
 *
 * @author ManasaPraveen-03
 * @version 11.0
 */

class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService
    ) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {

        while (true) {

            Reservation reservation;

            synchronized (bookingQueue) {

                if (!bookingQueue.hasPendingRequests()) {
                    break;
                }

                reservation = bookingQueue.getNextRequest();
            }

            synchronized (inventory) {

                allocationService.allocateRoom(reservation, inventory);
            }
        }
    }
}


/**
 * =====================================================
 * MAIN CLASS – BookMyStayApp
 * =====================================================
 *
 * Use Case 11: Concurrent Booking Simulation
 *
 * Simulates multiple users booking rooms simultaneously.
 *
 * @author ManasaPraveen-03
 * @version 11.0
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Vannathi", "Double"));
        bookingQueue.addRequest(new Reservation("Kural", "Suite"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));

        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService
                )
        );

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService
                )
        );

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        System.out.println("\nRemaining Inventory:");

        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("Single: " + availability.get("Single"));
        System.out.println("Double: " + availability.get("Double"));
        System.out.println("Suite: " + availability.get("Suite"));
    }
}