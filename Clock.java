package goldensalon;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Clock extends Thread {

    private LocalTime currentTime;
    private boolean closingTime = false;

    public Clock() {
        currentTime = LocalTime.now();
    }

    public String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return currentTime.format(formatter);
    }

    @Override
    public void run() {
        while (!closingTime) {
            LocalTime tempTime = LocalTime.now();
            currentTime = LocalTime.of(tempTime.getHour(), tempTime.getMinute(), 
                    tempTime.getSecond()).plusSeconds(1);
            currentTime = currentTime.plusSeconds(1);
        }
    }
    
public synchronized void setclosingTime(){
        closingTime = true;
    }
}
