package dacd.suarez.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class BusinessUnit {
    private static List<WeatherData> weatherDataList = new ArrayList<>();
    private static List<Booking> bookingDataList = new ArrayList<>();

    public static List<WeatherData> processWeatherDatamart(String filePath) {
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {

                JsonObject json = jsonParser.parse(line).getAsJsonObject();

                if (json.has("temperature")) {

                    WeatherData weatherData = gson.fromJson(json, WeatherData.class);
                    weatherDataList.add(weatherData);
                }
            }
            return weatherDataList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Booking> processHotelDatamart(String filePath) {
        List<Booking> bookingDataList = new ArrayList<>();
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                JsonObject json = jsonParser.parse(line).getAsJsonObject();

                if (json.has("hotel")) {
                    Booking bookingData = gson.fromJson(json, Booking.class);
                    bookingDataList.add(bookingData);
                }
            }
            return bookingDataList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateDataMartFileName() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return "datamart" + File.separator + "eventstore" + File.separator + currentDate.format(formatter)
                + File.separator + "all_events.events";
    }

    public static Map<String, List<WeatherData>> getWeatherData(List<String> selectedConditions, List<WeatherData> weatherDataList) {

        Map<String, Set<Long>> uniquePredictionTimes = new HashMap<>();
        Map<String, List<WeatherData>> islandWeatherData = new HashMap<>();

        for (WeatherData weatherData : weatherDataList) {
            String island = weatherData.getLocation().getName();
            Set<Long> predictionTimes = uniquePredictionTimes.computeIfAbsent(island, k -> new HashSet<>());

            if (predictionTimes.add(weatherData.getPredictionTime())) {
                islandWeatherData.computeIfAbsent(island, k -> new ArrayList<>()).add(weatherData);
            }
        }

        return islandWeatherData;
    }

    public static LocalDateTime convertEpochToDateTime(long epochSeconds) {
        Instant instant = Instant.ofEpochSecond(epochSeconds);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }


    public static List<Booking> sortHotelsByRate(List<Booking> bookingList, String selectedIsland) {
        return bookingList.stream()
                .filter(booking -> booking.getHotel().getLocation().equalsIgnoreCase(selectedIsland))
                .sorted(Comparator.comparingDouble(booking -> getMinRateFromBooking(booking)))
                .collect(Collectors.toList());
    }


    public static double getMinRateFromBooking(Booking booking) {
        return booking.getRates().stream()
                .mapToDouble(Rate::getRate)
                .min()
                .orElse(0.0);
    }
}