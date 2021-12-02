package ru.otus.factorymethod2;

import ru.otus.factorymethod1.Configuration;
import ru.otus.factorymethod1.ConfigurationDB;

class ConfigurationFactoryDB extends ConfigurationFactory {
  @Override
  Configuration buildConfiguration() {
    return new ConfigurationDB();
  }
}
