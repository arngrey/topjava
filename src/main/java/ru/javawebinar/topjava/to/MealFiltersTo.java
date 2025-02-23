package ru.javawebinar.topjava.to;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MealFiltersTo {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;

    private LocalDate dateFrom;
    private LocalTime timeFrom;
    private LocalDate dateTo;
    private LocalTime timeTo;

    public MealFiltersTo() {
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalTime getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(LocalTime timeFrom) {
        this.timeFrom = timeFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public LocalTime getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(LocalTime timeTo) {
        this.timeTo = timeTo;
    }

    @Override
    public String toString() {
        return "MealFiltersTo{" +
                "dateFrom=" + dateFrom +
                ", timeFrom=" + timeFrom +
                ", dateTo=" + dateTo +
                ", timeTo=" + timeTo +
                '}';
    }

    public static MealFiltersTo of(String dateFromAsString, String timeFromAsString, String dateToAsString, String timeToAsString) {
        MealFiltersTo result = new MealFiltersTo();
        if (dateFromAsString != null && !dateFromAsString.isEmpty()) {
            result.setDateFrom(LocalDate.parse(dateFromAsString, dateFormatter));
        }
        if (timeFromAsString != null && !timeFromAsString.isEmpty()) {
            result.setTimeFrom(LocalTime.parse(timeFromAsString, timeFormatter));
        }
        if (dateToAsString != null && !dateToAsString.isEmpty()) {
            result.setDateTo(LocalDate.parse(dateToAsString, dateFormatter));
        }
        if (timeToAsString != null && !timeToAsString.isEmpty()) {
            result.setTimeTo(LocalTime.parse(timeToAsString, timeFormatter));
        }
        return result;
    }
}
