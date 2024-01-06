package dacd.suarez.model;

import com.google.gson.JsonArray;

import java.time.Instant;

public class Booking {
    private Hotel hotel;
    private JsonArray rates;
    private String check_in;
    private String check_out;
    private final String ss;
    private final Instant ts;
    public Booking(String check_in, String check_out, JsonArray rates, Hotel hotel) {
        this.check_in = check_in;
        this.check_out = check_out;
        this.rates = rates;
        this.ss = "hotel-price-provider";
        this.ts = Instant.now();
        this.hotel = hotel;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public JsonArray getRates() {
        return rates;
    }

    public void setRates(JsonArray rates) {
        this.rates = rates;
    }

    public String getCheck_in() {
        return check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public void setCheck_out(String check_out) {
        this.check_out = check_out;
    }
}
