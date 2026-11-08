# Automated Vehicle Parking & Rental Billing System

An enterprise-ready Object-Oriented Java application simulating an intelligent multi-tiered parking garage facility. Features include enumerated vehicle categories, custom business logic exceptions, capacity tracking, dynamic fee formulas with overnight surcharges, and interactive kiosk ticketing.

## System Architecture

```text
+--------------------------------------------------------------------------+
|                               ParkingApp                                 |
|                       (CLI Kiosk & Interactive UI)                       |
+------------------------------------+-------------------------------------+
                                     |
                                     v
+--------------------------------------------------------------------------+
|                             ParkingGarage                                |
|         (Capacity Management, Slot Grid, Financial Aggregates)           |
+------------------+-----------------------------------+-------------------+
                   |                                   |
                   v                                   v
        +--------------------+              +--------------------+
        |   ParkingTicket    |              |    VehicleType     |
        | (Pricing & Receipt)|              |   (Rates & Enums)  |
        +---------+----------+              +--------------------+
                  |
        +---------+---------------------------------+
        |                                           |
        v                                           v
+-------------------------------+   +-------------------------------+
|   InvalidDurationException    |   |    ParkingLotFullException    |
| (Validation for 0 < hrs <= 24)|   |  (Capacity Exhaustion Event)  |
+-------------------------------+   +-------------------------------+
```

## Pricing & Rate Sheet

| Vehicle Category | Hourly Rate | Stall Footprint | Overnight Surcharge (> 12 hrs) |
| :--- | :---: | :---: | :---: |
| **Motorcycle / Scooter** | $2.50 | 1 Stall | +$15.00 flat fee |
| **Sedan / Compact** | $5.00 | 1 Stall | +$15.00 flat fee |
| **Full-Size SUV** | $7.50 | 1 Stall | +$15.00 flat fee |
| **Commercial Truck / Bus** | $12.00 | 2 Stalls | +$15.00 flat fee |

## How to Compile & Run

```bash
# Compile all files
javac *.java

# Run the program
java ParkingApp
```
