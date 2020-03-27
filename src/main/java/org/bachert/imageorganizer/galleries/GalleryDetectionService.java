package org.bachert.imageorganizer.galleries;

import com.drew.lang.GeoLocation;
import lombok.extern.slf4j.Slf4j;
import org.bachert.imageorganizer.analyzer.ImageAnalyzer;
import org.bachert.imageorganizer.galleries.model.Gallery;
import org.bachert.imageorganizer.geolocation.GeoLocationService;
import org.bachert.imageorganizer.metadata.model.FileMetadata;
import org.bachert.imageorganizer.metadata.sort.FileMetadataComparator;
import org.bachert.imageorganizer.process.dto.ProcessConfigurationDTO;
import org.bachert.imageorganizer.session.SessionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class GalleryDetectionService implements ImageAnalyzer {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd");

    @Autowired
    private SessionDataService sessionDataService;

    @Autowired
    private GeoLocationService geoLocationService;

    @Override
    public void accept(List<FileMetadata> files, ProcessConfigurationDTO configuration) {
        if (! configuration.getGalleries().isEnabled()) {
            sessionDataService.setDoneDetectingGalleries(true);
            return;
        }
        files.sort(new FileMetadataComparator());
        Gallery gallery = new Gallery();
        FileMetadata lastFileMetadata = null;
        for (FileMetadata file : files) {
            if (isNewGallery(file, lastFileMetadata, configuration)) {
                addGallery(gallery);
                gallery = new Gallery();
            }
            gallery.addFile(file);
            lastFileMetadata = file;
        }
        addGallery(gallery);
        sessionDataService.setDoneDetectingGalleries(true);
    }

    private void addGallery(Gallery gallery) {
        geoLocationService.getCountryAndCity(gallery);
        gallery.setName(getName(gallery));
        sessionDataService.addGallery(gallery);
    }

    private static boolean isNewGallery(FileMetadata currentFile, FileMetadata lastFile, ProcessConfigurationDTO configuration) {
        return lastFile != null &&
                (atMostHoursDiff(currentFile, lastFile, configuration.getGalleries().getHoursBetween()) ||
                        distance(currentFile.getGeoLocation(), lastFile.getGeoLocation()) > configuration.getGalleries().getDistance());
    }

    private static boolean atMostHoursDiff(FileMetadata currentFile, FileMetadata lastFile, double hoursBetween) {
        long diffInMillis = currentFile.getCreationDate().getTime() - lastFile.getCreationDate().getTime();
        long diffInHours = TimeUnit.HOURS.convert(diffInMillis, TimeUnit.MILLISECONDS);
        return diffInHours > hoursBetween;
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

    private static String getName(Gallery gallery) {
        long diffInMillis = gallery.getEndDate().getTime() - gallery.getStartDate().getTime();
        long diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
        boolean isSameDay = diffInDays == 1;
        StringBuilder builder = new StringBuilder();
        builder.append(DATE_FORMAT.format(gallery.getStartDate()));
        if (!isSameDay) {
            builder.append("-").append(DATE_FORMAT.format(gallery.getEndDate()));
        }
        if (!StringUtils.isEmpty(gallery.getCity())) {
            builder.append(" ").append(gallery.getCity());
        }
        return builder.toString();
    }
}
