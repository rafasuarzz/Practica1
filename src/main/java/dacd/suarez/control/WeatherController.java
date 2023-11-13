package dacd.suarez.control;

import dacd.suarez.model.Location;
import dacd.suarez.model.Weather;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class WeatherController {
    private List<Location> locations;
    private int days;
    private WeatherProvider weatherProvider;
    private WeatherStore weatherStore;

    // Constructor
    public WeatherController(List<Location> locations, int days, WeatherProvider weatherProvider, WeatherStore weatherStore) {
        this.locations = locations;
        this.days = days;
        this.weatherProvider = weatherProvider;
        this.weatherStore = weatherStore;
    }

    // Método para ejecutar la obtención de datos climáticos
    public void execute() {
        Instant now = Instant.now();

        for (Location location : locations) {
            for (int i = 0; i < days; i++) {
                Instant currentTime = now.plus(i, ChronoUnit.DAYS);

                // Obtener datos climáticos para la ubicación y tiempo actual
                Weather weather = weatherProvider.getWeather(location, currentTime);

                // Guardar los datos climáticos en el almacén
                weatherStore.save(weather);
            }
        }
    }
}
