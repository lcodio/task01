package ru.it.pilots_flights;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pilot {

    private String name;
    private Map<String, Integer> flightTimeByMonth;

    private List<String> overtimeMonths = new ArrayList<>(); // Список месяцев, где было превышение лимита
    private List<String> overtimeWeeks = new ArrayList<>();  // Список дат недель, где было превышение лимита
    private List<String> overtimeDays = new ArrayList<>();   // Список дат дней, где было превышение лимита

    private static final int MAX_MONTHLY_MINUTES = 4800;
    private static final int MAX_WEEKLY_MINUTES = 2160;
    private static final int MAX_DAILY_MINUTES = 480;

    public Pilot() {
    }

    public Pilot(String crewMember) {
        this.name = crewMember;
        this.flightTimeByMonth = new HashMap<>();
    }

    public Pilot(Map<String, Integer> flightTimeByMonth, String name, List<String> overtimeDays, List<String> overtimeMonths, List<String> overtimeWeeks) {
        this.flightTimeByMonth = flightTimeByMonth;
        this.name = name;
        this.overtimeDays = overtimeDays;
        this.overtimeMonths = overtimeMonths;
        this.overtimeWeeks = overtimeWeeks;
    }

    public Map<String, Integer> getFlightTimeByMonth() {
        return flightTimeByMonth;
    }

    public void setFlightTimeByMonth(Map<String, Integer> flightTimeByMonth) {
        this.flightTimeByMonth = flightTimeByMonth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getOvertimeDays() {
        return overtimeDays;
    }

    public void setOvertimeDays(List<String> overtimeDays) {
        this.overtimeDays = overtimeDays;
    }

    public List<String> getOvertimeMonths() {
        return overtimeMonths;
    }

    public void setOvertimeMonths(List<String> overtimeMonths) {
        this.overtimeMonths = overtimeMonths;
    }

    public List<String> getOvertimeWeeks() {
        return overtimeWeeks;
    }

    public void setOvertimeWeeks(List<String> overtimeWeeks) {
        this.overtimeWeeks = overtimeWeeks;
    }

    public void addFlightTime(String date, int flightDuration) {
        flightTimeByMonth.put(date, flightTimeByMonth.getOrDefault(date, 0) + flightDuration);
    }


    public void checkFlightTimeLimits(String month, LocalDate flightDate, long flightDuration) {
        // Проверка месячного лимита
        int monthlyFlightTime = flightTimeByMonth.getOrDefault(month, 0) + (int) flightDuration;

        if (overtimeMonths.isEmpty()) {
            if (monthlyFlightTime > MAX_MONTHLY_MINUTES) {
                overtimeMonths.add(month);
            } else {
                for (int i = 0; i < overtimeMonths.size(); i++) {
                    if (!overtimeMonths.get(i).equalsIgnoreCase(month) & monthlyFlightTime > MAX_MONTHLY_MINUTES) {
                        overtimeMonths.add(month);
                    }

                }

                //    overtimeMonths.add(flightDate.getMonth().toString());

     /*       if (overtimeMonths.isEmpty()) {
                overtimeMonths.add(month);
            } else {
                for (int i = 0; i < overtimeMonths.size(); i++) {
                    if (!overtimeMonths.get(i).equalsIgnoreCase(month)) {
                        overtimeMonths.add(month);
                    }
                }
            }*/
            }

        }
        System.out.println(overtimeMonths);

       // System.out.println("!" + flightTimeByMonth);


        // Проверка недельного лимита
        if (

                isOvertimeWeek(flightDate, flightDuration)) {
            overtimeWeeks.add(flightDate.getYear() + " " + flightDate.getMonth().toString()); //
        }

        // Проверка дневного лимита
        if (flightDuration > MAX_DAILY_MINUTES) {
            overtimeDays.add(flightDate.toString());
        }
    }

    private boolean isOvertimeWeek(LocalDate flightDate, long flightDuration) {
        // Вычисляем номер недели
        int weekOfYear = flightDate.getDayOfYear() / 7 + 1;
        //    System.out.println(weekOfYear);

        // Считаем суммарное время полета на этой неделе
        int weeklyFlightTime = 0;
        for (LocalDate date = flightDate.minusDays(flightDate.getDayOfWeek().getValue() - 1);
             date.isBefore(flightDate.plusDays(7 - flightDate.getDayOfWeek().getValue()));
             date = date.plusDays(1)) {
            weeklyFlightTime += flightTimeByMonth.getOrDefault(date.getMonth().toString(), 0);
        }

        // Проверяем, превышает ли суммарное время недельный лимит
        return weeklyFlightTime + (int) flightDuration > MAX_WEEKLY_MINUTES;
    }

}

