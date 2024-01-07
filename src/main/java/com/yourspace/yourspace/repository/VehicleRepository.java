package com.yourspace.yourspace.repository;

import com.yourspace.yourspace.model.Vehicle;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, ObjectId> {
}
