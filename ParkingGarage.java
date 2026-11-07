import java.util.ArrayList;

public class ParkingGarage {
    private String garageName;
    private int totalStalls;
    private ParkingTicket[] stalls;
    private ArrayList<ParkingTicket> completedTransactions;
    private int ticketCounter = 1000;
    private double totalRevenueCollected = 0.0;

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

    public double getTotalRevenueCollected() {
        return totalRevenueCollected;
    }

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

    /**
     * Checks out a vehicle by ticket ID, computes fees, prints receipt, and frees the stall.
     */
    public double checkOutVehicle(String ticketId, double hours) throws InvalidDurationException {
        int slotIndex = -1;
        for (int i = 0; i < stalls.length; i++) {
            if (stalls[i] != null && stalls[i].getTicketId().equalsIgnoreCase(ticketId)) {
                slotIndex = i;
                break;
            }
        }

        if (slotIndex == -1) {
            throw new IllegalArgumentException("Ticket ID '" + ticketId + "' not found in active parking stalls.");
        }

        ParkingTicket ticket = stalls[slotIndex];
        ticket.setHoursParked(hours);

        double fee = ticket.calculateTotalFee();
        totalRevenueCollected += fee;
        completedTransactions.add(ticket);
        stalls[slotIndex] = null; // Vacate slot

        System.out.printf("[CHECK-OUT SUCCESS] Stall #%02d is now vacant.%n", (slotIndex + 1));
        ticket.printReceipt();

        return fee;
    }

    public ParkingTicket[] getActiveStalls() {
        return stalls;
    }
}
