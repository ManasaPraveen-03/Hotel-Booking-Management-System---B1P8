import java.util.*;

/**
 * =====================================================
 * CLASS – RoomInventory
 * =====================================================
 *
 * Stores available room counts.
 *
 * @author ManasaPraveen-03
 * @version 10.0
 */

class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 4);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
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
 * CLASS – CancellationService
 * =====================================================
 *
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Description:
 * Handles booking cancellations.
 *
 * Ensures:
 * - Cancelled room IDs are tracked
 * - Inventory is restored correctly
 * - Invalid cancellations are prevented
 *
 * A stack models rollback behavior.
 *
 * @author ManasaPraveen-03
 * @version 10.0
 */

class CancellationService {

    /** Stack storing recently released room IDs */
    private Stack<String> releasedRoomIds;

    /** Maps reservation ID to room type */
    private Map<String, String> reservationRoomTypeMap;

    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    /**
     * Registers a confirmed booking
     */
    public void registerBooking(String reservationId, String roomType) {

        reservationRoomTypeMap.put(reservationId, roomType);
    }

    /**
     * Cancels a confirmed booking
     * and restores inventory
     */
    public void cancelBooking(String reservationId, RoomInventory inventory) {

        if (!reservationRoomTypeMap.containsKey(reservationId)) {
            System.out.println("Invalid reservation ID.");
            return;
        }

        String roomType = reservationRoomTypeMap.get(reservationId);

        Map<String, Integer> availability = inventory.getRoomAvailability();

        inventory.updateAvailability(roomType, availability.get(roomType) + 1);

        releasedRoomIds.push(reservationId);

        reservationRoomTypeMap.remove(reservationId);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
    }

    /**
     * Displays rollback history
     */
    public void showRollbackHistory() {

        System.out.println("\nRollback History (Most Recent First):");

        while (!releasedRoomIds.isEmpty()) {

            System.out.println("Released Reservation ID: " + releasedRoomIds.pop());
        }
    }
}


/**
 * =====================================================
 * MAIN CLASS – BookMyStayApp
 * =====================================================
 *
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Demonstrates cancelling confirmed bookings
 * and restoring inventory safely.
 *
 * @author ManasaPraveen-03
 * @version 10.0
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking Cancellation");

        RoomInventory inventory = new RoomInventory();
        CancellationService cancellationService = new CancellationService();

        String reservationId = "Single-1";

        // simulate confirmed booking
        cancellationService.registerBooking(reservationId, "Single");

        // cancel booking
        cancellationService.cancelBooking(reservationId, inventory);

        // show rollback history
        cancellationService.showRollbackHistory();
    }
}