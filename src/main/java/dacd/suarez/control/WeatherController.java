package dacd.suarez.control;

import dacd.suarez.model.Location;
import dacd.suarez.model.Weather;

import java.time.Instant;
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
        for (Location location : locations) {
            for (int i = 0; i < days; i++) {
                Weather weather = new Weather(0, 0, 0, 0, 0, Instant.now(), location); // Se crea un objeto Weather con valores predeterminados
                weather = weatherProvider.getWeather(location, weather); // Obtiene datos climáticos utilizando el proveedor

                // Almacenar el objeto Weather en el store, si es necesario
                if (weatherStore != null) {
                    weatherStore.save(weather);
                }
            }
        }
    }
}
