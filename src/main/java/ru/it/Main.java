package ru.it;

import ru.it.pilots_flights.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        String inputFilename = "src/main/java/ru/it/input_test.csv";
        String outputFilename = "src/main/java/ru/it/output.json";

        List<Flight> flights = DataReader.readFlights(inputFilename);

        Map<String, Pilot> pilots = FlightTimeProcessor.calculateFlightTime(flights);

        OutputGenerator.generateOutput(pilots, outputFilename);
    }
}