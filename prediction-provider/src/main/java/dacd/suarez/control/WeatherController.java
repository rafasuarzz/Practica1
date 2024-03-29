package dacd.suarez.control;

import dacd.suarez.model.Location;
import dacd.suarez.model.Weather;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class WeatherController {
    private WeatherProvider weatherProvider;
    private WeatherStore weatherStore;
    private int days = 5;
    private List<Location> locationList;

    public WeatherController(WeatherProvider weatherProvider, WeatherStore weatherStore) {
        this.weatherProvider = weatherProvider;
        this.weatherStore = weatherStore;
    }

    public void execute(){
        List<Instant> instantList = createInstantList();
        locationList = createLocationList();

        loadWeatherDataToDatabase(weatherProvider, locationList, instantList);
    }

    private List<Instant> createInstantList() {
        List<Instant> instants = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            instants.add(Instant.now().plus(i, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).plus(12, ChronoUnit.HOURS));
        }
        return instants;
    }
    private List<Location> createLocationList() {
        return List.of( new Location("El Hierro", 28.46824, -16.25462),
        new Location("La Palma", 28.68351, -17.7642),
        new Location("La Gomera", 28.09163, -17.11331),
        new Location("Tenerife", 27.80628, -17.91578),
        new Location("Gran Canaria", 28.09973, -15.41343),
        new Location("Fuerteventura", 28.50038, -13.86272),
        new Location("Lanzarote", 28.96302, -13.54769),
        new Location("La Graciosa", 29.23147, -13.50341));
    }

    private void loadWeatherDataToDatabase(WeatherProvider weatherProvider, List<Location> locationList, List<Instant> instantList) {
        for (Location location : locationList) {
            for (Instant instant : instantList) {
                Weather weather = weatherProvider.getWeather(location, instant);
                if (weather != null) {
                    System.out.println("Weather for " + location.getName() + " at " + instant + ":");
                    weatherStore.save(weather);
                } else {
                    System.out.println("No weather data found for " + location.getName() + " at " + instant);
                }
            }
        }
    }
}
