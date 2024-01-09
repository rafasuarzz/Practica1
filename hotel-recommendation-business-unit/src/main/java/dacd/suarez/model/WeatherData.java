package dacd.suarez.model;

import java.util.Map;

public class WeatherData {
    private double temperature;
    private int humidity;
    private int clouds;
    private double windSpeed;
    private double rainProbability;
    private long predictionTime;
    private Location location;

    public double getTemperature() {
        return temperature;
    }


    public int getHumidity() {
        return humidity;
    }


    public int getClouds() {
        return clouds;
    }


    public double getWindSpeed() {
        return windSpeed;
    }


    public double getRainProbability() {
        return rainProbability;
    }


    public long getPredictionTime() {
        return predictionTime;
    }


    public Location getLocation() {
        return location;
    }



}
