package ru.it.pilots_flights;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightTimeProcessor {

    public static Map<String, Pilot> calculateFlightTime(List<Flight> flights) {

        Map<String, Pilot> pilots = new HashMap<>();

        for (Flight flight : flights) {

            for (String crewMember : flight.getCrewMembers()) {

                Pilot pilot = pilots.getOrDefault(crewMember, new Pilot(crewMember));
                LocalTime departureTime = flight.getDepartureTime().toLocalTime();
                LocalTime arrivalTime = flight.getArrivalTime().toLocalTime();

                long flightDuration = ChronoUnit.MINUTES.between(departureTime, arrivalTime);

                LocalDate flightDate = flight.getDepartureTime().toLocalDate();
             //   String month = flightDate.getMonth().toString();
                String yearMonth = flightDate.getYear() + " " + flightDate.getMonth().toString();

                pilot.addFlightTime(yearMonth, (int) flightDuration);

                // Проверка ограничений по рабочему времени
                pilot.checkFlightTimeLimits(yearMonth, flightDate, flightDuration);
                pilots.put(crewMember, pilot);
            }
        }
        return pilots;
    }
}