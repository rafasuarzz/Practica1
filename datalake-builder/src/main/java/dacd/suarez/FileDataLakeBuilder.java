package dacd.suarez;

import com.google.gson.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class FileDataLakeBuilder implements Listener {
    private String rootDirectory;
    public FileDataLakeBuilder(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    @Override
    public void consume(String message, String topicName) {
        String dataLakePath = rootDirectory + File.separator + "datalake" + File.separator + "eventstore";


        System.out.println("Received Information: " + message);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);

        String  ssValue = jsonObject.get("ss").getAsString();
        String tsValue = jsonObject.get("ts").getAsString();

        long timestamp = Long.parseLong(tsValue);
        Instant instant = Instant.ofEpochSecond(timestamp);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = dateTime.format(formatter);

        String directoryPath = dataLakePath + File.separator + topicName + File.separator + ssValue;
        createDirectory(directoryPath);

        String filePath = directoryPath + File.separator + formattedDate + ".events";
        writeMessage(filePath, message);
    }

    private void createDirectory(String directoryPath){
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
            System.out.println("Directory created");
        }
    }

    private void writeMessage(String filePath, String message){
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(message + "\n");
            System.out.println("Message appended to file: " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file", e);
        }
    }

}
