package com.ding.airlinewebservice.dto;

import com.ding.airlinewebservice.validation.FlightMfdBy;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Objects;

public class FlightDto extends RepresentationModel<FlightDto> {

    private Integer id;

    @NotNull
    private String flightNumber;

    @NotNull
    @Positive(message = "Capacity cannot be negative")
    private Integer capacity;

    @NotNull
    @FlightMfdBy
    private String mfdBy;

    @NotNull
    @Past(message = "Current or Future date is not allowed")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate mfdOn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getMfdBy() {
        return mfdBy;
    }

    public void setMfdBy(String mfdBy) {
        this.mfdBy = mfdBy;
    }

    public LocalDate getMfdOn() {
        return mfdOn;
    }

    public void setMfdOn(LocalDate mfdOn) {
        this.mfdOn = mfdOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightDto flightDto = (FlightDto) o;
        return id.equals(flightDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "FlightDto{" +
                "id=" + id +
                ", flightNumber='" + flightNumber + '\'' +
                ", capacity=" + capacity +
                ", mfdBy='" + mfdBy + '\'' +
                ", mfdOn=" + mfdOn +
                '}';
    }
}
