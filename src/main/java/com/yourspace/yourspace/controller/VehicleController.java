package com.yourspace.yourspace.controller;

import com.yourspace.yourspace.model.Vehicle;
import com.yourspace.yourspace.model.VehicleList;
import com.yourspace.yourspace.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicle/v1")
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(
            final @RequestBody Vehicle vehicle
            ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.createVehicle(vehicle));
    }

    @GetMapping
    public ResponseEntity<VehicleList> getAllVehicles() {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getAllVehicles());
    }

    @GetMapping("{vehicleId}")
    public ResponseEntity<Vehicle> getVehicleById(
            final @PathVariable String vehicleId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                vehicleService.getVehicleById(new ObjectId(vehicleId))
        );
    }
}
