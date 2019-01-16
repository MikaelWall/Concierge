package com.nerdblistersteam.concierge.domain;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class Timespann implements Comparator<Timespann>{
    private LocalDateTime start;
    private LocalDateTime stop;
    boolean isBooked;

    public Timespann(LocalDateTime start, LocalDateTime stop, Boolean isBooked) {
        this.start = start;
        this.stop = stop;
        this.isBooked = isBooked;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalTime getStartClock() {
        return start.toLocalTime();
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getStop() {
        return stop;
    }

    public LocalTime getStopClock() {
        return stop.toLocalTime();
    }

    public void setStop(LocalDateTime stop) {
        this.stop = stop;
    }

    @Override
    public int compare(Timespann o1, Timespann o2) {
        if (o1.getStart().isBefore(o2.getStart())) {
            return 0;
        }
        return 1;
    }

    @Override
    public String toString() {
        return "Timespann{" +
                "start=" + start +
                ", stop=" + stop +
                '}';
    }
}
