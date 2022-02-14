package ru.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;

public class SensorDataProcessorRoom implements SensorDataProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(SensorDataProcessorRoom.class);

    private final String roomName;

    public SensorDataProcessorRoom(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public void process(SensorData data) {
        if (data == null || data.getValue() == null || data.getValue().isNaN()) {
            return;
        }
        LOG.info("Обработка данных по заданной комнате ({}): {}", roomName, data);
    }
}
