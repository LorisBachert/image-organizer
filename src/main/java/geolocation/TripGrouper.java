package geolocation;

import com.drew.lang.GeoLocation;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.FileMetadata;
import model.GeoLocationResult;
import model.Trip;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TripGrouper {

    private static final Logger logger = LogManager.getLogger(TripGrouper.class);

    private static final int TRIP_DISTANCE = 10 * 1000; // 50 km

    public static List<Trip> groupTrips(List<FileMetadata> files) {
        List<Trip> trips = new ArrayList<>();
        Trip trip = new Trip();
        FileMetadata lastFileMetadata = null;
        for (FileMetadata file : files) {
            if (isNewTrip(file, lastFileMetadata)) {
                getCountryAndCity(trip);
                trips.add(trip);
                trip = new Trip();
            }
            trip.addFile(file);
            lastFileMetadata = file;
        }
        logger.info("Found {} trips", trips.size());
        return trips;
    }

    private static boolean isNewTrip(FileMetadata currentFile, FileMetadata lastFile) {
        return lastFile != null && distance(currentFile.getGeoLocation(), lastFile.getGeoLocation()) > TRIP_DISTANCE;
    }

    private static float distance(GeoLocation loc1, GeoLocation loc2) {
        if (loc1 == null || loc2 == null) {
            return 0;
        }
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(loc2.getLatitude()-loc1.getLatitude());
        double dLng = Math.toRadians(loc2.getLongitude()-loc1.getLongitude());
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(loc1.getLatitude())) * Math.cos(Math.toRadians(loc2.getLatitude())) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return (float) (earthRadius * c);
    }

    private static void getCountryAndCity(Trip trip) {
        GeoLocation geoLocation = trip.getGeoLocation();
        if (geoLocation == null) {
            return;
        }
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            String url = String.format("https://nominatim.openstreetmap.org/reverse?lat=%s&lon=%s&zoom=18&format=json",
                    geoLocation.getLatitude(), geoLocation.getLongitude());
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            GeoLocationResult geoLocationResult = objectMapper.readValue(response.getEntity().getContent(), GeoLocationResult.class);
            trip.setCity(geoLocationResult.getAddress().getCity());
            trip.setCountry(geoLocationResult.getAddress().getCountry());
        } catch (IOException ex) {
            logger.error("Cannot fetch country and city details for trip {}", trip, ex);
        }
    }
}
