public enum VehicleType {
    MOTORCYCLE(2.50, "Motorcycle / Scooter", 1),
    SEDAN(5.00, "Standard Sedan / Compact", 1),
    SUV(7.50, "Full-Size SUV / Minivan", 1),
    TRUCK(12.00, "Commercial Truck / Bus", 2),
    ELECTRIC_VEHICLE(3.50, "Electric Vehicle (Eco-Rate)", 1);

    private final double hourlyRate;
    private final String displayName;
    private final int stallsRequired;

    VehicleType(double hourlyRate, String displayName, int stallsRequired) {
        this.hourlyRate = hourlyRate;
        this.displayName = displayName;
        this.stallsRequired = stallsRequired;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getStallsRequired() {
        return stallsRequired;
    }

    /**
     * Safely finds a VehicleType by enum name or partial display name case-insensitively.
     */
    public static VehicleType fromString(String text) {
        if (text == null) return null;
        for (VehicleType v : VehicleType.values()) {
            if (v.name().equalsIgnoreCase(text.trim()) || v.displayName.toLowerCase().contains(text.toLowerCase().trim())) {
                return v;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("%s ($%.2f/hr, %d stall%s)", 
            displayName, hourlyRate, stallsRequired, (stallsRequired > 1 ? "s" : ""));
    }
}
