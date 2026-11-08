import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ParkingLotFullException extends Exception {
    private static final long serialVersionUID = 102L;

    private int totalCapacity;
    private int occupiedSlots;
    private String timestamp;

    public ParkingLotFullException() {
        super("The parking facility is currently full. No available stalls.");
        this.totalCapacity = 0;
        this.occupiedSlots = 0;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public ParkingLotFullException(String message) {
        super(message);
        this.totalCapacity = 0;
        this.occupiedSlots = 0;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public ParkingLotFullException(int totalCapacity, int occupiedSlots, String message) {
        super(String.format("Parking facility full [%d/%d occupied at %s]: %s", 
            occupiedSlots, totalCapacity, 
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), 
            message));
        this.totalCapacity = totalCapacity;
        this.occupiedSlots = occupiedSlots;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public int getOccupiedSlots() {
        return occupiedSlots;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
