package org.bachert.imageorganizer.trips;

import org.bachert.imageorganizer.trips.dto.TripDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @GetMapping
    public List<TripDTO> findTrips() {
        return tripService.getTrips();
    }

    @GetMapping(value = "/done", produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody
    String isDone() {
        return String.valueOf(tripService.isDone());
    }
}
