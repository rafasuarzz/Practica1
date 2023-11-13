package dacd.suarez.control;

import dacd.suarez.model.Location;
import dacd.suarez.model.Weather;

import java.time.Instant;

public interface WeatherStore {
    void save(Weather weather);
    void load(Location location, Instant instant);
}
