package ru.otus.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.SensorsDataChannel;
import ru.otus.api.model.SensorData;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class SensorsDataQueueChannel implements SensorsDataChannel {

    public static final int SENSORS_DATA_QUEUE_CAPACITY = 1000;

    private static final Logger LOG = LoggerFactory.getLogger(SensorsDataQueueChannel.class);

    private final BlockingQueue<SensorData> sensorsDataQueue;

    public SensorsDataQueueChannel() {
        sensorsDataQueue = new ArrayBlockingQueue<>(SENSORS_DATA_QUEUE_CAPACITY);
    }

    @Override
    public boolean push(SensorData sensorData) {
        var pushResult = sensorsDataQueue.offer(sensorData);
        if (!pushResult) {
            LOG.warn("Очередь показаний переполнена");
        }
        return pushResult;
    }

    @Override
    public boolean isEmpty() {
        return sensorsDataQueue.isEmpty();
    }

    @Override
    public SensorData take(long timeout, TimeUnit unit) throws InterruptedException {
        return sensorsDataQueue.poll(timeout, unit);
    }
}
