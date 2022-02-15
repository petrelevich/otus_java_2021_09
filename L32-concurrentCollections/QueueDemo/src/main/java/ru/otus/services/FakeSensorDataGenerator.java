package ru.otus.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.SensorsDataServer;
import ru.otus.api.model.SensorData;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FakeSensorDataGenerator {
    private static final int POOL_SIZE = 3;

    private static final Logger LOG = LoggerFactory.getLogger(FakeSensorDataGenerator.class);

    private final int sensorsCount;
    private final Random random = new Random();
    private ScheduledExecutorService dataGenerationThreadPool = null;
    private final SensorsDataServer sensorServer;
    private final AtomicInteger processedCount = new AtomicInteger(0);

    public FakeSensorDataGenerator(SensorsDataServer sensorServer, int sensorsCount) {
        this.sensorServer = sensorServer;
        this.sensorsCount = sensorsCount;

    }

    public void start() {
        if (dataGenerationThreadPool != null) {
            throw new IllegalStateException("Поток данных уже запущен!");
        }

        dataGenerationThreadPool = Executors.newScheduledThreadPool(POOL_SIZE);
        dataGenerationThreadPool.scheduleAtFixedRate(this::generateSensorDataAndSend, 1, 500, TimeUnit.MILLISECONDS);
    }

    public void stop(){
        if (dataGenerationThreadPool == null) {
            return;
        }
        dataGenerationThreadPool.shutdown();
        dataGenerationThreadPool = null;
    }

    private void generateSensorDataAndSend() {
        sensorServer.onReceive(generate(sensorsCount));
    }

    private SensorData generate(int sensorsCount) {
        processedCount.incrementAndGet();
        var sensorData = new SensorData("Комната: " + random.nextInt(1, sensorsCount + 1),
                random.nextDouble());

        if (isErrorMustOccurs()) {
            sensorData.setValue(Double.NaN);
        }

        LOG.info("{} Сформированы новые данные датчика: {}", LocalDateTime.now(), sensorData);

        return sensorData;
    }

    private boolean isErrorMustOccurs() {
        return random.nextInt(0, 20) == 13;
    }
}
