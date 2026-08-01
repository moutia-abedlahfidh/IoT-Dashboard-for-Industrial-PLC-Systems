package com.RestfulWebApp.RestfulWebApp.Repository;
import com.RestfulWebApp.RestfulWebApp.models.Temperatur;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TemperatureRepository  extends MongoRepository<Temperatur, String> {

}