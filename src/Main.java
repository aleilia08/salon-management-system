import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            System.err.println("Error: Invalid number of arguments. Usage: java StoreManagementSystem -a/-c");
            return;
        }

        List<Appointment> appointments = FileHandler.readAppointments();
        Map<String, User> users = FileHandler.readUsers();

        AppointmentManager appointmentManager = new AppointmentManager(appointments);
        Scanner scanner = new Scanner(System.in);

        String mode = args[0].toLowerCase();
        if ("-a".equals(mode)) {
            adminMode(appointmentManager, scanner, users);
        } else if ("-c".equals(mode)) {
            clientMode(appointmentManager, scanner, users);
        } else {
            System.err.println("Error: Invalid mode. Please choose either -a (admin mode) or -c (client mode).");
        }

    }

    private static void adminMode(AppointmentManager appointmentManager, Scanner scanner, Map<String, User> users){
        System.out.println("Enter admin username:");
        String adminUsername = scanner.nextLine();
        System.out.println("Enter admin password:");
        String adminPassword = scanner.nextLine();

        try {
            Admin admin = (Admin) authenticateAdmin(adminUsername, adminPassword, users);
            if (admin != null) {
                adminOperations(appointmentManager, scanner);
            } else {
                System.out.println("Invalid admin credentials. Returning to main menu.");
            }
        } catch (IllegalArgumentException | ParseException e){
            System.out.println("Invalid admin credentials. Returning to main menu.");
        } catch (UserException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void clientMode(AppointmentManager appointmentManager, Scanner scanner, Map<String, User> users){
        System.out.println("Enter client username:");
        String adminUsername = scanner.nextLine();
        System.out.println("Enter client password:");
        String adminPassword = scanner.nextLine();

        try {
            Client client = (Client) authenticateClient(adminUsername, adminPassword, users);
            if (client != null) {
                clientOperations(appointmentManager, scanner);
            } else {
                System.out.println("Invalid client credentials. Returning to main menu.");
            }
        } catch (IllegalArgumentException | ParseException e){
            System.out.println("Invalid client credentials. Returning to main menu.");
        } catch (UserException e) {
            System.out.println(e.getMessage());
        } catch (InvalidTimeException e) {
            throw new RuntimeException(e);
        }
    }

    private static User authenticateAdmin(String username, String password, Map<String, User> users) throws UserException {
        for (User user : users.values()) {
            if (user instanceof Admin && user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            } else if (user instanceof Client && user.getUsername().equals(username) && user.getPassword().equals(password)) {
                throw new UserException("Client can not log in from admin mode.");
            }
        }
        return null;
    }

    private static User authenticateClient(String username, String password, Map<String, User> users) throws UserException {
        for (User user : users.values()) {
            if (user instanceof Client && user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            } else if (user instanceof Admin && user.getUsername().equals(username) && user.getPassword().equals(password)) {
                throw new UserException("Admin can not log in from client mode.");
            }
        }
        return null;
    }

    private static void adminOperations(AppointmentManager appointmentManager, Scanner scanner) throws ParseException {
        while (true) {
            System.out.println("Admin Operations:");
            System.out.println("1. Delete Appointment");
            System.out.println("2. View All Appointments");
            System.out.println("3. View Available Time Slots");
            System.out.println("4. Logout");

            int choice = getIntInput(scanner);

            switch (choice) {
                case 1:
                    System.out.println("What date is the appointment you want deleted (DD/MM/YYYY HH:MM):");
                    String appointmentDate = scanner.nextLine();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Date date = dateFormat.parse(appointmentDate);

                    appointmentManager.cancelAppointment(date);

                    break;
                case 2:
                    appointmentManager.viewAllAppointments();
                    break;
                case 3:
                    appointmentManager.viewAvailableTime();
                    break;
                case 4:
                    System.out.println("Logging out from admin mode.");
                    FileHandler.updateAppointments(appointmentManager.getAppointments());
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void clientOperations(AppointmentManager appointmentManager, Scanner scanner) throws ParseException, InvalidTimeException {
        while (true) {
            System.out.println("Client Operations:");
            System.out.println("1. Create Appointment");
            System.out.println("2. Cancel Appointment");
            System.out.println("3. View Available Time Slots");
            System.out.println("4. Logout");

            int choice = getIntInput(scanner);

            switch (choice) {
                case 1:
                    System.out.println("What date do you want to schedule an appointment (DD/MM/YYYY HH:MM):");
                    String appointmentDate = scanner.nextLine();
                    System.out.println("What type of appointment do you want? (Nails/Hair/Hair+Nails/Wax):");
                    String appointmentType = scanner.nextLine();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Date date = dateFormat.parse(appointmentDate);

                    try{
                        appointmentManager.addAppointment(date, appointmentType);
                    } catch (InvalidTimeException e){
                        System.out.println(e.getMessage());
                    }

                    break;
                case 2:
                    System.out.println("What date is the appointment you want deleted (DD/MM/YYYY HH:MM):");
                    String appointmentDateCancel = scanner.nextLine();

                    SimpleDateFormat dateFormatCancel = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Date dateCancel = dateFormatCancel.parse(appointmentDateCancel);

                    appointmentManager.cancelAppointment(dateCancel);
                    break;
                case 3:
                    appointmentManager.viewAvailableTime();
                    break;
                case 4:
                    System.out.println("Logging out from admin mode.");
                    FileHandler.updateAppointments(appointmentManager.getAppointments());
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static int getIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }
}