package ru.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;

public class SensorDataProcessorCommon implements SensorDataProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(SensorDataProcessorCommon.class);

    @Override
    public void process(SensorData data) {
        if (data == null || data.getValue() == null || data.getValue().isNaN()) {
            return;
        }
        LOG.info("Обработка данных: {}", data);
    }
}
