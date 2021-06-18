package com.ding.airlinewebservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Objects;

public class FlightDto {

    private Integer id;
    private String flightNumber;
    private Integer capacity;
    private String mfdBy;
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
