import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AppointmentManager {
    int[] duration = {40, 60, 100, 30};
    private List<Appointment> appointments;
    AppointmentManager(List<Appointment> app){
        appointments = app;
    }

    public void addAppointment(Date date, String type) throws InvalidTimeException{

        for (Appointment appointment : appointments){
            if (appointment.getDate().equals(date)){
                throw new InvalidTimeException("Selected date is already booked. Please select another date.");
            }
        }

        appointments.add(new Appointment(AppointmentType.valueOf(type), date, duration[AppointmentType.valueOf(type).ordinal()]));
    }

    public void cancelAppointment(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String formattedDate = dateFormat.format(date);

        Iterator<Appointment> iterator = appointments.iterator();
        while (iterator.hasNext()) {
            Appointment appointment = iterator.next();
            System.out.println(appointment.getDate());
            if (appointment.getDate().equals(formattedDate)) {
                iterator.remove();
                System.out.println("Appointment at " + date + " has been canceled.");
                return;
            }
        }
        System.out.println("No appointment found at " + date);
    }

    public void viewAllAppointments(){
        for (Appointment appointment : appointments){
            System.out.print(appointment.getDate() + " ");
            System.out.print(appointment.getType() + " ");
            System.out.print(Integer.toString(appointment.getDuration()) + "min");
            System.out.println();
        }
    }

    public void viewAvailableTime() throws ParseException {
        List<Date> availableTimeSlots = getAvailableTimeSlots(appointments);

        System.out.println("Available Time Slots:");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (Date timeSlot : availableTimeSlots) {
            System.out.println(dateFormat.format(timeSlot));
        }
    }

    public static List<Date> getAvailableTimeSlots(List<Appointment> existingAppointments) throws ParseException {
        List<Date> availableTimeSlots = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        // Set the start time to 9 am
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Calculate the end time (7 days from now)
        Calendar endCalendar = (Calendar) calendar.clone();
        endCalendar.add(Calendar.DAY_OF_YEAR, 7);

        // Generate all possible time slots within the specified time range
        while (calendar.before(endCalendar)) {
            Date currentTimeSlot = calendar.getTime();
            // Check if the current time slot is not already booked
            if (isTimeSlotAvailable(currentTimeSlot, existingAppointments)) {
                availableTimeSlots.add(currentTimeSlot);
            }
            // Move to the next time slot
            calendar.add(Calendar.MINUTE, 60);
        }

        return availableTimeSlots;
    }

    private static boolean isTimeSlotAvailable(Date timeSlot, List<Appointment> existingAppointments) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timeSlot);

        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        return hourOfDay >= 9 && hourOfDay < 17 && !isTimeSlotBooked(timeSlot, existingAppointments);
    }

    private static boolean isTimeSlotBooked(Date timeSlot, List<Appointment> existingAppointments) {
        for (Appointment appointment : existingAppointments) {
            Date startTime = appointment.getDate();
            if (startTime.equals(timeSlot))
                return true;
        }
        return false;
    }

    private static Date getEndTime(Appointment appointment) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(appointment.getDate());
        calendar.add(Calendar.MINUTE, appointment.getDuration());
        return calendar.getTime();
    }

    public List<Appointment> getAppointments(){
        return appointments;
    }

}
