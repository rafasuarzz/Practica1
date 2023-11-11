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
import java.util.ArrayList;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        try {
            String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=28.09973&lon=-15.41343&appid=724ea6ff6c24bf793a2f723008965e34&units=metric";
            String jsonString = Jsoup.connect(apiUrl).ignoreContentType(true).execute().body();

            Gson gson = new Gson();
            JsonObject weatherData = gson.fromJson(jsonString, JsonObject.class);
            JsonArray list = weatherData.getAsJsonObject().getAsJsonArray("list");

            List<Weather> weatherList = new ArrayList<>();



            for (JsonElement result : list) {
                JsonObject dataObject = result.getAsJsonObject();
                double temp = dataObject.getAsJsonObject("main").get("temp").getAsDouble();
                int humidity = dataObject.getAsJsonObject("main").get("humidity").getAsInt();
                int cloud = dataObject.getAsJsonObject("clouds").get("all").getAsInt();
                double speed = dataObject.getAsJsonObject("wind").get("speed").getAsDouble();
                double pop = dataObject.get("pop").getAsDouble();
                long dt = dataObject.get("dt").getAsLong();
                long unixTimestamp = dt;
                Instant weatherInstant = Instant.ofEpochSecond(unixTimestamp);



                Weather weatherObject = new Weather(temp, humidity, cloud, speed, pop, weatherInstant);
                weatherList.add(weatherObject);
            }

            for (Weather weatherIter : weatherList) {
                System.out.println("Wind: " + weatherIter.getSpeed() + " m/s" + ", Temperature: " + weatherIter.getTemp() + "º" + ", Humidity: " + weatherIter.getHumidity()
                        + "%" + ", Clouds: " + weatherIter.getAll() + ", Precipitation: " + weatherIter.getPop() + "%" + ", Date: " + weatherIter.getDt() );
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}