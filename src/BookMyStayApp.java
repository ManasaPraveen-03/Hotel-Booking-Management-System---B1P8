import java.util.*;

/**
 * =====================================================
 * CLASS – Reservation
 * =====================================================
 *
 * Represents a confirmed reservation.
 *
 * @author ManasaPraveen-03
 * @version 8.0
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
 * CLASS – BookingHistory
 * =====================================================
 *
 * Use Case 8: Booking History & Reporting
 *
 * Description:
 * Maintains a record of confirmed reservations.
 * Provides ordered storage for reporting.
 *
 * @author ManasaPraveen-03
 * @version 8.0
 */

class BookingHistory {

    /** List storing confirmed reservations */
    private List<Reservation> confirmedReservations;

    /** Initializes an empty booking history */
    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    /**
     * Adds a confirmed reservation
     * to booking history
     */
    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    /**
     * Returns all confirmed reservations
     */
    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}


/**
 * =====================================================
 * CLASS – BookingReportService
 * =====================================================
 *
 * Use Case 8: Booking History & Reporting
 *
 * Description:
 * Generates reports from booking history data.
 *
 * Reporting logic is separated from storage.
 *
 * @author ManasaPraveen-03
 * @version 8.0
 */

class BookingReportService {

    /**
     * Displays booking report
     */
    public void generateReport(BookingHistory history) {

        System.out.println("\nBooking History Report");

        for (Reservation r : history.getConfirmedReservations()) {

            System.out.println(
                    "Guest: " + r.getGuestName()
                            + ", Room Type: " + r.getRoomType()
            );
        }
    }
}


/**
 * =====================================================
 * MAIN CLASS – BookMyStayApp
 * =====================================================
 *
 * Use Case 8: Booking History & Reporting
 *
 * Demonstrates storing confirmed bookings
 * and generating reports.
 *
 * @author ManasaPraveen-03
 * @version 8.0
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking History and Reporting");

        BookingHistory history = new BookingHistory();

        history.addReservation(new Reservation("Abhi", "Single"));
        history.addReservation(new Reservation("Subha", "Double"));
        history.addReservation(new Reservation("Vannathi", "Suite"));

        BookingReportService reportService = new BookingReportService();

        reportService.generateReport(history);
    }
}