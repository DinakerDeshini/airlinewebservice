package com.ding.airlinewebservice.repository;

import com.ding.airlinewebservice.entity.Flight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends CrudRepository<Flight,Integer> {

    Flight findFlightByFlightNumber(String flightNumber);
}
