package org.bachert.imageorganizer.geolocation;

import com.drew.lang.GeoLocation;
import org.bachert.imageorganizer.geolocation.model.GeoLocationAddress;
import org.bachert.imageorganizer.geolocation.model.GeoLocationResult;
import org.bachert.imageorganizer.session.SessionDataService;
import org.bachert.imageorganizer.trips.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class GeoLocationService {

    @Autowired
    private SessionDataService sessionDataService;

    private static final Map<GeoLocation, GeoLocationAddress> CACHE = new HashMap<>();

    public void getCountryAndCity(Trip trip) {
        Optional<GeoLocation> geoLocationOpt = trip.getFiles().stream().findFirst().map(fileId -> sessionDataService.get(fileId).getGeoLocation());
        if (! geoLocationOpt.isPresent()) {
            return;
        }
        GeoLocation geoLocation = geoLocationOpt.get();
        GeoLocationAddress geoLocationAddress = CACHE.computeIfAbsent(geoLocation, location -> {
            RestTemplate restTemplate = new RestTemplate();
            String url = String.format("https://nominatim.openstreetmap.org/reverse?lat=%s&lon=%s&zoom=18&format=json&email=loris.bachert@gmx.de",
                    geoLocation.getLatitude(), geoLocation.getLongitude());
            ResponseEntity<GeoLocationResult> response
                    = restTemplate.getForEntity(url, GeoLocationResult.class);
            if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null) {
                return response.getBody().getAddress();
            } else {
                return null;
            }
        });
        Optional.ofNullable(geoLocationAddress).ifPresent(address -> {
            trip.setCity(address.getCity());
            trip.setCountry(address.getCountry());
        });
    }
}
