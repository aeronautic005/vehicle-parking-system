public class ParkingLotFullException extends Exception {
    public ParkingLotFullException() {
        super("The parking facility is currently full.");
    }

    public ParkingLotFullException(String message) {
        super(message);
    }
}
