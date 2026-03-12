import java.util.*;

/**
 * =====================================================
 * CLASS – Service
 * =====================================================
 *
 * Use Case 7: Add-On Service Selection
 *
 * Description:
 * This class represents an optional service
 * that can be added to a confirmed reservation.
 *
 * Examples:
 * - Breakfast
 * - Spa
 * - Airport Pickup
 *
 * @author ManasaPraveen-03
 * @version 7.0
 */

class Service {

    /** Name of the service */
    private String serviceName;

    /** Cost of the service */
    private double cost;

    /**
     * Creates a new add-on service
     *
     * @param serviceName name of the service
     * @param cost cost of the service
     */
    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    /** @return service name */
    public String getServiceName() {
        return serviceName;
    }

    /** @return service cost */
    public double getCost() {
        return cost;
    }
}


/**
 * =====================================================
 * CLASS – AddOnServiceManager
 * =====================================================
 *
 * Use Case 7: Add-On Service Selection
 *
 * Description:
 * This class manages optional services
 * associated with confirmed reservations.
 *
 * It supports attaching multiple services
 * to a single reservation.
 *
 * @author ManasaPraveen-03
 * @version 7.0
 */

class AddOnServiceManager {

    /**
     * Maps reservation ID to selected services
     *
     * Key   -> Reservation ID
     * Value -> List of selected services
     */
    private Map<String, List<Service>> servicesByReservation;

    /** Initializes the service manager */
    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    /**
     * Attaches a service to a reservation
     *
     * @param reservationId confirmed reservation ID
     * @param service add-on service
     */
    public void addService(String reservationId, Service service) {

        servicesByReservation.putIfAbsent(reservationId, new ArrayList<>());

        servicesByReservation.get(reservationId).add(service);
    }

    /**
     * Calculates total add-on cost
     *
     * @param reservationId reservation ID
     * @return total service cost
     */
    public double calculateTotalServiceCost(String reservationId) {

        double total = 0;

        List<Service> services = servicesByReservation.get(reservationId);

        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}


/**
 * =====================================================
 * MAIN CLASS – BookMyStayApp
 * =====================================================
 *
 * Use Case 7: Add-On Service Selection
 *
 * Description:
 * Demonstrates how optional services
 * can be attached to a confirmed booking.
 *
 * Services are added after room allocation
 * and do not affect inventory.
 *
 * @author ManasaPraveen-03
 * @version 7.0
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Add-On Service Selection");

        String reservationId = "Single-1";

        AddOnServiceManager manager = new AddOnServiceManager();

        Service breakfast = new Service("Breakfast", 500);
        Service spa = new Service("Spa", 1000);

        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, spa);

        double totalCost = manager.calculateTotalServiceCost(reservationId);

        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);
    }
}