package ru.it.pilots_flights;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class OutputGenerator {
    public static void generateOutput(Map<String, Pilot> pilots, String filename) throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(pilots, writer);
        }
    }
}
