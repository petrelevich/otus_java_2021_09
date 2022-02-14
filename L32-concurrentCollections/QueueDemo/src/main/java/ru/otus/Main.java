package ru.otus;

import ru.otus.lib.SensorDataBufferedWriterFake;
import ru.otus.services.FakeSensorDataGenerator;
import ru.otus.services.SensorDataProcessingFlowImpl;
import ru.otus.services.SensorsDataQueueChannel;
import ru.otus.services.SensorsDataServerImpl;
import ru.otus.services.processors.SensorDataProcessorBuffered;
import ru.otus.services.processors.SensorDataProcessorCommon;
import ru.otus.services.processors.SensorDataProcessorErrors;
import ru.otus.services.processors.SensorDataProcessorRoom;

import java.util.concurrent.TimeUnit;

public class Main {
    private static final String ALL_ROOMS_BINDING = "*";
    private static final String ROOM_NAME_BINDING = "Комната: 4";
    private static final int BUFFER_SIZE = 15;
    private static final int SENSORS_COUNT = 4;

    public static void main(String[] args) throws InterruptedException {

        var sensorsDataChannel = new SensorsDataQueueChannel();
        var sensorsDataServer = new SensorsDataServerImpl(sensorsDataChannel);
        var sensorDataProcessingFlow = new SensorDataProcessingFlowImpl(sensorsDataChannel);

        var fakeSensorDataGenerator = new FakeSensorDataGenerator(sensorsDataServer, SENSORS_COUNT);

        sensorDataProcessingFlow.bindProcessor(ALL_ROOMS_BINDING, new SensorDataProcessorCommon());
        sensorDataProcessingFlow.bindProcessor(ALL_ROOMS_BINDING, new SensorDataProcessorErrors());
        sensorDataProcessingFlow.bindProcessor(ROOM_NAME_BINDING, new SensorDataProcessorRoom(ROOM_NAME_BINDING));
        sensorDataProcessingFlow.bindProcessor(ALL_ROOMS_BINDING, new SensorDataProcessorBuffered(BUFFER_SIZE,
                new SensorDataBufferedWriterFake()));

        fakeSensorDataGenerator.start();
        sensorDataProcessingFlow.startProcessing();

        TimeUnit.SECONDS.sleep(10);

        fakeSensorDataGenerator.stop();
        sensorDataProcessingFlow.stopProcessing();
    }
}
