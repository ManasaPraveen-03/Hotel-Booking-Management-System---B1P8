import java.util.*;

/**
 * =====================================================
 * CLASS – InvalidBookingException
 * =====================================================
 *
 * Use Case 9: Error Handling & Validation
 *
 * Description:
 * Custom exception representing invalid booking scenarios.
 *
 * @author ManasaPraveen-03
 * @version 9.0
 */

class InvalidBookingException extends Exception {

    /**
     * Creates an exception with
     * a descriptive error message
     */
    public InvalidBookingException(String message) {
        super(message);
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
 * @version 9.0
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
}


/**
 * =====================================================
 * CLASS – BookingRequestQueue
 * =====================================================
 *
 * FIFO queue storing booking requests.
 *
 * @author ManasaPraveen-03
 * @version 9.0
 */

class BookingRequestQueue {

    private Queue<String> requests;

    public BookingRequestQueue() {
        requests = new LinkedList<>();
    }

    public void addRequest(String guestName) {
        requests.offer(guestName);
    }
}


/**
 * =====================================================
 * CLASS – ReservationValidator
 * =====================================================
 *
 * Use Case 9: Error Handling & Validation
 *
 * Description:
 * Validates booking input before processing.
 *
 * @author ManasaPraveen-03
 * @version 9.0
 */

class ReservationValidator {

    /**
     * Validates booking input
     */
    public void validate(
            String guestName,
            String roomType,
            RoomInventory inventory
    ) throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (!availability.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        if (availability.get(roomType) <= 0) {
            throw new InvalidBookingException("Selected room type is not available.");
        }
    }
}


/**
 * =====================================================
 * MAIN CLASS – BookMyStayApp
 * =====================================================
 *
 * Use Case 9: Error Handling & Validation
 *
 * Demonstrates validating user input
 * before processing a booking.
 *
 * @author ManasaPraveen-03
 * @version 9.0
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking Validation");

        Scanner scanner = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        try {

            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            validator.validate(guestName, roomType, inventory);

            bookingQueue.addRequest(guestName);

            System.out.println("Booking request accepted.");

        } catch (InvalidBookingException e) {

            System.out.println("Booking failed: " + e.getMessage());

        } finally {

            scanner.close();
        }
    }
}