package dacd.suarez.control;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        JMSHotelSender jmsSender = new JMSHotelSender();
        HotelController hotelController = new HotelController(jmsSender);


        Timer timer = new Timer();

        long delay = 0;
        long interval = TimeUnit.HOURS.toMillis(6);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Updating booking data...");
                hotelController.execute();
            }
        }, delay, interval);
    }
}
