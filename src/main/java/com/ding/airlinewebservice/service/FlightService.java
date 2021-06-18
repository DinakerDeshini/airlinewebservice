package com.ding.airlinewebservice.service;

import com.ding.airlinewebservice.dto.FlightDto;
import com.ding.airlinewebservice.entity.Flight;

import java.util.List;

public interface FlightService {

    FlightDto find(Integer flightId);

    FlightDto saveFlight(FlightDto flightDto);

    FlightDto update(FlightDto flightDto);

    void delete(Integer flightId);

    List<FlightDto> findAllFlights();

    FlightDto findFlightByFlightNumber(String flightNumber);
}
