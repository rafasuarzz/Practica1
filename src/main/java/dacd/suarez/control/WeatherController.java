package dacd.suarez.control;

import dacd.suarez.model.Location;
import dacd.suarez.model.Weather;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class WeatherController {
    private WeatherProvider weatherProvider;

    public WeatherController(WeatherProvider weatherProvider) {
        this.weatherProvider = weatherProvider;
    }

    // Constructor


    // Método para ejecutar la obtención de datos climáticos
    public void execute() {
        Location elHierro = new Location("Valverde", 28.46824, -16.25462);
        Location laPalma = new Location("Santa Cruz de la Palma", 28.68351, -17.7642);
        Location laGomera = new Location("San Sebastián de la Gomera", 28.09163, -17.11331);
        Location tenerife = new Location("Santa Cruz de Tenerife", 27.80628, -17.91578);
        Location granCanaria = new Location("Las Palmas de Gran Canaria", 28.09973, -15.41343);
        Location fuerteventura = new Location("Puerto del Rosario", 28.50038, -13.86272);
        Location lanzarote = new Location("Arrecife", 28.96302, -13.54769);
        Location laGraciosa = new Location("Caleta de Sebo", 29.23147, -13.50341);

        List<Location> locationList = List.of(elHierro, laPalma, laGomera, tenerife, granCanaria,
                fuerteventura, lanzarote, laGraciosa);

        ArrayList<Instant> instantList = new ArrayList<>();
        ArrayList<Weather> weatherArrayList = new ArrayList<>();

        createInstant(instantList);
        getWeatherCall(instantList, locationList, weatherArrayList);
        loadCall(instantList, locationList);
    }

    public static ArrayList<Instant> createInstant(ArrayList<Instant> instants) {
        for (int i = 0; i < 5; i++) {
            LocalDate hoy = LocalDate.now();
            LocalTime hour = LocalTime.of(12, 0);
            LocalDateTime todayHour = LocalDateTime.of(hoy, hour);
            Instant instant = todayHour.toInstant(ZoneOffset.UTC);
            Instant instant1 = instant.plus(i, ChronoUnit.DAYS);
            instants.add(instant1);
        }
        return instants;
    }

    public static ArrayList<Weather> getWeatherCall(ArrayList<Instant> instantList, List<Location> locationList,
                                                    ArrayList<Weather> weatherArrayList) {
        WeatherProvider weatherProvider = new OpenWeatherMapProvider();

        for (Location iteredLocation : locationList) {
            for (Instant iteredInstant : instantList) {
                Weather weather = weatherProvider.getWeather(iteredLocation, iteredInstant);

                if (weather != null) {
                    System.out.println("Weather for " + iteredLocation.getName() + " at " + iteredInstant + ":");
                    System.out.println(weather);
                    System.out.println("\n");
                } else {
                    System.out.println("No weather data found for " + iteredLocation.getName() + " at " + iteredInstant);
                }
                weatherArrayList.add(weather);
            }
        }
        return weatherArrayList;
    }

    public static void loadCall(ArrayList<Instant> instantList, List<Location> locationList){
        WeatherStore weatherStore = new SqliteWeatherStore();
        for (Location iteredLocation : locationList) {
            for (Instant iteredInstant : instantList) {
                weatherStore.load(iteredLocation, iteredInstant);

            }
        }
    }

    public static void main(String[] args) {
        WeatherController weatherController = new WeatherController(new OpenWeatherMapProvider());
        weatherController.execute();
    }
}

