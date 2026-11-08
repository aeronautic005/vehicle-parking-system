public class InvalidDurationException extends Exception {
    private static final long serialVersionUID = 101L;

    private double attemptedHours;
    private double minHours;
    private double maxHours;

    public InvalidDurationException() {
        super("Parking duration is invalid. Must be strictly positive and no more than 24.0 hours.");
        this.attemptedHours = 0.0;
        this.minHours = 0.1;
        this.maxHours = 24.0;
    }

    public InvalidDurationException(String message) {
        super(message);
        this.attemptedHours = 0.0;
        this.minHours = 0.1;
        this.maxHours = 24.0;
    }

    public InvalidDurationException(double attemptedHours, double minHours, double maxHours, String message) {
        super(String.format("Invalid parking duration [%.2f hrs]: %s (Allowed: %.1f - %.1f hrs)", 
            attemptedHours, message, minHours, maxHours));
        this.attemptedHours = attemptedHours;
        this.minHours = minHours;
        this.maxHours = maxHours;
    }

    public double getAttemptedHours() {
        return attemptedHours;
    }

    public double getMinHours() {
        return minHours;
    }

    public double getMaxHours() {
        return maxHours;
    }
}
