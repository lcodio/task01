import ru.it.DataReader;
import ru.it.Flight;
import ru.it.FlightTimeProcessor;
import ru.it.Pilot;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FlightTimeProcessorTest {
    @Test
    void calculateFlightTime() throws IOException {

        List<Flight> flights = DataReader.readFlights("test_data.csv");
        Map<String, Pilot> pilots = FlightTimeProcessor.calculateFlightTime(flights);
        Pilot pilot = pilots.get("Pilot1");

        assertEquals(120, pilot.getFlightTimeByMonth().get("DECEMBER"));
        assertEquals(true, pilot.isOvertime("DECEMBER"));
        assertEquals(true, pilot.isOvertime("JANUARY"));
        assertEquals(false, pilot.isOvertime("FEBRUARY"));
    }
}
