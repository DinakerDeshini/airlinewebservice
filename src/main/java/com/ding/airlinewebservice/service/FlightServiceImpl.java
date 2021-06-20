package com.ding.airlinewebservice.service;

import com.ding.airlinewebservice.dto.FlightDto;
import com.ding.airlinewebservice.entity.Flight;
import com.ding.airlinewebservice.repository.FlightRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FlightServiceImpl implements FlightService{

    @Autowired
    FlightRepository flightRepository;

    @Override
    public FlightDto find(Integer flightId) {

        Flight flightById = flightRepository.findById(flightId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found") );
        FlightDto flightDto = new FlightDto();
        BeanUtils.copyProperties(flightById,flightDto);

        return flightDto;
    }

    @Override
    public FlightDto saveFlight(FlightDto flightDto) {

        Flight flight = new Flight();
        BeanUtils.copyProperties(flightDto,flight);

        Flight flightResponse = flightRepository.save(flight);

        BeanUtils.copyProperties(flightResponse, flightDto);

        return flightDto;

    }

    @Override
    public FlightDto update(FlightDto flightDto) {
        Flight flightById = flightRepository.findById(flightDto.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));

        Flight flight = new Flight();
        BeanUtils.copyProperties(flightDto,flight);

        flightRepository.save(flight);

        BeanUtils.copyProperties(flight,flightDto);

        return flightDto;
    }

    @Override
    public void delete(Integer flightId) {
        Flight flightById = flightRepository.findById(flightId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));

        flightRepository.deleteById(flightId);
    }

    @Override
    public List<FlightDto> findAllFlights() {
        Iterable<Flight> flightIterable = flightRepository.findAll();
        List<FlightDto> flightDtoList = StreamSupport.stream(flightIterable.spliterator(), false).map(flight -> {
            FlightDto flightDto = new FlightDto();
            BeanUtils.copyProperties(flight, flightDto);
            return flightDto;
        }).collect(Collectors.toList());
        return flightDtoList;
    }

    @Override
    public FlightDto findFlightByFlightNumber(String flightNumber) {
       Flight flight = flightRepository.findFlightByFlightNumber(flightNumber);
       FlightDto flightDto = new FlightDto();
       BeanUtils.copyProperties(flight,flightDto);
       return flightDto;
    }
}
