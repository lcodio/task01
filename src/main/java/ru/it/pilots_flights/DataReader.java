package ru.it.pilots_flights;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataReader {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static List<Flight> readFlights(String filename) throws IOException {
        List<Flight> flights = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Flight flight = new Flight();
                flight.setAircraftType(parts[0]);
                flight.setAircraftNumber(parts[1]);
                flight.setDepartureTime(LocalDateTime.parse(parts[2], DATE_TIME_FORMATTER));
                flight.setArrivalTime(LocalDateTime.parse(parts[3], DATE_TIME_FORMATTER));
                flight.setDepartureAirport(parts[4]);
                flight.setArrivalAirport(parts[5]);
                flight.setCrewMembers(List.of(parts[6].split(";")));
                flights.add(flight);
            }
        }
        return flights;
    }
}
