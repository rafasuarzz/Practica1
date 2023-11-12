package dacd.suarez.control;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dacd.suarez.model.Location;
import dacd.suarez.model.Weather;
import org.jsoup.Jsoup;

import java.io.IOException;

public class OpenWeatherMapProvider implements WeatherProvider {
    private String apiKey;
    private String templateUrl;

    public OpenWeatherMapProvider(String apiKey, String templateUrl) {
        this.apiKey = apiKey;
        this.templateUrl = templateUrl;
    }

    // Implementación del método getWeather de la interfaz WeatherProvider
    public Weather getWeather(Location location, Weather weather) {
        try {
            String apiUrl = templateUrl + "?lat=" + location.getLat() + "&lon=" + location.getLon() + "&appid=" + apiKey;
            String jsonString = Jsoup.connect(apiUrl).ignoreContentType(true).execute().body();

            Gson gson = new Gson();
            JsonObject weatherData = gson.fromJson(jsonString, JsonObject.class);
            JsonArray list = weatherData.getAsJsonArray("list");

            // Procesar los datos y actualizar el objeto Weather

        } catch (IOException e) {
            e.printStackTrace(); // Manejo apropiado de la excepción
        }

        return weather; // Devolver el objeto Weather actualizado
    }
}
