import java.util.InputMismatchException;
import java.util.Scanner;

public class ParkingApp {
    private static ParkingGarage garage = new ParkingGarage("Metro Central Tower Parking", 8);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeSampleParkedVehicles();

        boolean running = true;
        System.out.println("=================================================");
        System.out.println("     WELCOME TO METRO PARKING AUTOMATION HUB     ");
        System.out.println("=================================================");

        while (running) {
            printMenu();
            System.out.print("Enter choice (1-8): ");
            int choice = readIntChoice();

            switch (choice) {
                case 1:
                    garage.displayGarageFloorPlan();
                    break;
                case 2:
                    handleVehicleCheckIn();
                    break;
                case 3:
                    handleVehicleCheckOut();
                    break;
                case 4:
                    handleVehicleSearchByPlate();
                    break;
                case 5:
                    garage.displayTransactionHistory();
                    break;
                case 6:
                    garage.generateFinancialAuditReport();
                    break;
                case 7:
                    runAutomatedTestSuite();
                    break;
                case 8:
                    running = false;
                    System.out.println("\nThank you for choosing Metro Parking. Drive safely!");
                    break;
                default:
                    System.out.println("[ERROR] Invalid selection. Choose a number between 1 and 8.");
            }
            System.out.println();
        }

        scanner.close();
    }

    private static void initializeSampleParkedVehicles() {
        try {
            garage.checkInVehicle("ABC-1234", VehicleType.SEDAN);
            garage.checkInVehicle("XYZ-9988", VehicleType.SUV);
            garage.checkInVehicle("MOTO-555", VehicleType.MOTORCYCLE);
            garage.checkInVehicle("TRK-7711", VehicleType.TRUCK);
            garage.checkInVehicle("ECO-2024", VehicleType.ELECTRIC_VEHICLE);
        } catch (ParkingLotFullException e) {
            System.out.println("[ERROR] Sample vehicle initialization failed: " + e.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println("----------------- MAIN MENU ---------------------");
        System.out.println("1. View Parking Garage Occupancy Floor Plan");
        System.out.println("2. Check-In Vehicle (Generate Ticket)");
        System.out.println("3. Check-Out Vehicle (Calculate Fee & Exit)");
        System.out.println("4. Locate Vehicle by License Plate");
        System.out.println("5. View Completed Transactions Ledger");
        System.out.println("6. Generate Financial & Revenue Audit Report");
        System.out.println("7. Run Automated Exception & Rate Test Suite");
        System.out.println("8. Exit");
        System.out.println("-------------------------------------------------");
    }

    private static void handleVehicleCheckIn() {
        System.out.println("\n--- VEHICLE CHECK-IN KIOSK ---");
        System.out.print("Enter License Plate Number: ");
        String plate = scanner.nextLine().trim();

        System.out.println("Select Vehicle Type:");
        VehicleType[] types = VehicleType.values();
        for (int i = 0; i < types.length; i++) {
            System.out.printf("%d. %s%n", (i + 1), types[i]);
        }
        System.out.print("Choice (1-" + types.length + "): ");
        int typeIndex = readIntChoice();

        if (typeIndex < 1 || typeIndex > types.length) {
            System.out.println("[ERROR] Invalid vehicle category selected.");
            return;
        }

        VehicleType selectedType = types[typeIndex - 1];

        try {
            garage.checkInVehicle(plate, selectedType);
        } catch (ParkingLotFullException e) {
            System.out.println("\n[CAUGHT ParkingLotFullException]: " + e.getMessage());
            System.out.printf("  Occupancy Status: %d of %d stalls taken. Please seek alternate parking.%n",
                e.getOccupiedSlots(), e.getTotalCapacity());
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR]: " + e.getMessage());
        }
    }

    private static void handleVehicleCheckOut() {
        System.out.println("\n--- VEHICLE CHECK-OUT & PAYMENT KIOSK ---");
        System.out.print("Enter Ticket ID (e.g. TKT-1001): ");
        String ticketId = scanner.nextLine().trim();

        System.out.print("Enter Total Hours Parked (0.1 - 24.0): ");
        double hours = readDouble();

        System.out.print("Is customer a VIP Member? (y/n): ");
        String vipResp = scanner.nextLine().trim();
        double discount = vipResp.equalsIgnoreCase("y") ? 0.15 : 0.0; // 15% VIP discount

        try {
            garage.checkOutVehicle(ticketId, hours, discount);
        } catch (InvalidDurationException e) {
            System.out.println("\n[CAUGHT InvalidDurationException]: " + e.getMessage());
            System.out.printf("  Attempted Input: %.2f hours | Valid Range: %.1f to %.1f hrs.%n",
                e.getAttemptedHours(), e.getMinHours(), e.getMaxHours());
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR]: " + e.getMessage());
        }
    }

    private static void handleVehicleSearchByPlate() {
        System.out.println("\n--- LOCATE VEHICLE BY LICENSE PLATE ---");
        System.out.print("Enter License Plate to Search: ");
        String plate = scanner.nextLine().trim();

        int stallIndex = garage.findStallByLicensePlate(plate);
        if (stallIndex != -1) {
            ParkingTicket t = garage.getActiveStalls()[stallIndex];
            System.out.printf("[FOUND] Vehicle '%s' is parked in Stall #%02d! (Ticket: %s | Type: %s)%n",
                plate, (stallIndex + 1), t.getTicketId(), t.getVehicleType().getDisplayName());
        } else {
            System.out.printf("[NOT FOUND] No vehicle with license plate '%s' is currently parked.%n", plate);
        }
    }

    private static void runAutomatedTestSuite() {
        System.out.println("\n=================================================");
        System.out.println("     RUNNING AUTOMATED TEST & EXCEPTION SUITE    ");
        System.out.println("=================================================");

        ParkingGarage testGarage = new ParkingGarage("Test Facility", 2);

        // Test 1: Zero or negative duration exception
        System.out.print("[Test 1] Testing zero/negative duration (-2.5 hrs): ");
        try {
            ParkingTicket t = new ParkingTicket("TKT-TEST", "TEST-01", VehicleType.SEDAN);
            t.setHoursParked(-2.5);
            System.out.println("FAIL (Exception was not thrown)");
        } catch (InvalidDurationException e) {
            System.out.println("PASS -> Caught: " + e.getMessage());
        }

        // Test 2: Duration exceeding 24-hour limit
        System.out.print("[Test 2] Testing duration exceeding 24 hours (28.0 hrs): ");
        try {
            ParkingTicket t = new ParkingTicket("TKT-TEST", "TEST-02", VehicleType.SEDAN);
            t.setHoursParked(28.0);
            System.out.println("FAIL (Exception was not thrown)");
        } catch (InvalidDurationException e) {
            System.out.println("PASS -> Caught: " + e.getMessage());
        }

        // Test 3: Overnight surcharge verification (> 12 hrs)
        try {
            ParkingTicket tSedan = new ParkingTicket("TKT-TEST", "TEST-03", VehicleType.SEDAN);
            tSedan.setHoursParked(14.0);
            double total = tSedan.calculateTotalFee();
            System.out.printf("[Test 3] Overnight fee formula (14 hrs @ $5/hr + $15 fee = $85.00): $%.2f -> %s%n",
                total, (total == 85.00 ? "PASS" : "FAIL"));
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected error in Test 3: " + e.getMessage());
        }

        // Test 4: Capacity overflow throwing ParkingLotFullException
        System.out.print("[Test 4] Testing parking lot full exception (filling 2/2 stalls): ");
        try {
            testGarage.checkInVehicle("CAR-1", VehicleType.SEDAN);
            testGarage.checkInVehicle("CAR-2", VehicleType.SEDAN);
            testGarage.checkInVehicle("CAR-3", VehicleType.SEDAN);
            System.out.println("FAIL (Exception was not thrown)");
        } catch (ParkingLotFullException e) {
            System.out.println("PASS -> Caught expected ParkingLotFullException: " + e.getMessage());
        }

        System.out.println("=================================================");
        System.out.println("         ALL TESTS COMPLETED SUCCESSFULLY!       ");
        System.out.println("=================================================");
    }

    private static int readIntChoice() {
        try {
            int val = scanner.nextInt();
            scanner.nextLine();
            return val;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return -1;
        }
    }

    private static double readDouble() {
        try {
            double val = scanner.nextDouble();
            scanner.nextLine();
            return val;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return -1.0;
        }
    }
}
