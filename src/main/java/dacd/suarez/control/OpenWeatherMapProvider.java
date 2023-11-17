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
    private static String API_KEY;
    private static String URL;

    public OpenWeatherMapProvider(String API_KEY, String URL) {
        this.API_KEY = API_KEY;
        this.URL = URL;
    }

    public static String getAPI_KEY() {
        return API_KEY;
    }

    public static String getURL() {
        return URL;
    }

    @Override
    public Weather getWeather(Location location, Instant instant) {
        try {
            String apiUrl = URL + "?lat="+ location.getLat()+
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
                    return new Weather(temperature, humidity, all, windSpeed, pop, weatherInstant, location);
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}