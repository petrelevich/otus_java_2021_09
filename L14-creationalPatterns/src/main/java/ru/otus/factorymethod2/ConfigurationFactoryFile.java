package ru.otus.factorymethod2;

import ru.otus.factorymethod1.Configuration;
import ru.otus.factorymethod1.ConfigurationFile;

class ConfigurationFactoryFile extends ConfigurationFactory  {
  @Override
  Configuration buildConfiguration() {
    return new ConfigurationFile();
  }
}
