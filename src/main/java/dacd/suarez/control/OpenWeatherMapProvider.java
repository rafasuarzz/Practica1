package dacd.suarez.control;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dacd.suarez.model.Location;
import dacd.suarez.model.Weather;
import org.jsoup.Jsoup;

;
import java.io.IOException;

import java.time.Instant;


public class OpenWeatherMapProvider implements WeatherProvider {
    private static final String API_KEY = "724ea6ff6c24bf793a2f723008965e34"; // Reemplaza "your-api-key" con tu propia clave de API de OpenWeatherMap

    @Override
    public Weather getWeather(Location location, Instant instant) {
        Weather weatherObject = null;
        try {
            String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat="+ location.getLat()+
                    "&lon="+location.getLon()+ "&appid=" + API_KEY + "&units=metric";

            String jsonString = Jsoup.connect(apiUrl).ignoreContentType(true).execute().body();
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(jsonString, JsonObject.class);
            JsonArray weatherJsonArray = json.getAsJsonObject().getAsJsonArray("list");

            for (JsonElement weather : weatherJsonArray) {
                JsonObject weatherJsonObject = weather.getAsJsonObject();

                JsonObject main = weatherJsonObject.getAsJsonObject("main");
                JsonObject clouds = weatherJsonObject.getAsJsonObject("clouds");
                JsonObject wind = weatherJsonObject.getAsJsonObject("wind");


                double temperature = main.get("temp").getAsDouble();
                int humidity = main.get("humidity").getAsInt();
                double pop = weatherJsonObject.get("pop").getAsDouble();
                int dt = weatherJsonObject.get("dt").getAsInt();
                int all = clouds.get("all").getAsInt();
                double windSpeed = wind.get("speed").getAsDouble();


                long unixTimestamp = dt;
                Instant weatherInstant = Instant.ofEpochSecond(unixTimestamp);
                if (weatherInstant.equals(instant)) {
                    weatherObject = new Weather(temperature, humidity, all, windSpeed, pop, weatherInstant);
                    break;
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return weatherObject;
    }
}