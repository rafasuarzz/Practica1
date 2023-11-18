package dacd.suarez.control;

import dacd.suarez.model.Location;
import dacd.suarez.model.Weather;

import java.sql.*;
import java.time.Instant;
import java.util.List;


public class SqliteWeatherStore implements WeatherStore {

    public SqliteWeatherStore(String db_url) {
        this.db_url = db_url;
        createTable();
    }

    private final String db_url;


    public void createTable() {
        Location elHierro = new Location("Valverde", 28.46824, -16.25462);
        Location laPalma = new Location("Santa Cruz de la Palma", 28.68351, -17.7642);
        Location laGomera = new Location("San Sebastián de la Gomera", 28.09163, -17.11331);
        Location tenerife = new Location("Santa Cruz de Tenerife", 27.80628, -17.91578);
        Location granCanaria = new Location("Las Palmas de Gran_Canaria", 28.09973, -15.41343);
        Location fuerteventura = new Location("Puerto del Rosario", 28.50038, -13.86272);
        Location lanzarote = new Location("Arrecife", 28.96302, -13.54769);
        Location laGraciosa = new Location("Caleta de Sebo", 29.23147, -13.50341);

        List<Location> locationList = List.of(elHierro, laPalma, laGomera, tenerife, granCanaria,
                fuerteventura, lanzarote, laGraciosa);
        try (Connection connection = DriverManager.getConnection(db_url);
             Statement statement = connection.createStatement()) {

            for (Location location : locationList) {
                String tableName = location.getName().toLowerCase().replace(" ", "_");
                String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
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
                statement.executeUpdate(createTableSQL);
                System.out.println("Table created successfully for " + location.getName());
            }

            System.out.println("All tables created successfully.");

        } catch (SQLException e) {
            throw new RuntimeException("Error initializing database", e);
        }


    }


    @Override
    public void save(Weather weather) {
        String islandName = weather.getLocation().getName();
        String tableName = islandName.replaceAll("\\s+", "_").toLowerCase();


        try {
            Connection connection = DriverManager.getConnection(db_url);
            String insertWeatherSQL = "INSERT OR REPLACE INTO " + tableName + " (location_name, temp, humidity, cloud, speed, pop, dt, location_lat, location_lon) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
    public void update(Weather weather) {
        String islandName = weather.getLocation().getName();
        String tableName = islandName.replaceAll("\\s+", "_").toLowerCase();

        try {
            Connection connection = DriverManager.getConnection(db_url);
            String updateWeatherSQL = "UPDATE " + tableName + " SET temp=?, humidity=?, cloud=?, speed=?, pop=? WHERE dt=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateWeatherSQL);

            preparedStatement.setDouble(1, weather.getTemp());
            preparedStatement.setInt(2, weather.getHumidity());
            preparedStatement.setInt(3, weather.getAll());
            preparedStatement.setDouble(4, weather.getSpeed());
            preparedStatement.setDouble(5, weather.getPop());
            preparedStatement.setString(6, weather.getInstant().toString());

            preparedStatement.executeUpdate();

            System.out.println("Weather data updated successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean exists(Location location, Instant dt) {
        String islandName = location.getName();
        String tableName = islandName.replaceAll("\\s+", "_").toLowerCase();
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE dt = ?";
        try (Connection connection = DriverManager.getConnection(db_url);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, dt.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error checking if weather data exists", e);
        }
    }


    @Override
    public void load(Location location, Instant instant) {
        WeatherProvider weatherProvider = new OpenWeatherMapProvider(OpenWeatherMapProvider.getAPI_KEY(), OpenWeatherMapProvider.getURL());
        Weather weather = weatherProvider.getWeather(location, instant);



        if (weather != null) {
            if (exists(location, instant)) {
                update(weather);
                System.out.println("Weather data updated successfully for " + location.getName() + " at " + instant);
            } else {

                save(weather);
                System.out.println("Weather data saved successfully for " + location.getName() + " at " + instant);
            }
        } else {
            System.out.println("No weather data found for " + location.getName() + " at " + instant);
        }
    }


}