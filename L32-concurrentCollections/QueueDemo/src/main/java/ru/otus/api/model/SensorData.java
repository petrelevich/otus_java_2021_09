package ru.otus.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class SensorData {
    private LocalDateTime measurementTime = LocalDateTime.now();
    private String room;
    private Double value;

    public SensorData(String room, Double value) {
        this.room = room;
        this.value = value;
    }
}
