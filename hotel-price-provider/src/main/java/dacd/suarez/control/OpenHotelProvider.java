package dacd.suarez.control;

import com.google.gson.*;
import dacd.suarez.model.Booking;
import dacd.suarez.model.Hotel;
import dacd.suarez.model.Rate;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OpenHotelProvider implements HotelProvider{
    @Override
    public Booking getHotel(Hotel hotel, Booking booking){

        Instant now = Instant.now().plusSeconds(24 * 60 * 60);
        Instant endDate = now.plusSeconds(5 * 24 * 60 * 60);

        String apiUrl = "https://data.xotelo.com/api/rates?hotel_key=" + hotel.getHotel_key() +
                "&chk_in=" + DateTimeFormatter.ofPattern("yyyyMMdd").format(now.atZone(ZoneOffset.UTC)) +
                "&chk_out=" + DateTimeFormatter.ofPattern("yyyyMMdd").format(endDate.atZone(ZoneOffset.UTC));

        try {
            JsonObject result = JsonParser.parseString(Jsoup.connect(apiUrl).ignoreContentType(true).execute().body()).getAsJsonObject();
            booking.setCheck_in(result.getAsJsonObject("result").get("chk_in").getAsString());
            booking.setCheck_out(result.getAsJsonObject("result").get("chk_out").getAsString());

            JsonArray ratesArray = result.getAsJsonObject("result").getAsJsonArray("rates");
            ArrayList<Rate> rates = new ArrayList<>();

            for (JsonElement rate : ratesArray) {
                JsonObject rateObject = rate.getAsJsonObject();

                String code = rateObject.getAsJsonPrimitive("code").getAsString();
                String name = rateObject.getAsJsonPrimitive("name").getAsString();
                int rateName = rateObject.getAsJsonPrimitive("rate").getAsInt();
                int tax = rateObject.getAsJsonPrimitive("tax").getAsInt();

                Rate rate1 = new Rate(code,name,rateName,tax);

                rates.add(rate1);

            }

            if (ratesArray != null && ratesArray.size() > 0) {
                booking.setRates(rates);
                hotel.setLocation(hotel.getLocation());
                hotel.setHotel_name(hotel.getHotel_name());
                return booking;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Booking getHotelDetails(Hotel hotel){
        Booking booking = new Booking("default_check_in", "default_check_out", new ArrayList<>(), hotel);

        hotel.setHotel_name(hotel.getHotel_name());
        hotel.setLocation(hotel.getLocation());

        booking.setRates(new ArrayList<>());


        try{
            return getHotel(hotel, booking);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
