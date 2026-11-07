public class InvalidDurationException extends Exception {
    public InvalidDurationException() {
        super("Parking duration is invalid.");
    }

    public InvalidDurationException(String message) {
        super(message);
    }
}
