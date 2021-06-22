package com.ding.airlinewebservice.controller;

import com.ding.airlinewebservice.dto.FlightDto;
import com.ding.airlinewebservice.entity.Flight;
import com.ding.airlinewebservice.exception.ApiErrorResponse;
import com.ding.airlinewebservice.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;


@RestController
@Validated
@Tag(name = "Flight Controller", description = "API methods of Airline webservice")
public class FlightController {

    private static final Logger logger = LogManager.getLogger(FlightController.class);

    @Autowired
    FlightService flightService;

    @Operation(summary = "Get Flight Info by Id", description = "This method gets flight information by flight Id", method = "GET", tags = {"Flight Controller"})
    @Parameter(name = "id", description = "Flight Id", example = "3", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Flight Info was successfully fetched",
                    content = @Content(schema = @Schema(implementation = FlightDto.class))),
            @ApiResponse(responseCode = "404", description = "Flight Info was not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping(path = "/flight/getinfo/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public EntityModel<FlightDto> getFlightInfo(@PathVariable(name = "id") @Positive Integer flightId) {

        FlightDto flightDto = flightService.find(flightId);
        Link link = linkTo(methodOn(FlightController.class).getFlightInfo(flightId)).withSelfRel();
        flightDto.add(link);
        return EntityModel.of(flightDto);
    }

    @Operation(summary = "Get all Flights Info", description = "This method gets a list of all flights information", method = "GET", tags = {"Flight Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Flight Info was successfully fetched",
                    content = @Content(schema = @Schema(implementation = FlightDto.class))),
            @ApiResponse(responseCode = "404", description = "Flight Info was not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping(path = "/flights", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<FlightDto> getAllFlights() {

        List<FlightDto> flightDtoList = flightService.findAllFlights();
        for (FlightDto flightDto: flightDtoList) {
            Link link = linkTo(methodOn(FlightController.class).getFlightInfo(flightDto.getId())).withSelfRel();
            flightDto.add(link);
        }
        Link link = linkTo(methodOn(FlightController.class).getAllFlights()).withSelfRel();
        return CollectionModel.of(flightDtoList, link);
    }

    @Operation(summary = "Get Flight Info by flight number", description = "This method gets flight information by flight number", method = "GET", tags = {"Flight Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Flight Info was successfully fetched",
                    content = @Content(schema = @Schema(implementation = FlightDto.class))),
            @ApiResponse(responseCode = "404", description = "Flight Info was not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping(path = "/flight/getflightinfo/{flightNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<FlightDto> getFlightByFlightNumber(
            @Parameter(name = "flightNumber", description = "Flight Number", example = "F123", required = true, in = ParameterIn.PATH)
            @PathVariable(name="flightNumber") String flightNumber) {

        FlightDto flightDto = flightService.findFlightByFlightNumber(flightNumber);
        Link link = linkTo(methodOn(FlightController.class).getFlightByFlightNumber(flightNumber)).withSelfRel();
        flightDto.add(link);
        return EntityModel.of(flightDto);
    }

    @Operation(summary = "Create Flight Info", description = "This method posts flight information", method = "POST", tags = {"Flight Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Flight Info was successfully created",
                    content = @Content(schema = @Schema(implementation = FlightDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping(path = "/flight", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<FlightDto> createFlightInfo(
            @Parameter(name = "flightDto", description = "Flight Dto",
                    content = @Content(schema = @Schema(implementation = FlightDto.class)), required = true, in = ParameterIn.DEFAULT)
            @RequestBody @Valid FlightDto flightDto) {

        FlightDto dto = flightService.saveFlight(flightDto);
        Link link = linkTo(methodOn(FlightController.class).getFlightInfo(flightDto.getId())).withSelfRel();
        dto.add(link);
        return EntityModel.of(dto);
    }

    @Operation(summary = "Update Flight Info", description = "This method updates flight information", method = "PUT", tags = {"Flight Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Flight Info was successfully updated",
                    content = @Content(schema = @Schema(implementation = FlightDto.class))),
            @ApiResponse(responseCode = "404", description = "Flight Info was not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PutMapping(path = "/flight/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<FlightDto> updateFlightInfo(
            @Parameter(name = "flightDto", description = "Flight Dto",
            content = @Content(schema = @Schema(implementation = FlightDto.class)), required = true, in = ParameterIn.DEFAULT)
            @RequestBody FlightDto flightDto) {

        FlightDto dto = flightService.update(flightDto);
        Link link = linkTo(methodOn(FlightController.class).getFlightInfo(flightDto.getId())).withSelfRel();
        dto.add(link);
        return EntityModel.of(dto);
    }

    @Operation(summary = "Delete Flight Info", description = "This method deletes flight information", method = "DELETE", tags = {"Flight Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Flight Info was successfully deleted",
                    content = @Content()),
            @ApiResponse(responseCode = "404", description = "Flight Info was not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping(path = "/flight/delete/{id}")
    public void deleteFlight(
            @Parameter(name = "id", description = "Flight Id", example = "3", required = true, in = ParameterIn.PATH)
            @PathVariable(name = "id") Integer flightId) {
        flightService.delete(flightId);

    }
}

