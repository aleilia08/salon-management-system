import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileHandler {
    private static final String APPOINTMENTS_FILE_PATH = "F:\\faculta\\StoreManagementSystem\\src\\appointments.csv";
    private static final String USERS_FILE_PATH = "F:\\faculta\\StoreManagementSystem\\src\\users.csv";

    public static List<Appointment> readAppointments() {
        List<Appointment> appointments = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(APPOINTMENTS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] appData = line.split(",");

                AppointmentType type = AppointmentType.valueOf(appData[0].trim());

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date date = dateFormat.parse(appData[1].trim());

                int duration = Integer.parseInt(appData[2].trim());

                appointments.add(new Appointment(type, date, duration));
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return appointments;
    }

    public static Map<String, User> readUsers() {
        Map<String, User> users = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");

                String username = userData[0].trim();
                String password = userData[1].trim();
                String userType = userData[2].trim();

                User user;
                if ("admin".equalsIgnoreCase(userType)) {
                    user = new Admin(username, password);
                } else if ("client".equalsIgnoreCase(userType)) {
                    user = new Client(username, password);
                } else {
                    throw new InvalidPropertiesFormatException("dada");
                }

                users.put(username, user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public static void updateAppointments(List<Appointment> appointments){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(APPOINTMENTS_FILE_PATH))) {
            for (Appointment appointment : appointments) {

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String date = dateFormat.format(appointment.getDate());

                String line = String.format("%s,%s,%d", appointment.getType(), date, appointment.getDuration());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
