package com.yourspace.yourspace.service;

import com.yourspace.yourspace.model.Vehicle;
import com.yourspace.yourspace.model.VehicleList;
import com.yourspace.yourspace.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public Vehicle createVehicle(final Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public VehicleList getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return new VehicleList(vehicles);
    }

    public Vehicle getVehicleById(final ObjectId id) {
         return vehicleRepository.findById(id).orElseThrow(
                 () -> new RuntimeException("test")
         );
    }
}
