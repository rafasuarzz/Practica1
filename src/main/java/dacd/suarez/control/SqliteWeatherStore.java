package dacd.suarez.control;

import dacd.suarez.model.Location;
import dacd.suarez.model.Weather;

import java.awt.*;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;

public class SqliteWeatherStore implements WeatherStore {

    private static final String DB_URL = "jdbc:sqlite:weather_data.db";

    public void createTable(String islandName) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS \"" + islandName + " \"(" +
                "location_name TEXT," +
                "temp REAL," +
                "humidity INTEGER," +
                "cloud INTEGER," +
                "speed REAL," +
                "pop REAL," +
                "dt TEXT," +
                "location_lat REAL," +
                "location_lon REAL" +
                ")";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement statement = conn.createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void save(Weather weather) {
        String islandName = weather.getLocation().getName();
        createTable(islandName);

        try {
            Connection connection = DriverManager.getConnection(DB_URL);
            String insertWeatherSQL = "INSERT OR REPLACE INTO \"" + islandName + " \"(location_name, temp, humidity, cloud, speed, pop, dt, location_lat, location_lon) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertWeatherSQL);

            preparedStatement.setString(1, weather.getLocation().getName());
            preparedStatement.setDouble(2, weather.getTemp());
            preparedStatement.setInt(3, weather.getHumidity());
            preparedStatement.setInt(4, weather.getAll());
            preparedStatement.setDouble(5, weather.getSpeed());
            preparedStatement.setDouble(6, weather.getPop());
            preparedStatement.setString(7, weather.getInstant().toString());
            preparedStatement.setDouble(8, weather.getLocation().getLat());
            preparedStatement.setDouble(9, weather.getLocation().getLon());

            preparedStatement.executeUpdate();

            System.out.println("Weather data saved successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void load(Location location, Instant instant) {
        WeatherProvider weatherProvider = new OpenWeatherMapProvider(OpenWeatherMapProvider.getAPI_KEY(), OpenWeatherMapProvider.getURL());
        Weather weather = weatherProvider.getWeather(location, instant);


        if (weather != null) {
            save(weather);
        } else {
            System.out.println("No weather data found for " + location.getName() + " at " + instant);
        }
    }


}