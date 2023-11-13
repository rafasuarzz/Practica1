package dacd.suarez.control;

import dacd.suarez.model.Location;
import dacd.suarez.model.Weather;

import java.sql.*;
import java.time.Instant;

public class SqliteWeatherStore implements WeatherStore {

    private static final String DB_URL = "jdbc:sqlite:weather_database1.db";


    @Override
    public void save(Weather weather) {

    }

    @Override
    public void load(Location location, Instant instant) {
        WeatherProvider weatherProvider = new OpenWeatherMapProvider();
        Weather weather = weatherProvider.getWeather(location, instant);
        if (weather != null) {
            try {
                Connection connection = DriverManager.getConnection(DB_URL);
                String createTableSQL = "CREATE TABLE IF NOT EXISTS weather (" +
                        "name TEXT ," +
                        "clouds INTEGER," +
                        "wind REAL," +
                        "temperature REAL," +
                        "humidity INTEGER," +
                        "instant TEXT," +
                        "pop REAL" +
                        ")";
                System.out.println("Tabla creada");
                Statement statement = connection.createStatement();
                statement.executeUpdate(createTableSQL);

                String insertWeatherSQL = "INSERT INTO weather (name, clouds, wind, temperature, humidity, instant, pop) VALUES (?, ?, ?, ?, ?, ?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertWeatherSQL);

                preparedStatement.setString(1, location.getName());
                preparedStatement.setInt(2, weather.getAll());
                preparedStatement.setDouble(3, weather.getSpeed());
                preparedStatement.setDouble(4, weather.getTemp());
                preparedStatement.setInt(5, weather.getHumidity());
                preparedStatement.setString(6, weather.getInstant().toString());
                preparedStatement.setDouble(7, weather.getPop());
                preparedStatement.executeUpdate();

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);

            }catch (NullPointerException exception){
                exception.printStackTrace();

            }
        } else {
            System.out.println("No weather data found for ");
        }

    }
}



