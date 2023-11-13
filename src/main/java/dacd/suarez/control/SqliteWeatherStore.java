package dacd.suarez.control;

import dacd.suarez.model.Location;
import dacd.suarez.model.Weather;

import java.sql.*;
import java.time.Instant;

public class SqliteWeatherStore implements WeatherStore {
    private String file;

    public SqliteWeatherStore(String file) {
        this.file = file;
        createTable();
    }

    private void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS weather_data (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "temp REAL," +
                "humidity INTEGER," +
                "cloud INTEGER," +
                "speed REAL," +
                "pop REAL," +
                "location_name TEXT," +
                "location_lat REAL," +
                "location_lon REAL" +
                ")";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + file);
             Statement statement = conn.createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Weather weather) {
        String insertSQL = "INSERT INTO weather_data(temp, humidity, cloud, speed, pop, location_name, location_lat, location_lon) VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + file);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setDouble(1, weather.getTemp());
            pstmt.setInt(2, weather.getHumidity());
            pstmt.setInt(3, weather.getAll());
            pstmt.setDouble(4, weather.getSpeed());
            pstmt.setDouble(5, weather.getPop());
            pstmt.setString(6, weather.getLocation().getName());
            pstmt.setDouble(7, weather.getLocation().getLat());
            pstmt.setDouble(8, weather.getLocation().getLon());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Weather load(Location location, Instant instant) {
        String selectSQL = "SELECT * FROM weather_data WHERE location_name = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + file);
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setString(1, location.getName());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double temp = rs.getDouble("temp");
                int humidity = rs.getInt("humidity");
                int cloud = rs.getInt("cloud");
                double speed = rs.getDouble("speed");
                double pop = rs.getDouble("pop");
                Instant dt = Instant.ofEpochSecond(rs.getLong("dt"));
                String locationName = rs.getString("location_name");
                double locationLat = rs.getDouble("location_lat");
                double locationLon = rs.getDouble("location_lon");

                Location locationData = new Location(locationName, locationLat, locationLon);
                return new Weather(temp, humidity, cloud, speed, pop, dt, locationData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}

