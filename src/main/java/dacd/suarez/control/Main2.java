package dacd.suarez.control;

import dacd.suarez.model.Location;

import java.util.ArrayList;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        WeatherController weatherController = new WeatherController(new OpenWeatherMapProvider());
        weatherController.execute();
    }

}

