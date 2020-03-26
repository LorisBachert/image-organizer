package org.bachert.imageorganizer.trips;

import lombok.extern.slf4j.Slf4j;
import org.bachert.imageorganizer.metadata.model.FileMetadata;
import org.bachert.imageorganizer.session.SessionDataService;
import org.bachert.imageorganizer.trips.dto.TripDTO;
import org.bachert.imageorganizer.trips.mapper.TripMapper;
import org.bachert.imageorganizer.trips.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public void update(Long id, TripDTO trip) {
        Trip existingTrip = sessionDataService.getTrip(id);
        existingTrip.setName(trip.getName());
        List<FileMetadata> files = trip.getFiles().stream().map(fileId -> sessionDataService.get(fileId)).collect(toList());
        existingTrip.updateFiles(files);
    }
}
