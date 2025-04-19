package org.axzarian.timebot.model.domain;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class Stopwatch {

    private Instant startTime = Instant.now();

    public long getTime() {
        return Duration.between(startTime, Instant.now()).toSeconds();
    }

    public void reset() {
        this.startTime = Instant.now();
    }

    public void setStartTime(Instant time) {
        this.startTime = time;
    }

    public String formatUptime() {
        long totalSeconds = this.getTime();

        long days = totalSeconds / (60 * 60 * 24);
        long hours = (totalSeconds / (60 * 60)) % 24;
        long minutes = (totalSeconds / 60) % 60;
        long seconds = totalSeconds % 60;

        return "Days: %d, hours: %d, min: %d, sec: %d".formatted(days, hours, minutes, seconds);
    }
}
