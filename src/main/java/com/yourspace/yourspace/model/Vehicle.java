package com.yourspace.yourspace.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    private String description;
    private Integer manufactureYear;
    private Integer registeredYear;
    private VehicleType vehicleType;
    private ObjectId userId;
    private String model;
    private Double price;
    private TransmissionType transmissionType;
    private FuelType fuelType;
    private VehicleCondition vehicleCondition;
    private Manufacturer manufacturer;
    private Double engineCapacity;
    private Integer mileage;
    private FeatureList featureList;

}
