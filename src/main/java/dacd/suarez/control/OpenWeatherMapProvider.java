package dacd.suarez.control;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dacd.suarez.model.Location;
import dacd.suarez.model.Weather;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.time.Instant;

public class OpenWeatherMapProvider implements WeatherProvider {
    private String apiKey;
    private String templateUrl;

    public OpenWeatherMapProvider(String apiKey, String templateUrl) {
        this.apiKey = apiKey;
        this.templateUrl = templateUrl;
    }

    // Implementación del método getWeather de la interfaz WeatherProvider
    public Weather getWeather(Location location, Instant dt) {
        Weather weather = null;

        try {
            String apiUrl = templateUrl + "?lat=" + location.getLat() + "&lon=" + location.getLon() + "&appid=" + apiKey;
            String jsonString = Jsoup.connect(apiUrl).ignoreContentType(true).execute().body();

            Gson gson = new Gson();
            JsonObject weatherData = gson.fromJson(jsonString, JsonObject.class);
            JsonArray list = weatherData.getAsJsonArray("list");

            for (JsonElement element : list) {
                JsonObject dataPoint = element.getAsJsonObject();
                Instant dataPointTime = Instant.ofEpochSecond(dataPoint.get("dt").getAsLong());

                if (dataPointTime.equals(dt)) {
                    JsonObject dataObject = dataPoint.getAsJsonObject("main");
                    double temp = dataObject.get("temp").getAsDouble();
                    int humidity = dataObject.get("humidity").getAsInt();
                    int cloud = dataObject.getAsJsonObject("clouds").get("all").getAsInt();
                    double speed = dataObject.getAsJsonObject("wind").get("speed").getAsDouble();
                    double pop = dataObject.get("pop").getAsDouble();
                    long fo = dataObject.get("dt").getAsLong();
                    long unixTimestamp = fo;
                    Instant weatherInstant = Instant.ofEpochSecond(unixTimestamp);
                    // Accede a otros datos relevantes y actualiza el objeto Weather
                    weather = new Weather(temp, humidity, cloud, speed, pop, weatherInstant, location);
                }
            }
            if (weather == null) {
                // Si no se encontraron datos, devuelve un objeto Weather con valores predeterminados
                weather = new Weather(0.0, 0, 0, 0.0, 0.0, Instant.now(), location);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return weather;
    }
}