public class ParkingTicket {
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
}
