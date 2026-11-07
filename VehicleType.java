public enum VehicleType {
    MOTORCYCLE(2.50),
    SEDAN(5.00),
    SUV(7.50),
    TRUCK(12.00);

    private final double hourlyRate;

    VehicleType(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }
}
