import java.util.ArrayList;

public class ParkingGarage {
    private String garageName;
    private int totalStalls;
    private ParkingTicket[] stalls;
    private ArrayList<ParkingTicket> completedTransactions;

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
}
