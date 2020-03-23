package org.bachert.imageorganizer.expander.impl;

import com.drew.imaging.ImageMetadataReader;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bachert.imageorganizer.model.FileMetadata;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Consumer;

@Component
public class ExifParser implements Consumer<FileMetadata> {

    private static final Logger logger = LogManager.getLogger(ExifParser.class);

    @Override
    public void accept(FileMetadata fileMetadata) {
        try {
            logger.info("Processing file {}", fileMetadata.getPath().toString());
            Metadata metadata = ImageMetadataReader.readMetadata(fileMetadata.getPath().toFile());
            fileMetadata.setCreationDate(getCreationDate(metadata));
            fileMetadata.setGeoLocation(getGeolocation(metadata));
            fileMetadata.setWidth(getDimension(metadata, ExifSubIFDDirectory.TAG_EXIF_IMAGE_WIDTH));
            fileMetadata.setWidth(getDimension(metadata, ExifSubIFDDirectory.TAG_EXIF_IMAGE_HEIGHT));
        } catch (Exception e) {
            logger.error("Failed to parse exif metadata for file {}", fileMetadata.getPath(), e);
        }
    }

    private int getDimension(Metadata metadata, int whichDimension) {
        ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        return directory.getInteger(whichDimension);
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
