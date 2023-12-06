package dacd.suarez;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import dacd.suarez.model.Weather;

import javax.jms.Topic;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class MessageSaver implements Listener {

    private static final String EVENT_STORE_DIRECTORY = "eventstore";

    @Override
    public void consume(String message) {
        try {
            Weather weather = parseWeather(message);
            saveToEventStore(weather);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error processing event: " + e.getMessage());
        }
    }

    private Weather parseWeather(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, (JsonDeserializer<Instant>) (jsonElement, type, jsonDeserializationContext) ->
                        Instant.ofEpochSecond(jsonElement.getAsLong()))
                .create();

        return gson.fromJson(json, Weather.class);
    }


    private void saveToEventStore(Weather weather) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String dateDirectoryName = dateFormat.format(Date.from(weather.getPredictiontime()));

            String eventStorePath = EVENT_STORE_DIRECTORY + "/prediction.Weather" + "/" + weather.getSs() + "/" + dateDirectoryName;
            createDirectoryIfNotExists(eventStorePath);

            String fileName = dateDirectoryName + ".events";
            String filePath = eventStorePath + "/" + fileName;

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(weather.toString()); // Adjust this line based on your event structure
                writer.newLine();
                System.out.println("Event saved to Event Store: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error saving event to Event Store: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error processing event: " + e.getMessage());
        }
    }

    private void createDirectoryIfNotExists(String directoryPath) {
        try {
            if (!Files.exists(Path.of(directoryPath))) {
                Files.createDirectories(Path.of(directoryPath));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error creating directory: " + e.getMessage());
        }
    }
}
