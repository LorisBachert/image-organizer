package org.bachert.imageorganizer.trips;

import lombok.extern.slf4j.Slf4j;
import org.bachert.imageorganizer.session.SessionDataService;
import org.bachert.imageorganizer.trips.dto.TripDTO;
import org.bachert.imageorganizer.trips.mapper.TripMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class TripService {

    @Autowired
    private SessionDataService sessionDataService;

    @Autowired
    private TripMapper tripMapper;

    public boolean isDone() {
        return sessionDataService.isDoneDetectingTrips();
    }

    public List<TripDTO> getTrips() {
        return sessionDataService.getTrips().stream().map(tripMapper::toDTO).collect(toList());
    }
}
