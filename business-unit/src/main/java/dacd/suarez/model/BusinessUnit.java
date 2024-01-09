package dacd.suarez.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dacd.suarez.model.Booking;
import dacd.suarez.model.Hotel;
import dacd.suarez.model.Location;
import dacd.suarez.model.WeatherData;
import org.apache.activemq.selector.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BusinessUnit {
    private static List<WeatherData> weatherDataList = new ArrayList<>();
    private static List<Booking> bookingDataList = new ArrayList<>();

    public static List<WeatherData> processWeatherDatamart(String filePath) {
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Parsea cada línea como un objeto JSON
                JsonObject json = jsonParser.parse(line).getAsJsonObject();

                if (json.has("temperature")) {
                    // Es un objeto de datos meteorológicos
                    WeatherData weatherData = gson.fromJson(json, WeatherData.class);
                    weatherDataList.add(weatherData);
                }
            }return weatherDataList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static List<Booking> processHotelDatamart(String filePath){
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                    // Parsea cada línea como un objeto JSON
                JsonObject json = jsonParser.parse(line).getAsJsonObject();

                if (json.has("hotel")) {
                        // Es un objeto de datos del hotel
                    Booking bookingData = gson.fromJson(json, Booking.class);
                    bookingDataList.add(bookingData);
                    System.out.println(bookingData.getRates());
                }
            }return bookingDataList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<WeatherData> filterWeatherDataByLocation(List<WeatherData> weatherDataList, Location location) {
        List<WeatherData> locationWeatherData = new ArrayList<>();

        for (WeatherData weatherData : weatherDataList) {
            // Asume que hay un método getLocation() en WeatherData para obtener la localización
            String dataLocation = weatherData.getLocation().getName();

            // Verifica si la localización del WeatherData coincide con la localización deseada
            if (dataLocation != null && dataLocation.equals(location.getName())) {
                locationWeatherData.add(weatherData);
            }
        }

        return locationWeatherData;
    }

    public List<Hotel> sortHotelsByRate(List<Hotel> hotelList) {
        // Ordena los hoteles por tasa (rate)
       // Collections.sort(hotelList, Comparator.comparing(Booking::getRates));
        // Devuelve la lista ordenada
        return hotelList;
    }

    //

    public static double parseDoubleValue(String token) {
        String[] parts = token.split(":");
        if (parts.length > 1 && !parts[1].trim().isEmpty()) {
            try {
                return Double.parseDouble(parts[1].trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0.0;
    }



    private static String extractDateFromEvent(String dateSubstring) {
        int startIndex = dateSubstring.indexOf(":\"") + 2;
        int endIndex = dateSubstring.indexOf("\",");
        if (startIndex != -1 && endIndex != -1) {
            return dateSubstring.substring(startIndex, endIndex);
        }
        return "";
    }

    private static String extractHotelNameFromEvent(String event) {
        int hotelNameIndex = event.indexOf("\"name\":\"");
        if (hotelNameIndex != -1) {
            int endIndex = event.indexOf("\",", hotelNameIndex);
            return event.substring(hotelNameIndex + "\"name\":\"".length(), endIndex);
        }
        return "";
    }

    private static double extractRateFromHotelEntry(String hotelEntry) {
        String numericPart = hotelEntry.replaceAll("[^\\d.]", "");
        if (!numericPart.isEmpty()) {
            numericPart = numericPart.replaceFirst("\\.(?=.*\\.)", "");
            return Double.parseDouble(numericPart);
        }
        return 0.0;
    }


}
