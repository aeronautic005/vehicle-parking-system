import java.util.ArrayList;

public class ParkingGarage {
    private String garageName;
    private int totalStalls;
    private ParkingTicket[] stalls;
    private ArrayList<ParkingTicket> completedTransactions;
    private int ticketCounter = 1000;

    public ParkingGarage(String garageName, int totalStalls) {
        if (totalStalls <= 0) {
            throw new IllegalArgumentException("Total stalls must be strictly positive.");
        }
        this.garageName = garageName;
        this.totalStalls = totalStalls;
        this.stalls = new ParkingTicket[totalStalls];
        this.completedTransactions = new ArrayList<>();
    }

    public String getGarageName() {
        return garageName;
    }

    public int getTotalStalls() {
        return totalStalls;
    }

    public int getOccupiedStallCount() {
        int count = 0;
        for (ParkingTicket t : stalls) {
            if (t != null) count++;
        }
        return count;
    }

    public int getAvailableStallCount() {
        return totalStalls - getOccupiedStallCount();
    }

    /**
     * Attempts to check in a vehicle.
     * Throws ParkingLotFullException if no slots are available.
     */
    public ParkingTicket checkInVehicle(String licensePlate, VehicleType type) throws ParkingLotFullException {
        int emptySlot = -1;
        for (int i = 0; i < stalls.length; i++) {
            if (stalls[i] == null) {
                emptySlot = i;
                break;
            }
        }

        if (emptySlot == -1) {
            throw new ParkingLotFullException(totalStalls, getOccupiedStallCount(), 
                "No vacant stalls available for check-in.");
        }

        String ticketId = "TKT-" + (++ticketCounter);
        ParkingTicket ticket = new ParkingTicket(ticketId, licensePlate, type);
        stalls[emptySlot] = ticket;

        System.out.printf("[CHECK-IN SUCCESS] Assigned Stall #%02d to Plate '%s' (%s). Ticket ID: %s%n",
            (emptySlot + 1), licensePlate, type.getDisplayName(), ticketId);

        return ticket;
    }
}
