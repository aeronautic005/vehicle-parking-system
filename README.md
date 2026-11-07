# Automated Vehicle Parking & Rental Billing System

An Object-Oriented Java application simulating an intelligent multi-tiered parking garage facility. Features include enumerated vehicle categories, custom business logic exceptions, capacity tracking, dynamic fee formulas with overnight surcharges, and interactive kiosk ticketing.

## Architecture & Concepts

- **Enums (`VehicleType.java`):** Enumerates vehicle classifications (`MOTORCYCLE`, `SEDAN`, `SUV`, `TRUCK`) encapsulated with hourly rates and stall space coefficients.
- **Custom Exceptions:**
  - `InvalidDurationException.java`: Enforces realistic parking duration constraints (0 < hours <= 24).
  - `ParkingLotFullException.java`: Thrown when facility capacity is exhausted.
- **Ticketing Model (`ParkingTicket.java`):** Encapsulates ticket IDs, license plates, timestamps, and calculates exact billing breakdowns.
- **Garage Manager (`ParkingGarage.java`):** Tracks slot availability, processes vehicle check-ins/check-outs, and calculates tiered rates with overnight fees.
- **Driver Program (`ParkingApp.java`):** Interactive kiosk CLI with garage occupancy visualizations and automated boundary test suite.

## How to Compile & Run

```bash
# Compile all files
javac *.java

# Run the program
java ParkingApp
```
