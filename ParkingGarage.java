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

    public double checkOutVehicle(String ticketId, double hours) throws InvalidDurationException {
        return checkOutVehicle(ticketId, hours, 0.0);
    }

    public double checkOutVehicle(String ticketId, double hours, double discountRate) throws InvalidDurationException {
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
        if (discountRate > 0) {
            ticket.applyDiscount(discountRate);
        }

        double fee = ticket.calculateTotalFee();
        totalRevenueCollected += fee;
        completedTransactions.add(ticket);
        stalls[slotIndex] = null; // Vacate slot

        System.out.printf("[CHECK-OUT SUCCESS] Stall #%02d is now vacant.%n", (slotIndex + 1));
        ticket.printReceipt();

        return fee;
    }

    public int findStallByLicensePlate(String plate) {
        if (plate == null) return -1;
        for (int i = 0; i < stalls.length; i++) {
            if (stalls[i] != null && stalls[i].getLicensePlate().equalsIgnoreCase(plate.trim())) {
                return i;
            }
        }
        return -1;
    }

    public void displayTransactionHistory() {
        System.out.println("\n==================================================");
        System.out.println("           COMPLETED TRANSACTIONS LEDGER          ");
        System.out.println("==================================================");
        if (completedTransactions.isEmpty()) {
            System.out.println("No completed check-out transactions on record.");
        } else {
            for (int i = 0; i < completedTransactions.size(); i++) {
                ParkingTicket t = completedTransactions.get(i);
                System.out.printf("%2d. [%s] Plate: %-9s | Type: %-15s | %.1f hrs | Paid: $%.2f%n",
                    (i + 1), t.getTicketId(), t.getLicensePlate(), t.getVehicleType().name(),
                    t.getHoursParked(), t.calculateTotalFee());
            }
        }
        System.out.println("==================================================");
    }

    public void generateFinancialAuditReport() {
        System.out.println("\n==================================================");
        System.out.println("           GARAGE FINANCIAL AUDIT REPORT          ");
        System.out.println("==================================================");
        System.out.printf("Total Vehicles Serviced : %d%n", completedTransactions.size());
        System.out.printf("Total Gross Revenue     : $%.2f%n", totalRevenueCollected);
        double avg = completedTransactions.isEmpty() ? 0.0 : (totalRevenueCollected / completedTransactions.size());
        System.out.printf("Average Fee Per Vehicle : $%.2f%n", avg);
        System.out.println("==================================================");
    }

    public void displayGarageFloorPlan() {
        System.out.println("\n==================================================");
        System.out.printf("  %s - STALL OCCUPANCY GRID%n", garageName.toUpperCase());
        System.out.println("==================================================");
        System.out.printf("Capacity: %d | Occupied: %d | Vacant: %d | Revenue: $%.2f%n",
            totalStalls, getOccupiedStallCount(), getAvailableStallCount(), totalRevenueCollected);
        System.out.println("--------------------------------------------------");

        for (int i = 0; i < stalls.length; i++) {
            if (stalls[i] == null) {
                System.out.printf("[Stall %02d]: [ VACANT ]%n", (i + 1));
            } else {
                ParkingTicket t = stalls[i];
                System.out.printf("[Stall %02d]: [OCCUPIED] %s | %s (%s)%n",
                    (i + 1), t.getTicketId(), t.getLicensePlate(), t.getVehicleType().name());
            }
        }
        System.out.println("==================================================");
    }

    public ParkingTicket[] getActiveStalls() {
        return stalls;
    }
}
