package dacd.suarez.control;

import dacd.suarez.model.Location;
import dacd.suarez.model.Weather;

public interface WeatherProvider {
    Weather getWeather(Location location, Weather weather);
}
