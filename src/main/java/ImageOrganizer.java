import geolocation.TripGrouper;
import io.FileCrawler;
import metadata.ExifParser;
import metadata.MetadataExpander;
import model.FileMetadata;
import model.Trip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.midi.MetaEventListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ImageOrganizer {

    private static final Logger logger = LogManager.getLogger(ImageOrganizer.class);

    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\loris.bachert.HEIDELPAY\\Pictures\\Test";
        List<FileMetadata> expandedFiles = FileCrawler.getFiles(path)
                .peek(MetadataExpander.expandMetadata)
                .sorted(new FileMetadataComparator())
                .collect(Collectors.toList());
        List<Trip> trips = TripGrouper.groupTrips(expandedFiles);
        trips.forEach(logger::info);
    }

    private static class FileMetadataComparator implements Comparator<FileMetadata> {

        @Override
        public int compare(FileMetadata o1, FileMetadata o2) {
            if (o1.getCreationDate() == null && o2.getCreationDate() == null) {
                return 0;
            } else if (o1.getCreationDate() == null) {
                return -1;
            } else if (o2.getCreationDate() == null) {
                return 1;
            }
            return o1.getCreationDate().compareTo(o2.getCreationDate());
        }
    }
}
