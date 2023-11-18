package dacd.suarez.control;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        WeatherProvider weatherProvider = new OpenWeatherMapProvider(args[0], "https://api.openweathermap.org/data/2.5/forecast");
        WeatherStore weatherStore = new SqliteWeatherStore("jdbc:sqlite:" + args[1]);
        WeatherController weatherController = new WeatherController(weatherProvider, weatherStore);

        Timer timer = new Timer();

        long delay = 0;
        long interval = TimeUnit.HOURS.toMillis(6);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Updating weather data...");
                weatherController.execute();
            }
        }, delay, interval);
    }
}

