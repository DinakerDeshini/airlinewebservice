package com.ding.airlinewebservice.controller;

import com.ding.airlinewebservice.dto.FlightDto;
import com.ding.airlinewebservice.entity.Flight;
import com.ding.airlinewebservice.service.FlightService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;


@RestController
@Validated
public class FlightController {

    private static final Logger logger = LogManager.getLogger(FlightController.class);

    @Autowired
    FlightService flightService;

    @GetMapping(path = "/flight/getinfo/{id}")
    public EntityModel<FlightDto> getFlightInfo(@PathVariable(name = "id") @Positive Integer flightId) {

        FlightDto flightDto = flightService.find(flightId);
        Link link = linkTo(methodOn(FlightController.class).getFlightInfo(flightId)).withSelfRel();
        flightDto.add(link);
        return EntityModel.of(flightDto);
    }

    @GetMapping(path = "/flights")
    public CollectionModel<FlightDto> getAllFlights() {

        List<FlightDto> flightDtoList = flightService.findAllFlights();
        for (FlightDto flightDto: flightDtoList) {
            Link link = linkTo(methodOn(FlightController.class).getFlightInfo(flightDto.getId())).withSelfRel();
            flightDto.add(link);
        }
        Link link = linkTo(methodOn(FlightController.class).getAllFlights()).withSelfRel();
        return CollectionModel.of(flightDtoList, link);
    }

    @GetMapping(path = "/flight/getflightinfo/{flightNumber}")
    public EntityModel<FlightDto> getFlightByFlightNumber(@PathVariable(name="flightNumber") String flightNumber) {

        FlightDto flightDto = flightService.findFlightByFlightNumber(flightNumber);
        Link link = linkTo(methodOn(FlightController.class).getFlightByFlightNumber(flightNumber)).withSelfRel();
        flightDto.add(link);
        return EntityModel.of(flightDto);
    }

    @PostMapping(path = "/flight")
    public EntityModel<FlightDto> createFlightInfo(@RequestBody @Valid FlightDto flightDto) {

        FlightDto dto = flightService.saveFlight(flightDto);
        Link link = linkTo(methodOn(FlightController.class).getFlightInfo(flightDto.getId())).withSelfRel();
        dto.add(link);
        return EntityModel.of(dto);
    }

    @PutMapping(path = "/flight/update")
    public EntityModel<FlightDto> updateFlightInfo(@RequestBody FlightDto flightDto) {

        FlightDto dto = flightService.update(flightDto);
        Link link = linkTo(methodOn(FlightController.class).getFlightInfo(flightDto.getId())).withSelfRel();
        dto.add(link);
        return EntityModel.of(dto);
    }

    @DeleteMapping(path = "/flight/delete/{id}")
    public void deleteFlight(@PathVariable(name = "id") Integer flightId) {
        flightService.delete(flightId);

    }
}

