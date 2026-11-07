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
            System.out.print("Enter choice (1-5): ");
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
                    System.out.println("[TODO] Automated Exception Test Suite coming in next commit");
                    break;
                case 5:
                    running = false;
                    System.out.println("\nThank you for choosing Metro Parking. Drive safely!");
                    break;
                default:
                    System.out.println("[ERROR] Invalid selection. Choose a number between 1 and 5.");
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
        } catch (ParkingLotFullException e) {
            System.out.println("[ERROR] Sample vehicle initialization failed: " + e.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println("----------------- MAIN MENU ---------------------");
        System.out.println("1. View Parking Garage Occupancy Floor Plan");
        System.out.println("2. Check-In Vehicle (Generate Ticket)");
        System.out.println("3. Check-Out Vehicle (Calculate Fee & Exit)");
        System.out.println("4. Run Automated Exception & Rate Test Suite");
        System.out.println("5. Exit");
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

        try {
            garage.checkOutVehicle(ticketId, hours);
        } catch (InvalidDurationException e) {
            System.out.println("\n[CAUGHT InvalidDurationException]: " + e.getMessage());
            System.out.printf("  Attempted Input: %.2f hours | Valid Range: %.1f to %.1f hrs.%n",
                e.getAttemptedHours(), e.getMinHours(), e.getMaxHours());
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR]: " + e.getMessage());
        }
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
