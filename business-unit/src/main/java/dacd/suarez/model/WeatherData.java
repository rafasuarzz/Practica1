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
    private Map<String, Object> attributes;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getRainProbability() {
        return rainProbability;
    }

    public void setRainProbability(double rainProbability) {
        this.rainProbability = rainProbability;
    }

    public long getPredictionTime() {
        return predictionTime;
    }

    public void setPredictionTime(long predictionTime) {
        this.predictionTime = predictionTime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    public boolean containsAttribute(String attribute) {
        return attributes.containsKey(attribute);
    }
}
