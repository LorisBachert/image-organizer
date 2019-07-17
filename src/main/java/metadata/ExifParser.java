package metadata;

import com.drew.imaging.ImageMetadataReader;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import model.FileMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.function.Consumer;

public class ExifParser implements Consumer<FileMetadata> {

    private static final Logger logger = LogManager.getLogger(ExifParser.class);

    @Override
    public void accept(FileMetadata fileMetadata) {
        try {
            logger.info("Processing file {}", fileMetadata.getPath().toString());
            Metadata metadata = ImageMetadataReader.readMetadata(fileMetadata.getPath().toFile());
            fileMetadata.setCreationDate(getCreationDate(metadata));
            fileMetadata.setGeoLocation(getGeolocation(metadata));
        } catch (Exception e) {
            logger.error("Failed to parse exif metadata for file {}", fileMetadata.getPath(), e);
        }
    }

    private Date getCreationDate(Metadata metadata) {
        ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        return directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
    }

    private GeoLocation getGeolocation(Metadata metadata) {
        GpsDirectory directory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
        return directory == null ? null : directory.getGeoLocation();
    }
}
