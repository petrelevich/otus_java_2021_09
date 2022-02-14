package ru.otus.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.model.SensorData;

import java.util.List;
import java.util.stream.Collectors;

public class SensorDataBufferedWriterFake implements SensorDataBufferedWriter {
    private static final Logger LOG = LoggerFactory.getLogger(SensorDataBufferedWriterFake.class);

    @Override
    public void writeBufferedData(List<SensorData> bufferedData) {
        LOG.info("Как будто куда-то записываем пачку данных: \n{}",
                bufferedData.stream().map(SensorData::toString).collect(Collectors.joining("\n")));
    }
}
