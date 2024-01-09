package dacd.suarez.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class FileDataMartBuilder implements Listener {

    private final String dataMartPath = "datamart" + File.separator + "eventstore";
    private final String fileName = "all_events";
    private static boolean dataMartEmpty = false;

    @Override
    public void consume(String message, String topicName) {
        System.out.println("Received Information: " + message);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);

        String tsValue = jsonObject.get("ts").getAsString();

        long timestamp = Long.parseLong(tsValue);
        Instant instant = Instant.ofEpochSecond(timestamp);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        if (dateTime.toLocalDate().equals(LocalDate.now())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String formattedDate = dateTime.format(formatter);

            String directoryPath = dataMartPath + File.separator + formattedDate;
            createDirectory(directoryPath);

            if (!dataMartEmpty) {
                clearDataMart();
                dataMartEmpty = true;
            }

            String filePath = directoryPath + File.separator + fileName + ".events";
            writeMessage(filePath, message);
        }
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

    private void clearDataMart() {
        File baseDirectory = new File(dataMartPath);
        File[] subdirectories = baseDirectory.listFiles(File::isDirectory);

        if (subdirectories != null) {
            for (File subdirectory : subdirectories) {
                deleteDirectory(subdirectory);
            }
        }
    }
    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }
}

