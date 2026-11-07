public class ParkingLotFullException extends Exception {
    private int totalCapacity;
    private int occupiedSlots;

    public ParkingLotFullException() {
        super("The parking facility is currently full. No available stalls.");
        this.totalCapacity = 0;
        this.occupiedSlots = 0;
    }

    public ParkingLotFullException(String message) {
        super(message);
        this.totalCapacity = 0;
        this.occupiedSlots = 0;
    }

    public ParkingLotFullException(int totalCapacity, int occupiedSlots, String message) {
        super(String.format("Parking facility full [%d/%d occupied]: %s", occupiedSlots, totalCapacity, message));
        this.totalCapacity = totalCapacity;
        this.occupiedSlots = occupiedSlots;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public int getOccupiedSlots() {
        return occupiedSlots;
    }
}
