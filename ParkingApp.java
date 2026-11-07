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
                    System.out.println("[TODO] Interactive Check-in feature coming in next commit");
                    break;
                case 3:
                    System.out.println("[TODO] Interactive Check-out feature coming in next commit");
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
}
