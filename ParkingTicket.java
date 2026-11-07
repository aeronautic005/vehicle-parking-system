public class ParkingTicket {
    public static final double OVERNIGHT_SURCHARGE = 15.00;
    public static final double OVERNIGHT_THRESHOLD_HOURS = 12.0;

    private String ticketId;
    private String licensePlate;
    private VehicleType vehicleType;
    private double hoursParked;

    public ParkingTicket(String ticketId, String licensePlate, VehicleType vehicleType) {
        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket ID cannot be empty.");
        }
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be empty.");
        }
        if (vehicleType == null) {
            throw new IllegalArgumentException("Vehicle type must be specified.");
        }

        this.ticketId = ticketId.trim().toUpperCase();
        this.licensePlate = licensePlate.trim().toUpperCase();
        this.vehicleType = vehicleType;
        this.hoursParked = 0.0;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public double getHoursParked() {
        return hoursParked;
    }

    public void setHoursParked(double hours) throws InvalidDurationException {
        if (hours <= 0.0 || hours > 24.0) {
            throw new InvalidDurationException(hours, 0.1, 24.0, 
                "Parking duration must be strictly between 0.1 and 24.0 hours.");
        }
        this.hoursParked = hours;
    }

    public double calculateBaseFee() {
        return hoursParked * vehicleType.getHourlyRate();
    }

    public double calculateOvernightSurcharge() {
        return (hoursParked > OVERNIGHT_THRESHOLD_HOURS) ? OVERNIGHT_SURCHARGE : 0.0;
    }

    public double calculateTotalFee() {
        return calculateBaseFee() + calculateOvernightSurcharge();
    }

    public void printReceipt() {
        System.out.println("==================================================");
        System.out.println("              PARKING EXIT RECEIPT                ");
        System.out.println("==================================================");
        System.out.println("Ticket ID    : " + ticketId);
        System.out.println("Plate Number : " + licensePlate);
        System.out.println("Vehicle Type : " + vehicleType.getDisplayName());
        System.out.printf("Duration     : %.2f Hours%n", hoursParked);
        System.out.printf("Hourly Rate  : $%.2f / hr%n", vehicleType.getHourlyRate());
        System.out.println("--------------------------------------------------");
        System.out.printf("Base Parking : $%.2f%n", calculateBaseFee());
        if (calculateOvernightSurcharge() > 0) {
            System.out.printf("Overnight Fee: $%.2f (> %.0f hrs surcharge)%n", 
                calculateOvernightSurcharge(), OVERNIGHT_THRESHOLD_HOURS);
        }
        System.out.printf("TOTAL CHARGE : $%.2f%n", calculateTotalFee());
        System.out.println("==================================================");
    }

    @Override
    public String toString() {
        return String.format("[%s] Plate: %s (%s)", ticketId, licensePlate, vehicleType.getDisplayName());
    }
}
