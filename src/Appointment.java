import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment {
    private AppointmentType appointmentType;
    private Date date;
    private int duration;

    Appointment(AppointmentType appointmentType, Date date, int duration){
        this.appointmentType = appointmentType;
        this.date = date;
        this.duration = duration;
    }

    public String getType(){
        return appointmentType.toString();
    }

    public Date getDate(){
        return date;
    }

    public int getDuration(){
        return duration;
    }


}
