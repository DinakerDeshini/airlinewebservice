package com.ding.airlinewebservice.controller;

import com.ding.airlinewebservice.dto.FlightDto;
import com.ding.airlinewebservice.entity.Flight;
import com.ding.airlinewebservice.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class FlightController {

    @Autowired
    FlightService flightService;

    @GetMapping(path = "/flight/getinfo/{id}")
    public FlightDto getFlightInfo(@PathVariable(name = "id") Integer flightId) {
        return flightService.find(flightId);
    }

    @GetMapping(path = "/flights")
    public List<FlightDto> getAllFlights() {
        return flightService.findAllFlights();
    }

    @GetMapping(path = "/flight/getflightinfo/{flightNumber}")
    public FlightDto getFlightByFlightNumber(@PathVariable(name="flightNumber") String flightNumber) {
        return flightService.findFlightByFlightNumber(flightNumber);
    }

    @PostMapping(path = "/flight")
    public FlightDto createFlightInfo(@RequestBody FlightDto flightDto) {

        return flightService.saveFlight(flightDto);
    }

    @PutMapping(path = "/flight/update")
    public FlightDto updateFlightInfo(@RequestBody FlightDto flightDto) {
        return flightService.update(flightDto);
    }

    @DeleteMapping(path = "/flight/delete/{id}")
    public void deleteFlight(@PathVariable(name = "id") Integer flightId) {
        flightService.delete(flightId);

    }
}

