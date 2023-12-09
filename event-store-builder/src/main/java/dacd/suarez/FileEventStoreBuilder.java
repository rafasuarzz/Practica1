package dacd.suarez;

import com.google.gson.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class FileEventStoreBuilder implements Listener {

    private final String eventStoreDirectory ;

    public FileEventStoreBuilder(String eventStoreDirectory) {
        this.eventStoreDirectory = eventStoreDirectory;
    }

    @Override
    public void consume(String message) {
        try {
            System.out.println("Received Weather: " + message);
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(message, JsonObject.class);

            String  ssValue = jsonObject.get("ss").getAsString();
            String tsValue = jsonObject.get("predictionTime").getAsString();

            long timestamp = Long.parseLong(tsValue);
            Instant instant = Instant.ofEpochSecond(timestamp);
            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String formattedDate = dateTime.format(formatter);

            String eventStorePath = eventStoreDirectory + "/prediction.Weather/" + ssValue;
            Path directoryPath = Path.of(eventStorePath);

            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
                System.out.println("Directory created: " + directoryPath);
            }

            String fileName = formattedDate + ".events";
            String filePath = eventStorePath + "/" + fileName;

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(message + "\n");
                System.out.println("Message saved in: " + filePath);
            } catch (IOException e) {
                throw new RuntimeException("Error saving event to Event Store: " + e.getMessage(), e);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error processing event: " + e.getMessage(), e);
        }
    }

}
