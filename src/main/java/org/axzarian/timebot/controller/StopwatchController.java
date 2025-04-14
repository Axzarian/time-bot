package org.axzarian.timebot.controller;

import lombok.RequiredArgsConstructor;
import org.axzarian.timebot.entity.Stopwatch;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stopwatch")
public class StopwatchController {

    private final Stopwatch stopwatch;

    @PostMapping
    public ResponseEntity<Void> updateStopwatch(@RequestParam Long time) {
        stopwatch.setStartTime(Instant.ofEpochMilli(time));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
