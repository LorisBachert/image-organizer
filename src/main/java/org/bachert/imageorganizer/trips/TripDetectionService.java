package org.bachert.imageorganizer.trips;

import com.drew.lang.GeoLocation;
import lombok.extern.slf4j.Slf4j;
import org.bachert.imageorganizer.analyzer.ImageAnalyzer;
import org.bachert.imageorganizer.geolocation.GeoLocationService;
import org.bachert.imageorganizer.metadata.model.FileMetadata;
import org.bachert.imageorganizer.session.SessionDataService;
import org.bachert.imageorganizer.trips.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TripDetectionService implements ImageAnalyzer {

    private static final int TRIP_DISTANCE = 10 * 1000; // 10 km

    @Autowired
    private SessionDataService sessionDataService;

    @Autowired
    private GeoLocationService geoLocationService;

    @Override
    public void accept(List<FileMetadata> files) {
        Trip trip = new Trip();
        FileMetadata lastFileMetadata = null;
        for (FileMetadata file : files) {
            if (isNewTrip(file, lastFileMetadata)) {
                geoLocationService.getCountryAndCity(trip);
                sessionDataService.addTrip(trip);
                trip = new Trip();
            }
            trip.addFile(file);
            lastFileMetadata = file;
        }
        geoLocationService.getCountryAndCity(trip);
        sessionDataService.addTrip(trip);
        sessionDataService.setDoneDetectingTrips(true);
    }


    private static boolean isNewTrip(FileMetadata currentFile, FileMetadata lastFile) {
        return lastFile != null && distance(currentFile.getGeoLocation(), lastFile.getGeoLocation()) > TRIP_DISTANCE;
    }

    private static float distance(GeoLocation loc1, GeoLocation loc2) {
        if (loc1 == null || loc2 == null) {
            return 0;
        }
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(loc2.getLatitude() - loc1.getLatitude());
        double dLng = Math.toRadians(loc2.getLongitude() - loc1.getLongitude());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(loc1.getLatitude())) * Math.cos(Math.toRadians(loc2.getLatitude())) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (float) (earthRadius * c);
    }
}
