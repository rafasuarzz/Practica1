package dacd.suarez.control;

public class Main {
    public static void main(String[] args) {
        WeatherProvider weatherProvider = new OpenWeatherMapProvider(args[0], args[1]);
        WeatherStore weatherStore = new SqliteWeatherStore();

        WeatherController weatherController = new WeatherController(weatherProvider, weatherStore);
        weatherController.execute();
    }

}

