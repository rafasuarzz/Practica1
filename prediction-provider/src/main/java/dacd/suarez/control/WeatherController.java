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
        Location elHierro = new Location("Valverde", 28.46824, -16.25462);
        Location laPalma = new Location("Santa Cruz de la Palma", 28.68351, -17.7642);
        Location laGomera = new Location("San Sebastián de la Gomera", 28.09163, -17.11331);
        Location tenerife = new Location("Santa Cruz de Tenerife", 27.80628, -17.91578);
        Location granCanaria = new Location("Las Palmas de Gran_Canaria", 28.09973, -15.41343);
        Location fuerteventura = new Location("Puerto del Rosario", 28.50038, -13.86272);
        Location lanzarote = new Location("Arrecife", 28.96302, -13.54769);
        Location laGraciosa = new Location("Caleta de Sebo", 29.23147, -13.50341);

        locationList = List.of(elHierro, laPalma, laGomera, tenerife, granCanaria,
                fuerteventura, lanzarote, laGraciosa);

        List<Instant> instantList = createInstantList();

        getAndPrintWeatherData(locationList, instantList);
        loadWeatherDataToDatabase(weatherProvider, locationList, instantList);
    }

    private List<Instant> createInstantList() {
        List<Instant> instants = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            instants.add(Instant.now().plus(i, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).plus(12, ChronoUnit.HOURS));
        }
        return instants;
    }

    private void getAndPrintWeatherData(List<Location> locationList, List<Instant> instantList){
        for (Location location : locationList) {
            for (Instant instant : instantList) {
                Weather weather = weatherProvider.getWeather(location, instant);

                if (weather != null) {
                    System.out.println("Weather for " + location.getName() + " at " + instant + ":");
                } else {
                    System.out.println("No weather data found for " + location.getName() + " at " + instant);
                }
            }
        }
    }

    private void loadWeatherDataToDatabase(WeatherProvider weatherProvider, List<Location> locationList, List<Instant> instantList) {
        for (Location location : locationList) {
            for (Instant instant : instantList) {
                Weather weather = weatherProvider.getWeather(location, instant);
                if (weather != null) {
                    weatherStore.save(weather);
                } else {
                    System.out.println("No weather data found for " + location.getName() + " at " + instant);
                }
            }
        }
    }
}
