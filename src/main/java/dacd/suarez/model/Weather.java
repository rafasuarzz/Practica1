package dacd.suarez.model;

import java.time.Instant;
import java.util.ArrayList;

public class Weather {
    private final double temp;
    private final double humidity;
    private final int all;
    private final double speed;
    private final double pop;
    private final Instant dt;


    public Weather(double temp, double humidity, int all, double speed, double pop, Instant dt) {
        this.temp = temp;
        this.humidity = humidity;
        this.all = all;
        this.speed = speed;
        this.pop = pop;
        this.dt = dt;
    }


    public double getTemp() {
        return temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public int getAll() {
        return all;
    }

    public double getSpeed() {
        return speed;
    }

    public double getPop() {
        return pop;
    }

    public Instant getDt() {
        return dt;
    }
}
