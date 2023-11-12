package dacd.suarez.control;

import dacd.suarez.model.Location;
import dacd.suarez.model.Weather;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Instant;

public class SqliteWeatherStore implements WeatherStore {
    private String file;

    public SqliteWeatherStore(String file) {
        this.file = file;
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + file)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void save(Weather weather) {
        // Implementación para guardar datos en SQLite
    }

    @Override
    public Weather load(Location location, Instant dt) {
        // Implementación para cargar datos desde SQLite
        return null; // Retorno de ejemplo
    }

    // Otros métodos, si es necesario
}

