package dacd.suarez.control;

import dacd.suarez.model.Location;

import java.util.ArrayList;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        // Configuración de proveedor y almacenamiento
        WeatherProvider provider = new OpenWeatherMapProvider("724ea6ff6c24bf793a2f723008965e34", "https://api.openweathermap.org/data/2.5/forecast");
        WeatherStore store = new SqliteWeatherStore("/Users/rafasuarzz/documentos/weather_data.db");

        // Ubicaciones a obtener datos climáticos
        List<Location> locations = new ArrayList<>();
        Location location1 = new Location("Las Palmas de Gran Canaria", 28.09973, -15.41343);
        Location location2 = new Location("Santa Cruz de Tenerife", 28.4682400, -16.2546200);
        Location location3 = new Location("Valverde", 27.7985700, -17.9348300);
        Location location4 = new Location("Puerto del Rosario", 28.5104500, -13.9559900);
        Location location5 = new Location("Arrecife", 28.9630200, -13.5476900);
        Location location6 = new Location("San Sebastian de la Gomera", 28.0916300, -17.1133100);
        Location location7 = new Location("Santa Cruz de la Palma", 28.6835100, -17.7642100);
        Location location8 = new Location("Caleta de Sebo", 29.25, -13.5);
        // Agrega más ubicaciones si es necesario
        locations.add(location1);
        locations.add(location2);
        locations.add(location3);
        locations.add(location4);
        locations.add(location5);
        locations.add(location6);
        locations.add(location7);
        locations.add(location8);

        // Configuración de días a obtener datos climáticos
        int days = 5;

        // Ejecución para obtener y almacenar datos climáticos
        WeatherController weatherController = new WeatherController(locations, days, provider, store);
        weatherController.execute();
    }
}

