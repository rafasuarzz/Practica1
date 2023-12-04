package dacd.suarez;

import dacd.suarez.model.Weather;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageSaver {

    private static final String EVENT_STORE_DIRECTORY = "eventstore";

    public void saveToEventStore(Weather weather) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String dateDirectoryName = dateFormat.format(Date.from(weather.getInstant()));

            String eventStorePath = EVENT_STORE_DIRECTORY + "/" + JMRWeatherStore.TOPIC_NAME  +"/" + weather.getSs() + "/" + dateDirectoryName;
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
